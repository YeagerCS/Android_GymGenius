package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import okhttp3.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MuscleTargetActivity extends AppCompatActivity {

    private TextView textViewWorkout;
    private String selectedMuscle;
    private EditText selectedMuscleEditText;
    private List<Exercise> givenExercises;
    private Button button;
    private Spinner spinner;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_target);


        givenExercises = new ArrayList<>();
        textViewWorkout = findViewById(R.id.textViewWorkout);
        selectedMuscleEditText = findViewById(R.id.targetMuscleTbx);
        button = findViewById(R.id.searchBtn);
        bottomNavigationView = findViewById(R.id.bottomnav);
        Navigation.loadNavigationBar(bottomNavigationView, MuscleTargetActivity.this);
        Intent intent = getIntent();
        if(intent != null){
            textViewWorkout.setText(intent.getStringExtra("workoutName"));
            if(intent.getSerializableExtra("chosen") != null){
                givenExercises = (List<Exercise>) intent.getSerializableExtra("chosen");
            }
        }
        registerButtonClick();
        configureSpinner();
    }

    private void configureSpinner(){
        String[] options = {"Beginner", "Intermediate", "Expert"};
        spinner = findViewById(R.id.spinner);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, options);
        spinner.setAdapter(adapter);
    }

    private void registerButtonClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog loadingDialog = new Dialog(MuscleTargetActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                loadingDialog.setContentView(R.layout.custom_dialog);
                loadingDialog.setCancelable(false);
                loadingDialog.show();

                selectedMuscle = selectedMuscleEditText.getText().toString();
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> apiRequest(selectedMuscle), executorService);

                future.thenAccept(result -> {
                    if(result != null){
                        Log.d("Tag", result);
                        loadingDialog.dismiss();
                        List<Exercise> exercises = parseExercises(result);
                        exercises.forEach(ex -> Log.d("tag", String.valueOf(ex.getName())));
                        Intent intent = new Intent(getApplicationContext(), ApiListReturnActivity.class);
                        intent.putExtra("exercises", (Serializable) exercises);
                        intent.putExtra("muscle", selectedMuscle);
                        intent.putExtra("workout", textViewWorkout.getText().toString());
                        intent.putExtra("chosen", (Serializable) givenExercises);
                        startActivity(intent);
                    }else {
                        Log.d("ApiError", "Some error occured");
                    }
                });
            }
        });
    }

    private String apiRequest(String muscle) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .writeTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.api-ninjas.com/v1/exercises?muscle=" + muscle + "&difficulty=" + spinner.getSelectedItem().toString())
                    .addHeader("X-Api-Key", "06V+T5JsdFtigAoJerG2AQ==1xsZ2ljWVDExy7xW")
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            Log.e("NetworkError", "Error during network request: " + e.getMessage());
        }

        return null;
    }

    private List<Exercise> parseExercises(String json){
        List<Exercise> exercises = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String muscle = jsonObject.getString("muscle");
                String equipment = jsonObject.getString("equipment");
                String difficulty = jsonObject.getString("difficulty");
                String instructions = jsonObject.getString("instructions");
                Exercise exercise = new Exercise(name, type, muscle, equipment, difficulty, instructions);
                exercises.add(exercise);
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }

        return exercises;
    }

}
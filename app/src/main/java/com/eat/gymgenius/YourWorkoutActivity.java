package com.eat.gymgenius;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class YourWorkoutActivity extends AppCompatActivity {

    private List<Exercise> exercises;
    private ListView listView;
    private String workoutName;
    private BottomNavigationView bottomNavigationView;
    private CustomListAdapter listAdapter;
    private Button addMuscleBtn;
    private int index;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_workout);

        Intent intent = getIntent();
        if(intent != null){
            exercises = (List<Exercise>) intent.getSerializableExtra("chosen");
            workoutName = intent.getStringExtra("workoutName");

            index = intent.getIntExtra("index", -1);
        }

        bottomNavigationView = findViewById(R.id.bottomnav);
        Navigation.loadNavigationBar(bottomNavigationView, YourWorkoutActivity.this);
        saveBtn = findViewById(R.id.saveBtn);
        addMuscleBtn = findViewById(R.id.addMuscleBtn);
        listAdapter = new CustomListAdapter(this, exercises);
        listView = findViewById(R.id.listViewEsse);
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(0);
        listView.setAdapter(listAdapter);

        registerButtonHold();
        registerAddMuscleButton();
        registerSaveButton();
    }

    private void registerButtonHold(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise removedExc = listAdapter.getItem(i);
                exercises.remove(i);

                listAdapter.remove(removedExc);

                listAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void registerAddMuscleButton(){
        addMuscleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MuscleTargetActivity.class);
                intent.putExtra("chosen", (Serializable) exercises);
                intent.putExtra("workoutName", workoutName);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });
    }

    private void registerSaveButton(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == -1){
                    saveWorkout();
                } else{
                    overwriteWorkout();
                }
                //Sleep for .5s, then navigate
                final Dialog loadingDialog = new Dialog(YourWorkoutActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                loadingDialog.setContentView(R.layout.custom_dialog);
                loadingDialog.setCancelable(false);
                loadingDialog.show();
                new Handler().postDelayed(() -> {
                    loadingDialog.dismiss();
                    Intent intent = new Intent(YourWorkoutActivity.this, SavedWorkoutsActivity.class);
                    startActivity(intent);
                }, 500);
            }
        });
    }

    private void saveWorkout(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingWorkoutJson = sharedPreferences.getString("workout_key", "");
        Gson gson = new Gson();

        List<Workout> workoutList;
        if(!existingWorkoutJson.isEmpty()){
            Type typeList = new TypeToken<List<Workout>>(){}.getType();
            workoutList = gson.fromJson(existingWorkoutJson, typeList);
        } else{
            workoutList = new ArrayList<>();
        }

        workoutList.add(new Workout(workoutName, exercises));

        String updatedJson = gson.toJson(workoutList);
        editor.putString("workout_key", updatedJson);
        editor.apply();
    }

    private void overwriteWorkout() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String existingWorkoutJson = sharedPreferences.getString("workout_key", "");
        Gson gson = new Gson();

        List<Workout> workoutList;
        if(!existingWorkoutJson.isEmpty()){
            Type typeList = new TypeToken<List<Workout>>(){}.getType();
            workoutList = gson.fromJson(existingWorkoutJson, typeList);
        } else{
            workoutList = new ArrayList<>();
        }

        try{
            workoutList.set(index, new Workout(workoutName, exercises));
            String updatedJson = gson.toJson(workoutList);
            editor.putString("workout_key", updatedJson);
            editor.apply();
        } catch (Exception ex){
            Log.d("Oops", "This shouldn't have happened");
        }

    }

}
package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedWorkoutsActivity extends AppCompatActivity {
    private List<Workout> workouts;
    private CustomListAdapterWorkouts customListAdapter;
    private RecyclerView listView;
    private boolean isLocked;
    private TextView lockedView;
    private Button button;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        isLocked = sharedPreferences.getBoolean("areWorkoutsLocked", false);
        button = findViewById(R.id.lockButton);
        lockedView = findViewById(R.id.lockTbx);

        if(isLocked){
            button.setText("Unlock Workouts");
            lockedView.setText("\uD83D\uDD12");
        } else{
            button.setText("Lock Workouts");
            lockedView.setText("\uD83D\uDD13");
        }
        workouts = new ArrayList<>();
        listView = findViewById(R.id.recyclerViewSaved);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.workouts);
        Navigation.loadNavigationBar(bottomNavigationView, SavedWorkoutsActivity.this);
        loadWorkouts();
        customListAdapter = new CustomListAdapterWorkouts(workouts);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(customListAdapter);
        registerButtonClick();
    }

    private void registerButtonClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("areWorkoutsLocked", !isLocked);
                editor.apply();

                isLocked = !isLocked;

                if(isLocked){
                    button.setText("Unlock Workouts");
                    lockedView.setText("\uD83D\uDD12");
                } else{
                    button.setText("Lock Workouts");
                    lockedView.setText("\uD83D\uDD13");
                }
            }
        });
    }

    private void loadWorkouts(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String existingWorkouts = sharedPreferences.getString("workout_key", "");
        Log.d("HERE WORKOUTS", existingWorkouts);

        if(!existingWorkouts.isEmpty()){
            Gson gson = new Gson();
            Type typeList = new TypeToken<List<Workout>>(){}.getType();
            List<Workout> workoutList = gson.fromJson(existingWorkouts, typeList);
            workouts = workoutList;
        }
    }
}
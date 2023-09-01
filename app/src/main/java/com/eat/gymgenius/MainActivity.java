package com.eat.gymgenius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnav);
        Navigation.loadNavigationBar(bottomNavigationView, this);
        /*bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.newWorkout){
                    Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });*/

        temporarySharedPreferencesCheck();
    }

    private void temporarySharedPreferencesCheck(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String existingWorkoutJson = sharedPreferences.getString("workout_key", "");

        if (!existingWorkoutJson.isEmpty()) {
            Gson gson = new Gson();
            Type typeList = new TypeToken<List<Workout>>(){}.getType();
            List<Workout> workoutList = gson.fromJson(existingWorkoutJson, typeList);

            for (Workout workout : workoutList) {
                Log.d("Workout Name:", workout.getName());
            }

            Log.d("Json Workouts", existingWorkoutJson);
        } else {
            Log.d("Shared Preferences", "No data found in SharedPreferences");
        }
    }
}
package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);
        workouts = new ArrayList<>();
        listView = findViewById(R.id.recyclerViewSaved);
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.workouts);
        Navigation.loadNavigationBar(bottomNavigationView, SavedWorkoutsActivity.this);
        loadWorkouts();
        customListAdapter = new CustomListAdapterWorkouts(workouts);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(customListAdapter);
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
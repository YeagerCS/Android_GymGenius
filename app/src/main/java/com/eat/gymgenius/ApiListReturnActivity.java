package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ApiListReturnActivity extends AppCompatActivity {


    private TextView muscleTextView;
    private String workoutName;
    private List<Exercise> chosenExercises;
    private CustomRecyclingAdapter customRecyclingAdapter;
    private Button doneButton;
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list_return);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Exercise> exercises;
        Intent intent = getIntent();

        Navigation.loadNavigationBar(findViewById(R.id.bottomnav), this);
        String muscle = "";
        if(intent != null){
            exercises = (List<Exercise>) intent.getSerializableExtra("exercises");
            customRecyclingAdapter = new CustomRecyclingAdapter(exercises);

            workoutName = intent.getStringExtra("workout");
            muscle = intent.getStringExtra("muscle");
            if(intent.getSerializableExtra("chosen") != null){
                chosenExercises = (List<Exercise>) intent.getSerializableExtra("chosen");
                customRecyclingAdapter.setChosenExercises(chosenExercises);
            }
        } else{
            exercises = new ArrayList<>();
        }

        bottomNavigationView = findViewById(R.id.bottomnav);
        Navigation.loadNavigationBar(bottomNavigationView, ApiListReturnActivity.this);
        doneButton = findViewById(R.id.doneBtn);
        muscleTextView = findViewById(R.id.muscleTextView);
        muscleTextView.setText(muscle);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customRecyclingAdapter);

        registerButtonClicks();
    }

    private void registerButtonClicks(){
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessChosenExercises();
                Intent intent = new Intent(getApplicationContext(), YourWorkoutActivity.class);
                intent.putExtra("chosen", (Serializable) chosenExercises);
                intent.putExtra("workoutName", workoutName);
                startActivity(intent);
            }
        });
    }

    private void accessChosenExercises(){
        chosenExercises = customRecyclingAdapter.getChosenExercises();
    }
}
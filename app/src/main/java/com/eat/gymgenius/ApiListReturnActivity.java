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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list_return);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Exercise> exercises;
        Intent intent = getIntent();
        String muscle = "";
        if(intent != null){
            exercises = (List<Exercise>) intent.getSerializableExtra("exercises");
            workoutName = intent.getStringExtra("workout");
            muscle = intent.getStringExtra("muscle");
        } else{
            exercises = new ArrayList<>();
        }

        doneButton = findViewById(R.id.doneBtn);
        muscleTextView = findViewById(R.id.muscleTextView);
        muscleTextView.setText(muscle);

        customRecyclingAdapter = new CustomRecyclingAdapter(exercises);

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
                startActivity(intent);
            }
        });
    }

    private void accessChosenExercises(){
        chosenExercises = customRecyclingAdapter.getChosenExercises();
    }
}
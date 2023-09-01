package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class YourWorkoutActivity extends AppCompatActivity {

    private List<Exercise> exercises;
    private ArrayAdapter<Exercise> exerciseArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_workout);

        Intent intent = getIntent();
        if(intent != null){
            exercises = (List<Exercise>) intent.getSerializableExtra("chosen");
        }

        exerciseArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exercises);

        ListView listView = findViewById(R.id.listViewEsse);
        listView.setAdapter(exerciseArrayAdapter);
    }
}
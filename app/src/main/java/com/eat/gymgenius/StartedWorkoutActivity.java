package com.eat.gymgenius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StartedWorkoutActivity extends AppCompatActivity {

    private Workout workout;
    private TextView workoutNameTitle;
    private CustomRecyclerAdapter customListAdapter;
    private List<Exercise> exercises;
    private RecyclerView listView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_workout);


        Intent intent = getIntent();
        if(intent != null){
            workout = (Workout) intent.getSerializableExtra("workout");
            exercises = workout.getExercises();
        }

        button = findViewById(R.id.giveupButton);
        customListAdapter = new CustomRecyclerAdapter(exercises);
        workoutNameTitle = findViewById(R.id.workoutNameTbx);
        listView = findViewById(R.id.listViewExercises);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(customListAdapter);
        workoutNameTitle.setText(workout.getName());
        listDeletion();
        registerButtonClick();
    }

    private void registerButtonClick(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to Aww Window
                Intent intent = new Intent(StartedWorkoutActivity.this, AwwActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listDeletion(){
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                // Handle item removal here
                String exName = exercises.get(position).getName();
                exercises.remove(position);
                customListAdapter.notifyDataSetChanged();
                eat(exercises);
                Toast.makeText(StartedWorkoutActivity.this, "" + exName + " completed.", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(listView);
    }

    private void eat(List<Exercise> exercises){
        if(exercises.size() == 0){
            Intent intent = new Intent(StartedWorkoutActivity.this, CongratulationsActivity.class);
            startActivity(intent);
        }
    }




}
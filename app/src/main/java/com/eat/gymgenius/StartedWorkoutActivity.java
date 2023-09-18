package com.eat.gymgenius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;

public class StartedWorkoutActivity extends AppCompatActivity {

    private Workout workout;
    private TextView workoutNameTitle;
    private CustomRecyclerAdapter customListAdapter;
    private List<Exercise> exercises;
    private MediaPlayer mediaPlayer;
    private RecyclerView listView;
    private BottomNavigationView bottomNavigationView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_workout);

        Log.d("StartedWorkout", "HERE");
        Intent intent = getIntent();
        if(intent != null){
            workout = (Workout) intent.getSerializableExtra("workout");
            exercises = workout.getExercises();
        }

        mediaPlayer = new MediaPlayer();
        initMediaPlayer();
        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.workouts);
        Navigation.loadNavigationBar(bottomNavigationView, this);

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

    private void initMediaPlayer(){
        try{
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.success));
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void playAudio() {
        mediaPlayer = new MediaPlayer();
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(0);
            } else {
                mediaPlayer.reset();
                initMediaPlayer();
                mediaPlayer.prepare();

            }

            mediaPlayer.start();
            Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

            if (vibrator.hasVibrator()) {
                vibrator.vibrate(600);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
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
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String exName = exercises.get(position).getName();
                exercises.remove(position);
                customListAdapter.notifyDataSetChanged();
                eat(exercises);
                Toast.makeText(StartedWorkoutActivity.this, "" + exName + " completed.", Toast.LENGTH_SHORT).show();
                playAudio();
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
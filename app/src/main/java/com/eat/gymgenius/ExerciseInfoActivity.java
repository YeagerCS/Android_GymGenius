package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class ExerciseInfoActivity extends AppCompatActivity {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        Intent intent = getIntent();

        String infoText = "";
        if(intent != null){
            infoText = intent.getStringExtra("info");
        }

        bottomNavigationView = findViewById(R.id.bottomnav);
        Navigation.loadNavigationBar(bottomNavigationView, ExerciseInfoActivity.this);
        infoTextView= findViewById(R.id.informationTextView);
        infoTextView.setText(infoText);
    }
}
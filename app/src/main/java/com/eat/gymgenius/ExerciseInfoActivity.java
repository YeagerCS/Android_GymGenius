package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ExerciseInfoActivity extends AppCompatActivity {

    private TextView infoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        Intent intent = getIntent();

        String infoText = "";
        if(intent != null){
            infoText = intent.getStringExtra("info");
        }

        infoTextView= findViewById(R.id.informationTextView);
        infoTextView.setText(infoText);
    }
}
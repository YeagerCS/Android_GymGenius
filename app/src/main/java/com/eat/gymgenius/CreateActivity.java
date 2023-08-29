package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private String workoutName;
    private EditText workoutNameTbx;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create1);

        workoutNameTbx = findViewById(R.id.targetMuscleTbx);
        nextBtn = findViewById(R.id.searchBtn);
        registerButtonClicked();
    }

    private void registerButtonClicked(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutName = workoutNameTbx.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MuscleTargetActivity.class);
                intent.putExtra("workoutName", workoutName);
                startActivity(intent);
            }
        });
    }
}

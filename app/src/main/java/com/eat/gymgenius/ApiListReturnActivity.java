package com.eat.gymgenius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ApiListReturnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list_return);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<String> strings = new ArrayList<>();
        strings.add("nigger");
        strings.add("nigger");
        strings.add("esse");

        CustomRecyclingAdapter customRecyclingAdapter = new CustomRecyclingAdapter(strings);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customRecyclingAdapter);
    }
}
package com.eat.gymgenius;


import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Navigation {
    public static void loadNavigationBar(BottomNavigationView bottomNavigationView, Context context){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.newWorkout){
                    Intent intent = new Intent(context, CreateActivity.class);
                    context.startActivity(intent);
                    return true;
                }

                return false;
            }
        });
    }
}

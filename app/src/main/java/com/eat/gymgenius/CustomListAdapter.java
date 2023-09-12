package com.eat.gymgenius;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Exercise> {

    public CustomListAdapter(Context context, List<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.workout_item_layout_v2, parent, false);
        }

        TextView textViewExerciseName = convertView.findViewById(R.id.textViewExerciseName);
        Exercise exercise = getItem(position);

        if (exercise != null) {
            textViewExerciseName.setText(exercise.getName());
        }

        return convertView;
    }


}

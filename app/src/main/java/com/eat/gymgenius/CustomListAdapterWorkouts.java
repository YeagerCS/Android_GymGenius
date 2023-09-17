package com.eat.gymgenius;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomListAdapterWorkouts extends RecyclerView.Adapter<CustomListAdapterWorkouts.ViewHolder> {

    private List<Workout> itemList;

    public CustomListAdapterWorkouts(List<Workout> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout item = itemList.get(position);

        holder.itemText.setText(item.getName());
        //Start
        holder.button1.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), StartedWorkoutActivity.class);
            intent.putExtra("workout", item);
            view.getContext().startActivity(intent);
        });
        //Edit
        holder.button2.setOnClickListener(view -> {
            //YourWorkout
            Intent intent = new Intent(view.getContext(), YourWorkoutActivity.class);
            intent.putExtra("chosen", (Serializable) item.getExercises());
            intent.putExtra("workoutName", item.getName());
            intent.putExtra("index", position);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        Button button1, button2;

        public ViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.textViewExerciseName);
            button1 = itemView.findViewById(R.id.startButton);
            button2 = itemView.findViewById(R.id.editButton);
        }
    }
}

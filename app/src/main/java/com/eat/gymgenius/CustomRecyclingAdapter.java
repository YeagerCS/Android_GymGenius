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

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclingAdapter extends RecyclerView.Adapter<CustomRecyclingAdapter.ViewHolder> {

    private List<Exercise> itemList;
    private List<Exercise> chosenExercises;

    public CustomRecyclingAdapter(List<Exercise> itemList) {
        this.itemList = itemList;
        chosenExercises = new ArrayList<>();
    }

    public List<Exercise> getChosenExercises(){
        return this.chosenExercises;
    }
    public void setChosenExercises(List<Exercise> chosenExercises){
        this.chosenExercises = chosenExercises;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise item = itemList.get(position);

        holder.itemText.setText(item.getName());
        holder.button1.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ExerciseInfoActivity.class);
            intent.putExtra("info", itemList.get(position).getInstructions());
            view.getContext().startActivity(intent);
        });
        if (chosenExercises.contains(item)) {
            holder.button2.setText("Added");
        } else {
            holder.button2.setText("Add");
        }

        holder.button2.setOnClickListener(view -> {
            if (chosenExercises.contains(item)) {
                chosenExercises.remove(item);
                holder.button2.setText("Add");
            } else {
                chosenExercises.add(item);
                holder.button2.setText("Added");
            }
            Log.d("Tag", chosenExercises.toString());
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
            itemText = itemView.findViewById(R.id.itemText);
            button1 = itemView.findViewById(R.id.infoButton);
            button2 = itemView.findViewById(R.id.addButton);
        }
    }
}

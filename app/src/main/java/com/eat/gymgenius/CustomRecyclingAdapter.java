package com.eat.gymgenius;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclingAdapter extends RecyclerView.Adapter<CustomRecyclingAdapter.ViewHolder> {

    private List<Exercise> itemList;

    public CustomRecyclingAdapter(List<Exercise> itemList) {
        this.itemList = itemList;
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
        holder.button2.setOnClickListener(view -> {
            // Handle button2 click
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

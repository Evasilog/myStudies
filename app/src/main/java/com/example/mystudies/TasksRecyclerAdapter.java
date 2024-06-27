package com.example.mystudies;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder> {

    private static List<Task> tasksList;
    private static OnTaskClickListener onTaskClickListener;

    public interface OnTaskClickListener {
        void onTaskClick(int position, List<Task> list);
    }

    public TasksRecyclerAdapter(List<Task> tasksList, OnTaskClickListener onTaskClickListener){
        TasksRecyclerAdapter.tasksList = new ArrayList<>(tasksList);
        TasksRecyclerAdapter.onTaskClickListener = onTaskClickListener;
    }


    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView itemCard;
        TextView itemTaskTitle;
        TextView itemTaskDeadline;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTaskTitle = itemView.findViewById(R.id.item_task_title);
            itemTaskDeadline = itemView.findViewById(R.id.item_task_deadline);
            itemCard = itemView.findViewById(R.id.task_card_view);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                onTaskClickListener.onTaskClick(position, tasksList);
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public TasksRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TasksRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTaskTitle.setText(tasksList.get(position).getTitle());

        String deadline = tasksList.get(position).getDeadline();
        holder.itemTaskDeadline.setText(deadline.substring(0, deadline.length() - 3));
        if (tasksList.get(position).getCompleted() == 1) {
            holder.itemCard.setAlpha(0.4f);
        }
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

}
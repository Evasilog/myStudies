package com.example.mystudies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InstructorsRecyclerAdapter extends RecyclerView.Adapter<InstructorsRecyclerAdapter.ViewHolder> {

    private static List<Instructor> instructorsList;
    private static OnInstructorClickListener onInstructorClickListener;

    public interface OnInstructorClickListener {
        void onInstructorClick(int position, List<Instructor> list);
    }

    public InstructorsRecyclerAdapter(List<Instructor> instructorsList, OnInstructorClickListener onInstructorClickListener){
        InstructorsRecyclerAdapter.instructorsList = new ArrayList<>(instructorsList);
        InstructorsRecyclerAdapter.onInstructorClickListener = onInstructorClickListener;
    }

    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemInstructorName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemInstructorName = itemView.findViewById(R.id.item_instructor_name);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                onInstructorClickListener.onInstructorClick(position, instructorsList);
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public InstructorsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_instructor_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(InstructorsRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemInstructorName.setText(instructorsList.get(position).getLastName() + " " + instructorsList.get(position).getFirstName());
    }

    @Override
    public int getItemCount() {
        return instructorsList.size();
    }

}

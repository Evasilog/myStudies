package com.example.mystudies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    private static List<Note> notesList;

    public NotesRecyclerAdapter(List<Note> notesList){
        NotesRecyclerAdapter.notesList = new ArrayList<>(notesList);
    }

    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNoteTitle;
        TextView itemNoteUpdatedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNoteTitle = itemView.findViewById(R.id.item_note_title);
            itemNoteUpdatedAt = itemView.findViewById(R.id.item_note_updated_at);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                Intent intent = new Intent(v.getContext(), NoteFormActivity.class);
                intent.putExtra("note_id", notesList.get(position).getID());
                v.getContext().startActivity(intent);
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public NotesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_note_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NotesRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemNoteTitle.setText(notesList.get(position).getTitle());
        holder.itemNoteUpdatedAt.setText(notesList.get(position).getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}
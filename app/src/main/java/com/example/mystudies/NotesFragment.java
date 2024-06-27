package com.example.mystudies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesFragment extends Fragment implements NotesRecyclerAdapter.OnNoteClickListener {

    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> adapter;
    TextView totalNotes;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        addNoteBtn = view.findViewById(R.id.add_note);
        addNoteBtn.setAlpha(0.75f);
        addNoteBtn.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), NoteFormActivity.class), 1));

        totalNotes = view.findViewById(R.id.total_notes);

        FragmentActivity context = requireActivity();

        recyclerView = view.findViewById(R.id.notes_recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
        List<Note> notes = dbHandler.getNotes();

        //Set my Adapter for the RecyclerView
        adapter = new NotesRecyclerAdapter(notes, this);
        recyclerView.setAdapter(adapter);

        totalNotes.setText(adapter.getItemCount() == 1 ? adapter.getItemCount() + " " + getResources().getString(R.string.note).toLowerCase() : adapter.getItemCount() + " " + getResources().getString(R.string.nav_menu_notes).toLowerCase());

        dbHandler.close();

        return view;
    }

    @Override
    public void onNoteClick(int position, List<Note> notes) {
        Intent intent = new Intent(getContext(), NoteFormActivity.class);
        intent.putExtra("note_id", notes.get(position).getID());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            FragmentActivity context = requireActivity();
            MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
            List<Note> notes = dbHandler.getNotes();

            //Set my Adapter for the RecyclerView
            adapter = new NotesRecyclerAdapter(notes, this);
            recyclerView.setAdapter(adapter);
            totalNotes.setText(adapter.getItemCount() == 1 ? adapter.getItemCount() + " " + getResources().getString(R.string.note).toLowerCase() : adapter.getItemCount() + " " + getResources().getString(R.string.nav_menu_notes).toLowerCase());

            dbHandler.close();
        }
    }
}
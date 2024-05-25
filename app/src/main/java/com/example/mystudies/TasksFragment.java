package com.example.mystudies;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TasksFragment extends Fragment {

    FloatingActionButton addTaskBtn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder> adapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        addTaskBtn = view.findViewById(R.id.add_task);
        addTaskBtn.setAlpha(0.75f);
        addTaskBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), TaskFormActivity.class)));

        FragmentActivity context = requireActivity();

        recyclerView = view.findViewById(R.id.tasks_recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
        List<Task> tasks = dbHandler.getTasks();

        //Set my Adapter for the RecyclerView
        adapter = new TasksRecyclerAdapter(tasks);
        recyclerView.setAdapter(adapter);

        dbHandler.close();

        return view;
    }
}
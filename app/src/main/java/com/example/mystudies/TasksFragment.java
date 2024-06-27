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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TasksFragment extends Fragment implements TasksRecyclerAdapter.OnTaskClickListener {

    FloatingActionButton addTaskBtn;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder> adapter;
    TextView totalTasks;

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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        addTaskBtn = view.findViewById(R.id.add_task);
        addTaskBtn.setAlpha(0.75f);
        addTaskBtn.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), TaskFormActivity.class), 1));

        totalTasks = view.findViewById(R.id.total_tasks);

        FragmentActivity context = requireActivity();

        recyclerView = view.findViewById(R.id.tasks_recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
        List<Task> tasks = dbHandler.getTasks();

        //Set my Adapter for the RecyclerView
        adapter = new TasksRecyclerAdapter(tasks, this);
        recyclerView.setAdapter(adapter);

        int total_tasks = tasks.size();

        int completed_tasks = 0;

        for (Task task: tasks) {
            if (task.getCompleted() == 1) {
                completed_tasks++;
            }
        }

        float progress = (float) completed_tasks / total_tasks * 100;

        progressBar = view.findViewById(R.id.tasksProgressBar);

        progressBar.setProgress(Math.round(progress));

        totalTasks.setText(completed_tasks + "/" + total_tasks + " " + getResources().getString(R.string.completed_tasks).toLowerCase());

        dbHandler.close();

        return view;
    }

    @Override
    public void onTaskClick(int position, List<Task> tasks) {
        Intent intent = new Intent(getContext(), TaskFormActivity.class);
        intent.putExtra("task_id", tasks.get(position).getID());
        startActivityForResult(intent, 1);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            FragmentActivity context = requireActivity();
            MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
            List<Task> tasks = dbHandler.getTasks();

            //Set my Adapter for the RecyclerView
            adapter = new TasksRecyclerAdapter(tasks, this);
            recyclerView.setAdapter(adapter);

            int total_tasks = tasks.size();

            int completed_tasks = 0;

            for (Task task: tasks) {
                if (task.getCompleted() == 1) {
                    completed_tasks++;
                }
            }

            float progress = (float) completed_tasks / total_tasks * 100;

            progressBar.setProgress(Math.round(progress));

            totalTasks.setText(completed_tasks + "/" + total_tasks + " " + getResources().getString(R.string.completed_tasks).toLowerCase());

            dbHandler.close();
        }
    }
}
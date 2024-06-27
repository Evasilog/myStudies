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

public class InstructorsFragment extends Fragment implements InstructorsRecyclerAdapter.OnInstructorClickListener {

    FloatingActionButton addInstructorBtn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<InstructorsRecyclerAdapter.ViewHolder> adapter;
    TextView totalInstructors;

    public InstructorsFragment() {
        // Required empty public constructor
    }

    public static InstructorsFragment newInstance() {
        return new InstructorsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_instructors, container, false);

        addInstructorBtn = view.findViewById(R.id.add_instructor);
        addInstructorBtn.setAlpha(0.75f);
        addInstructorBtn.setOnClickListener(v -> startActivityForResult(new Intent(getActivity(), InstructorFormActivity.class), 1));

        totalInstructors = view.findViewById(R.id.total_instructors);

        FragmentActivity context = requireActivity();

        recyclerView = view.findViewById(R.id.instructors_recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
        List<Instructor> instructors = dbHandler.getInstructors();

        //Set my Adapter for the RecyclerView
        adapter = new InstructorsRecyclerAdapter(instructors, this);
        recyclerView.setAdapter(adapter);

        totalInstructors.setText(adapter.getItemCount() == 1 ? adapter.getItemCount() + " " + getResources().getString(R.string.instructor).toLowerCase() : adapter.getItemCount() + " " + getResources().getString(R.string.nav_menu_instructors).toLowerCase());

        dbHandler.close();

        return view;
    }

    @Override
    public void onInstructorClick(int position, List<Instructor> instructors) {
        Intent intent = new Intent(getContext(), InstructorFormActivity.class);
        intent.putExtra("instructor_id", instructors.get(position).getID());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            FragmentActivity context = requireActivity();
            MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
            List<Instructor> instructors = dbHandler.getInstructors();

            //Set my Adapter for the RecyclerView
            adapter = new InstructorsRecyclerAdapter(instructors, this);
            recyclerView.setAdapter(adapter);
            totalInstructors.setText(adapter.getItemCount() == 1 ? adapter.getItemCount() + " " + getResources().getString(R.string.instructor).toLowerCase() : adapter.getItemCount() + " " + getResources().getString(R.string.nav_menu_instructors).toLowerCase());

            dbHandler.close();
        }
    }
}
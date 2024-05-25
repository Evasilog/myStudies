package com.example.mystudies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class HomeFragment extends Fragment {

    FloatingActionButton addCourseBtn;
    TextView progressValue, weightedGradeAverageValue, gradeAverageValue, passedCoursesValue, passedEctsValue;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolder> adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addCourseBtn = view.findViewById(R.id.add_course);
        addCourseBtn.setAlpha(0.75f);
        addCourseBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), CourseFormActivity.class)));

        FragmentActivity context = requireActivity();

        recyclerView = view.findViewById(R.id.recycler_view);

        // Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
        List<Course> courses = dbHandler.getCourses();

        dbHandler.close();

        int total_courses = courses.size();

        int passed_courses = 0;

        float passed_ects = 0;

        float sum_passed_ects_x_grades = 0;

        float sum_passed_grades = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        float total_ects = Float.parseFloat(sharedPreferences.getString("preference_ects", "240"));
        float passing_grade = Float.parseFloat(sharedPreferences.getString("preference_passing_grade", "5"));

        for (Course course: courses) {
            if (course.getGrade() >= passing_grade || course.getHasGrade() == 0) {
                passed_courses++;
                passed_ects += course.getEcts();
                if (course.getHasGrade() == 1) {
                    sum_passed_ects_x_grades += course.getEcts() * course.getGrade();
                    sum_passed_grades += course.getGrade();
                }
            }
        }

        progressValue = view.findViewById(R.id.progress_value);
        progressBar =  view.findViewById(R.id.progressBar);
        weightedGradeAverageValue = view.findViewById(R.id.weighted_grade_average_value);
        gradeAverageValue = view.findViewById(R.id.grade_average_value);
        passedCoursesValue = view.findViewById(R.id.passed_courses_value);
        passedEctsValue = view.findViewById(R.id.passed_ects_value);

        float weighted_grade_average = passed_ects > 0 ? sum_passed_ects_x_grades/passed_ects : 0;
        float grade_average = passed_courses > 0 ? sum_passed_grades/passed_courses : 0;
        float progress = passed_ects/total_ects * 100;

        progressBar.setProgress(Math.round(progress));

        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalSeparatorAlwaysShown(false);

        String progress_value = df.format(passed_ects)+"/"+df.format(total_ects);
        progressValue.setText(progress_value);
        if(progress_value.length() > 5 && progress_value.length() <= 7) {
            progressValue.setTextSize(24);
        } else if (progress_value.length() > 7) {
            progressValue.setTextSize(20);
        }

        weightedGradeAverageValue.setText(df.format(weighted_grade_average));
        gradeAverageValue.setText(df.format(grade_average));
        passedCoursesValue.setText(passed_courses+"/"+total_courses);
        passedEctsValue.setText(df.format(passed_ects)+"/"+df.format(total_ects));


        //Set my Adapter for the RecyclerView
        adapter = new CoursesRecyclerAdapter(courses);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
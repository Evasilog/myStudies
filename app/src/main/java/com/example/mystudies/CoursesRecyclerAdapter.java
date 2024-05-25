package com.example.mystudies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolder> {

    private static List<Course> coursesList;

    public CoursesRecyclerAdapter(List<Course> coursesList){
        CoursesRecyclerAdapter.coursesList = new ArrayList<>(coursesList);
    }

    //Class that holds the items to be displayed (Views in card_layout)
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemGrade;
        TextView itemSemester;
        TextView itemEcts;
        TextView itemInstructor;
        ImageView iconSemester, iconEcts, iconInstructor;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_course_title);
            itemGrade = itemView.findViewById(R.id.item_course_grade);
            itemSemester = itemView.findViewById(R.id.item_course_semester);
            itemEcts = itemView.findViewById(R.id.item_course_ects);
            itemInstructor = itemView.findViewById(R.id.item_course_instructor);

            iconSemester = itemView.findViewById(R.id.icon_course_semester);
            iconSemester.setColorFilter(ContextCompat.getColor(iconSemester.getContext(), R.color.success_tint));
            iconEcts = itemView.findViewById(R.id.icon_course_ects);
            iconEcts.setColorFilter(ContextCompat.getColor(iconEcts.getContext(), R.color.success_tint));
            iconInstructor = itemView.findViewById(R.id.icon_course_instructor);
            iconInstructor.setColorFilter(ContextCompat.getColor(iconInstructor.getContext(), R.color.success_tint));

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                Intent intent = new Intent(v.getContext(), CourseFormActivity.class);
                intent.putExtra("course_id",coursesList.get(position).getID());
                v.getContext().startActivity(intent);
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @NonNull
    @Override
    public CoursesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_course_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(CoursesRecyclerAdapter.ViewHolder holder, int position) {
        Course course = coursesList.get(position);

        holder.itemTitle.setText(course.getTitle());

        float grade = course.getGrade();

        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalSeparatorAlwaysShown(false);

        String formattedNumber = df.format(grade);

        int has_grade = course.getHasGrade();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemGrade.getContext());
        float passing_grade = Float.parseFloat(sharedPreferences.getString("preference_passing_grade", "5"));

        if (grade >= passing_grade || has_grade == 0) {
            holder.itemGrade.setTextColor(ContextCompat.getColor(holder.itemGrade.getContext(), R.color.success));
        }

        holder.itemGrade.setText(formattedNumber);

        if (has_grade == 0) {
            holder.itemGrade.setText("✓");
            holder.itemGrade.setTextSize(20);
        }

        holder.itemSemester.setText(ordinal(course.getSemester(), holder.itemSemester.getResources()).toLowerCase());

        formattedNumber = df.format(course.getEcts());

        holder.itemEcts.setText(formattedNumber);

        Instructor instructor = course.getInstructor(holder.itemEcts.getContext());

        if (instructor != null) {
            holder.itemInstructor.setText(instructor.getLastName() + " " + instructor.getFirstName());
        } else {
            holder.iconInstructor.setVisibility(View.GONE);
            holder.itemInstructor.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    private static String ordinal(int i, Resources res) {
        String[] suffixes = new String[] { res.getString(R.string.number_th), res.getString(R.string.number_st), res.getString(R.string.number_nd), res.getString(R.string.number_rd), res.getString(R.string.number_th), res.getString(R.string.number_th), res.getString(R.string.number_th), res.getString(R.string.number_th), res.getString(R.string.number_th), res.getString(R.string.number_th) };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + res.getString(R.string.number_th) + " " + res.getString(R.string.semester);
            default:
                return i + suffixes[i % 10] + " " + res.getString(R.string.semester);
        }
    }
}

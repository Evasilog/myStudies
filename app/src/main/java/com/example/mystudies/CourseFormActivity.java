package com.example.mystudies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CourseFormActivity extends AppCompatActivity {

    Boolean is_edit;
    int id;
    Course course = null;
    ImageButton backIcon;
    TextView toolbarTitle;
    ImageButton deleteIcon;
    EditText titleInput;
    Spinner semesterInput;
    EditText ectsInput;
    Spinner typeInput;
    EditText hoursInput;
    EditText detailsInput;
    EditText gradeInput;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch hasGradeInput;
    Spinner instructorIdInput;

    SpinnerItem[] courseTypes;

    SpinnerItem[] courseInstructors;


    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("course_id",0);
        is_edit = id != 0;

        //Get references to view objects
        backIcon = findViewById(R.id.course_back_icon);
        toolbarTitle = findViewById(R.id.course_toolbar_title);
        deleteIcon = findViewById(R.id.course_delete);
        titleInput = findViewById(R.id.course_title);
        semesterInput = findViewById(R.id.course_semester);
        ectsInput = findViewById(R.id.course_ects);
        typeInput = findViewById(R.id.course_type);
        hoursInput = findViewById(R.id.course_hours);
        detailsInput = findViewById(R.id.course_details);
        gradeInput = findViewById(R.id.course_grade);
        hasGradeInput = findViewById(R.id.course_has_grade);
        instructorIdInput = findViewById(R.id.course_instructor_id);

        backIcon.setOnClickListener(v -> finish());

        deleteIcon.setOnClickListener(v -> {
            if (is_edit) {
                MyDBHandler dbHandler = new MyDBHandler(v.getContext(), null, null, 1);

                boolean result = dbHandler.deleteCourse(id);
                dbHandler.close();
                if (result) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("fragment","courses");
                    setResult(RESULT_OK, intent1);
                    finish();
                }
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int total_semesters = Integer.parseInt(sharedPreferences.getString("preference_semesters", "8"));

        Number[] semesters = new Number[total_semesters];

        for (int i = 0; i < total_semesters ; i++) {
            semesters[i] = i + 1;
        }

        ArrayAdapter<Number> adapterNumber = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesters);
        adapterNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterInput.setAdapter(adapterNumber);

        courseTypes = new SpinnerItem[2];
        String[] course_types_labels = getResources().getStringArray(R.array.course_types_labels);

        courseTypes[0] = new SpinnerItem();
        courseTypes[0].setValue("Core");
        courseTypes[0].setLabel(course_types_labels[0]);

        courseTypes[1] = new SpinnerItem();
        courseTypes[1].setValue("Elective");
        courseTypes[1].setLabel(course_types_labels[1]);

        ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeInput.setAdapter(adapter);

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        List<Instructor> instructors = dbHandler.getInstructors();

        courseInstructors = new SpinnerItem[instructors.size()+1];

        courseInstructors[0] = new SpinnerItem();
        courseInstructors[0].setValue("0");
        courseInstructors[0].setLabel("");

        for(int i = 0; i < instructors.size(); i++) {
            courseInstructors[i+1] = new SpinnerItem();
            courseInstructors[i+1].setValue(Integer.toString(instructors.get(i).getID()));
            courseInstructors[i+1].setLabel(instructors.get(i).getLastName() + " " + instructors.get(i).getFirstName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseInstructors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instructorIdInput.setAdapter(adapter);

        if (is_edit) {
            course = dbHandler.findCourse(id);

            toolbarTitle.setText(course.getTitle());
            toolbarTitle.setTextSize(20);

            titleInput.setText(course.getTitle());
            semesterInput.setSelection(course.getSemester()-1);
            ectsInput.setText(Float.toString(course.getEcts()));
            hoursInput.setText(Float.toString(course.getHours()));
            gradeInput.setText(Float.toString(course.getGrade()));
            hasGradeInput.setChecked(course.getHasGrade()==1);
            detailsInput.setText(course.getDetails());
        } else {
            deleteIcon.setVisibility(View.GONE);
        }
        dbHandler.close();
    }

    //OnClick method for "Save" button
    public void saveCourse (View view) {
        boolean isAllFieldsChecked = checkFields();

        if (isAllFieldsChecked) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            String title = titleInput.getText().toString();
            int semester = Integer.parseInt(semesterInput.getSelectedItem().toString());
            float ects = Float.parseFloat(ectsInput.getText().toString().equals("") ? "0" : ectsInput.getText().toString());
            String type = courseTypes[typeInput.getSelectedItemPosition()].getValue();
            float hours = Float.parseFloat(hoursInput.getText().toString().equals("") ? "0" : hoursInput.getText().toString());
            String details = detailsInput.getText().toString();
            float grade = Float.parseFloat(gradeInput.getText().toString().equals("") ? "5" : gradeInput.getText().toString());
            int has_grade = hasGradeInput.isChecked() ? 1 : 0;
            if (has_grade == 0 && grade < 5) {
                grade = 5;
            }
            int instructorId = Integer.parseInt(courseInstructors[instructorIdInput.getSelectedItemPosition()].getValue());
            Course form_course = new Course(title,semester,ects,type,hours,details,grade,has_grade,instructorId);

            if (is_edit) {
                dbHandler.updateCourse(id, form_course);
            } else {
                dbHandler.createCourse(form_course);
            }
            dbHandler.close();

            Intent intent = new Intent();
            intent.putExtra("fragment","courses");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean checkFields() {
        if (titleInput.length() == 0) {
            titleInput.setError("This field is required");
            return false;
        }

        if (ectsInput.length() == 0) {
            ectsInput.setError("This field is required");
            return false;
        }

        float grade_value = Float.parseFloat(gradeInput.getText().toString().equals("") ? "0" : gradeInput.getText().toString());
        if (gradeInput.length() != 0 && grade_value < 0) {
            gradeInput.setError("Grade >= 0");
            return false;
        } else if (gradeInput.length() != 0 && grade_value > 10) {
            gradeInput.setError("Grade <= 10");
            return false;
        }

        return true;
    }
}
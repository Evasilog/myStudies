package com.example.mystudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class InstructorFormActivity extends AppCompatActivity {
    Boolean is_edit;
    int id;
    Instructor instructor = null;
    ImageButton backIcon;
    TextView toolbarTitle;
    ImageButton deleteIcon;
    EditText firstNameInput;
    EditText lastNameInput;
    EditText emailInput;
    EditText phoneInput;
    EditText detailsInput;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("instructor_id",0);
        is_edit = id != 0;

        //Get references to view objects
        backIcon = findViewById(R.id.instructor_back_icon);
        toolbarTitle = findViewById(R.id.instructor_toolbar_title);
        deleteIcon = findViewById(R.id.instructor_delete);
        firstNameInput = findViewById(R.id.instructor_first_name);
        lastNameInput = findViewById(R.id.instructor_last_name);
        emailInput = findViewById(R.id.instructor_email);
        phoneInput = findViewById(R.id.instructor_phone);
        detailsInput = findViewById(R.id.instructor_details);

        backIcon.setOnClickListener(v -> finish());

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        deleteIcon.setOnClickListener(v -> {
            if (is_edit) {
                boolean result = dbHandler.deleteInstructor(id);
                if (result) {
                    finish();
                    Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                    intent1.putExtra("fragment","instructors");
                    intent1.putExtra("snackBar","Instructor \"" + instructor.getLastName() + "\" deleted");
                    v.getContext().startActivity(intent1);
                }
                dbHandler.close();
            }
        });

        if (is_edit) {
            instructor = dbHandler.findInstructor(id);

            toolbarTitle.setText(instructor.getLastName() + " " + instructor.getFirstName());
            toolbarTitle.setTextSize(24);

            firstNameInput.setText(instructor.getFirstName());
            lastNameInput.setText(instructor.getLastName());
            emailInput.setText(instructor.getEmail());
            phoneInput.setText(instructor.getPhone());
            detailsInput.setText(instructor.getDetails());
        } else {
            deleteIcon.setVisibility(View.GONE);
        }
        dbHandler.close();
    }

    // OnClick method for "Save" button
    public void saveInstructor (View view) {
        boolean isAllFieldsChecked = checkFields();

        if (isAllFieldsChecked) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            String first_name = firstNameInput.getText().toString();
            String last_name = lastNameInput.getText().toString();
            String email = emailInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String details = detailsInput.getText().toString();

            Instructor form_instructor = new Instructor(first_name,last_name,email,phone,details);

            if (is_edit) {
                dbHandler.updateInstructor(id, form_instructor);
            } else {
                dbHandler.createInstructor(form_instructor);
            }
            dbHandler.close();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment","instructors");
            startActivity(intent);
        }
    }

    private boolean checkFields() {
        if (firstNameInput.length() == 0) {
            firstNameInput.setError("This field is required");
            return false;
        }

        if (lastNameInput.length() == 0) {
            lastNameInput.setError("This field is required");
            return false;
        }

        return true;
    }
}
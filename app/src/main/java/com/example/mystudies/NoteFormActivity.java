package com.example.mystudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteFormActivity extends AppCompatActivity {

    Boolean is_edit;
    int id;
    Note note = null;
    ImageButton backIcon;
    TextView toolbarTitle;
    ImageButton deleteIcon;
    EditText titleInput;
    EditText detailsInput;
    SimpleDateFormat dateFormat;
    Calendar now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("note_id",0);
        is_edit = id != 0;

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        now = Calendar.getInstance();

        //Get references to view objects
        backIcon = findViewById(R.id.note_back_icon);
        toolbarTitle = findViewById(R.id.note_toolbar_title);
        deleteIcon = findViewById(R.id.note_delete);
        titleInput = findViewById(R.id.note_title);
        detailsInput = findViewById(R.id.note_details);

        backIcon.setOnClickListener(v -> finish());

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        deleteIcon.setOnClickListener(v -> {
            if (is_edit) {
                boolean result = dbHandler.deleteNote(id);
                dbHandler.close();
                if (result) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("fragment","notes");
                    setResult(RESULT_OK, intent1);
                    finish();
                }
            }
        });

        if (is_edit) {
            note = dbHandler.findNote(id);

            toolbarTitle.setText(note.getTitle());
            toolbarTitle.setTextSize(24);

            titleInput.setText(note.getTitle());
            detailsInput.setText(note.getDetails());
        } else {
            deleteIcon.setVisibility(View.GONE);
        }
        dbHandler.close();
    }

    // OnClick method for "Save" button
    public void saveNote (View view) {
        boolean isAllFieldsChecked = checkFields();

        if (isAllFieldsChecked) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            String title = titleInput.getText().toString();
            String details = detailsInput.getText().toString();
            String updated_at = dateFormat.format(now.getTime());

            Note form_note = new Note(title, details, updated_at, updated_at);

            if (is_edit) {
                dbHandler.updateNote(id, form_note);
            } else {
                dbHandler.createNote(form_note);
            }
            dbHandler.close();

            Intent intent = new Intent();
            intent.putExtra("fragment","notes");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean checkFields() {
        if (titleInput.length() == 0) {
            titleInput.setError("This field is required");
            return false;
        }

        return true;
    }
}
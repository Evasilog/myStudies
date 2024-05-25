package com.example.mystudies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TaskFormActivity extends AppCompatActivity {

    Boolean is_edit;
    int id;
    Task task = null;
    ImageButton backIcon;
    TextView toolbarTitle;
    ImageButton deleteIcon;
    EditText titleInput;
    ImageButton deadlineDate;
    TextView deadlineDateInput;
    ImageButton deadlineTime;
    TextView deadlineTimeInput;
    ImageButton reminderDate;
    TextView reminderDateInput;
    ImageButton reminderTime;
    TextView reminderTimeInput;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch completedInput;
    EditText detailsInput;
    SimpleDateFormat dateFormat;
    Calendar now;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        Intent intent = getIntent();
        id = intent.getIntExtra("task_id",0);
        is_edit = id != 0;

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        now = Calendar.getInstance();

        //Get references to view objects
        backIcon = findViewById(R.id.task_back_icon);
        toolbarTitle = findViewById(R.id.task_toolbar_title);
        deleteIcon = findViewById(R.id.task_delete);
        titleInput = findViewById(R.id.task_title);
        deadlineDate = findViewById(R.id.task_deadline_date_btn);
        deadlineDateInput = findViewById(R.id.task_deadline_date);
        deadlineTime = findViewById(R.id.task_deadline_time_btn);
        deadlineTimeInput = findViewById(R.id.task_deadline_time);
        reminderDate = findViewById(R.id.task_reminder_date_btn);
        reminderDateInput = findViewById(R.id.task_reminder_date);
        reminderTime = findViewById(R.id.task_reminder_time_btn);
        reminderTimeInput = findViewById(R.id.task_reminder_time);
        completedInput = findViewById(R.id.task_completed);
        detailsInput = findViewById(R.id.task_details);

        backIcon.setOnClickListener(v -> finish());

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        deleteIcon.setOnClickListener(v -> {
            if (is_edit) {
                boolean result = dbHandler.deleteTask(id);
                if (result) {
                    finish();
                    Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                    intent1.putExtra("fragment","tasks");
                    intent1.putExtra("snackBar","Task \"" + task.getTitle() + "\" deleted");
                    v.getContext().startActivity(intent1);
                }
                dbHandler.close();
            }
        });

        deadlineDate.setOnClickListener(v -> {
            int current_year = now.get(Calendar.YEAR);
            int current_month = now.get(Calendar.MONTH);
            int current_day = now.get(Calendar.DAY_OF_MONTH);

            String dateString = (String) deadlineDateInput.getText();
            if (!dateString.equals(getResources().getString(R.string.select_date))) {
                String[] dateParts = dateString.split("/");
                current_day = Integer.parseInt(dateParts[0]);
                current_month = Integer.parseInt(dateParts[1])-1;
                current_year = Integer.parseInt(dateParts[2]);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
                deadlineDateInput.setText(getFormattedDate(day,month,year));
            }, current_year, current_month, current_day);
            datePickerDialog.show();
        });

        deadlineTime.setOnClickListener(v -> {
            int current_hourOfDay = now.get(Calendar.HOUR_OF_DAY);
            int current_minute = now.get(Calendar.MINUTE);

            String timeString = (String) deadlineTimeInput.getText();
            if (!timeString.equals(getResources().getString(R.string.select_date))) {
                String[] timeParts = timeString.split(":");
                current_hourOfDay = Integer.parseInt(timeParts[0]);
                current_minute = Integer.parseInt(timeParts[1]);
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hourOfDay, minute) -> {
                deadlineTimeInput.setText(getFormattedTime(hourOfDay,minute));
            },current_hourOfDay,current_minute,true);
            timePickerDialog.show();
        });

        reminderDate.setOnClickListener(v -> {
            int current_year = now.get(Calendar.YEAR);
            int current_month = now.get(Calendar.MONTH);
            int current_day = now.get(Calendar.DAY_OF_MONTH);

            String dateString = (String) reminderDateInput.getText();
            if (!dateString.equals(getResources().getString(R.string.select_date))) {
                String[] dateParts = dateString.split("/");
                current_day = Integer.parseInt(dateParts[0]);
                current_month = Integer.parseInt(dateParts[1])-1;
                current_year = Integer.parseInt(dateParts[2]);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
                reminderDateInput.setText(getFormattedDate(day,month,year));
            }, current_year, current_month, current_day);
            datePickerDialog.show();
        });

        reminderTime.setOnClickListener(v -> {
            int current_hourOfDay = now.get(Calendar.HOUR_OF_DAY);
            int current_minute = now.get(Calendar.MINUTE);

            String timeString = (String) reminderTimeInput.getText();
            if (!timeString.equals(getResources().getString(R.string.select_date))) {
                String[] timeParts = timeString.split(":");
                current_hourOfDay = Integer.parseInt(timeParts[0]);
                current_minute = Integer.parseInt(timeParts[1]);
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hourOfDay, minute) -> {
                reminderTimeInput.setText(getFormattedTime(hourOfDay,minute));
            },current_hourOfDay,current_minute,true);
            timePickerDialog.show();
        });

        if (is_edit) {
            task = dbHandler.findTask(id);

            toolbarTitle.setText(task.getTitle());
            toolbarTitle.setTextSize(24);

            titleInput.setText(task.getTitle());


            int task_year, task_month, task_day, task_hour, task_minute;
            String taskDateTime, datePart, timePart;
            String[] dateTimeParts, dateParts, timeParts;

            taskDateTime = task.getDeadline();
            if (!taskDateTime.equals("")) {
                dateTimeParts = taskDateTime.split(" ");

                datePart = dateTimeParts[0];
                dateParts = datePart.split("-");
                task_year = Integer.parseInt(dateParts[0]);
                task_month = Integer.parseInt(dateParts[1])-1;
                task_day = Integer.parseInt(dateParts[2]);

                deadlineDateInput.setText(getFormattedDate(task_day, task_month, task_year));

                timePart = dateTimeParts[1];
                timeParts = timePart.split(":");
                task_hour = Integer.parseInt(timeParts[0]);
                task_minute = Integer.parseInt(timeParts[1]);

                deadlineTimeInput.setText(getFormattedTime(task_hour, task_minute));
            }

            taskDateTime = task.getReminder();
            if (!taskDateTime.equals("")) {
                dateTimeParts = taskDateTime.split(" ");

                datePart = dateTimeParts[0];
                dateParts = datePart.split("-");
                task_year = Integer.parseInt(dateParts[0]);
                task_month = Integer.parseInt(dateParts[1])-1;
                task_day = Integer.parseInt(dateParts[2]);

                reminderDateInput.setText(getFormattedDate(task_day, task_month, task_year));

                timePart = dateTimeParts[1];
                timeParts = timePart.split(":");
                task_hour = Integer.parseInt(timeParts[0]);
                task_minute = Integer.parseInt(timeParts[1]);

                reminderTimeInput.setText(getFormattedTime(task_hour, task_minute));
            }

            completedInput.setChecked(task.getCompleted()==1);
            detailsInput.setText(task.getDetails());
        } else {
            deleteIcon.setVisibility(View.GONE);
        }
        dbHandler.close();
    }

    // OnClick method for "Save" button
    public void saveTask (View view) {
        boolean isAllFieldsChecked = checkFields();

        if (isAllFieldsChecked) {
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            String title = titleInput.getText().toString();

            String deadlineDate = deadlineDateInput.getText().toString();
            String deadlineTime = deadlineTimeInput.getText().toString();
            deadlineTime = deadlineTime.equals(getResources().getString(R.string.select_date)) ? "12:00" : deadlineTime;
            String deadline = deadlineDate.equals(getResources().getString(R.string.select_date)) ? "" : getFormattedDateTimeDB(deadlineDate, deadlineTime);

            String reminderDate = reminderDateInput.getText().toString();
            String reminderTime = reminderTimeInput.getText().toString();
            reminderTime = reminderTime.equals(getResources().getString(R.string.select_date)) ? "12:00" : reminderTime;
            String reminder = reminderDate.equals(getResources().getString(R.string.select_date)) ? "" : getFormattedDateTimeDB(reminderDate, reminderTime);

            String details = detailsInput.getText().toString();
            int completed = completedInput.isChecked() ? 1 : 0;
            String updated_at = dateFormat.format(now.getTime());

            Task form_task = new Task(title,deadline,reminder,completed,details, updated_at, updated_at);

            if (is_edit) {
                dbHandler.updateTask(id, form_task);
            } else {
                dbHandler.createTask(form_task);
            }
            dbHandler.close();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment","tasks");
            startActivity(intent);
        }
    }

    private boolean checkFields() {
        if (titleInput.length() == 0) {
            titleInput.setError("This field is required");
            return false;
        }

        return true;
    }

    private String getFormattedDate(int day, int month, int year) {
        return (day<10 ? "0" : "") + day + '/' + ((month+1)<10 ? "0" : "") + (month+1) + '/' + year;
    }

    private String getFormattedTime(int hourOfDay, int minute) {
        return (hourOfDay<10 ? "0" : "") + hourOfDay + ':' + ((minute+1)<10 ? "0" : "") + minute;
    }

    private String getFormattedDateTimeDB(String date, String time) {
        String[] dateParts = date.split("/");
        return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0] + " " + time + ":00" ;
    }
}
package com.example.mystudies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myStudies.db";
    public static final String TABLE_COURSES = "courses";
    public static final String COURSES_COLUMN_ID = "_id";
    public static final String COURSES_COLUMN_TITLE = "title";
    public static final String COURSES_COLUMN_SEMESTER = "semester";
    public static final String COURSES_COLUMN_ECTS = "ects";
    public static final String COURSES_COLUMN_TYPE = "type";
    public static final String COURSES_COLUMN_HOURS = "hours";
    public static final String COURSES_COLUMN_DETAILS = "details";
    public static final String COURSES_COLUMN_GRADE = "grade";
    public static final String COURSES_COLUMN_HAS_GRADE = "has_grade";
    public static final String COURSES_COLUMN_INSTRUCTOR_ID = "instructor_id";
    public static final String COURSES_COLUMN_CREATED_AT = "created_at";
    public static final String COURSES_COLUMN_UPDATED_AT = "updated_at";
    public static final String TABLE_INSTRUCTORS = "instructors";
    public static final String INSTRUCTORS_COLUMN_ID = "_id";
    public static final String INSTRUCTORS_COLUMN_FIRST_NAME = "first_name";
    public static final String INSTRUCTORS_COLUMN_LAST_NAME = "last_name";
    public static final String INSTRUCTORS_COLUMN_EMAIL = "email";
    public static final String INSTRUCTORS_COLUMN_PHONE = "phone";
    public static final String INSTRUCTORS_COLUMN_DETAILS = "details";
    public static final String INSTRUCTORS_COLUMN_CREATED_AT = "created_at";
    public static final String INSTRUCTORS_COLUMN_UPDATED_AT = "updated_at";
    public static final String TABLE_TASKS = "tasks";
    public static final String TASKS_COLUMN_ID = "_id";
    public static final String TASKS_COLUMN_TITLE = "title";
    public static final String TASKS_COLUMN_DEADLINE = "deadline";
    public static final String TASKS_COLUMN_REMINDER = "reminder";
    public static final String TASKS_COLUMN_COMPLETED = "completed";
    public static final String TASKS_COLUMN_DETAILS = "details";
    public static final String TASKS_COLUMN_CREATED_AT = "created_at";
    public static final String TASKS_COLUMN_UPDATED_AT = "updated_at";
    public static final String TABLE_NOTES = "notes";
    public static final String NOTES_COLUMN_ID = "_id";
    public static final String NOTES_COLUMN_TITLE = "title";
    public static final String NOTES_COLUMN_DETAILS = "details";
    public static final String NOTES_COLUMN_CREATED_AT = "created_at";
    public static final String NOTES_COLUMN_UPDATED_AT = "updated_at";

    // Constructor
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Create DB (tables: instructors, courses, tasks, notes)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INSTRUCTORS_TABLE = "CREATE TABLE " +
                TABLE_INSTRUCTORS + "(" +
                INSTRUCTORS_COLUMN_ID + " INTEGER PRIMARY KEY," +
                INSTRUCTORS_COLUMN_FIRST_NAME + " TEXT," +
                INSTRUCTORS_COLUMN_LAST_NAME + " TEXT," +
                INSTRUCTORS_COLUMN_EMAIL + " TEXT," +
                INSTRUCTORS_COLUMN_PHONE + " TEXT," +
                INSTRUCTORS_COLUMN_DETAILS + " TEXT," +
                INSTRUCTORS_COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                INSTRUCTORS_COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_INSTRUCTORS_TABLE);

        String CREATE_COURSES_TABLE = "CREATE TABLE " +
                TABLE_COURSES + "(" +
                COURSES_COLUMN_ID + " INTEGER PRIMARY KEY," +
                COURSES_COLUMN_TITLE + " TEXT," +
                COURSES_COLUMN_SEMESTER + " INTEGER DEFAULT 0," +
                COURSES_COLUMN_ECTS + " FLOAT DEFAULT 0," +
                COURSES_COLUMN_TYPE + " TEXT," +
                COURSES_COLUMN_HOURS + " TEXT," +
                COURSES_COLUMN_DETAILS + " TEXT," +
                COURSES_COLUMN_GRADE + " FLOAT DEFAULT 0," +
                COURSES_COLUMN_HAS_GRADE + " BOOLEAN DEFAULT 1," +
                COURSES_COLUMN_INSTRUCTOR_ID + " INTEGER DEFAULT 0," +
                COURSES_COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                COURSES_COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "foreign key(" + COURSES_COLUMN_INSTRUCTOR_ID + ") references " + TABLE_INSTRUCTORS + "(" + INSTRUCTORS_COLUMN_ID + ") on delete SET NULL on update CASCADE" + ")";
        db.execSQL(CREATE_COURSES_TABLE);

        String CREATE_TASKS_TABLE = "CREATE TABLE " +
                TABLE_TASKS + "(" +
                TASKS_COLUMN_ID + " INTEGER PRIMARY KEY," +
                TASKS_COLUMN_TITLE + " TEXT," +
                TASKS_COLUMN_DEADLINE + " DATETIME," +
                TASKS_COLUMN_REMINDER + " DATETIME," +
                TASKS_COLUMN_COMPLETED + " INTEGER," +
                TASKS_COLUMN_DETAILS + " TEXT," +
                TASKS_COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                TASKS_COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_NOTES_TABLE = "CREATE TABLE " +
                TABLE_NOTES + "(" +
                NOTES_COLUMN_ID + " INTEGER PRIMARY KEY," +
                NOTES_COLUMN_TITLE + " TEXT," +
                NOTES_COLUMN_DETAILS + " TEXT," +
                NOTES_COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                NOTES_COLUMN_UPDATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    // Upgrade DB: Delete and recreate DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    // Method to create a Course in DB
    public void createCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(COURSES_COLUMN_TITLE, course.getTitle());
        values.put(COURSES_COLUMN_SEMESTER, course.getSemester());
        values.put(COURSES_COLUMN_ECTS, course.getEcts());
        values.put(COURSES_COLUMN_TYPE, course.getType());
        values.put(COURSES_COLUMN_HOURS, course.getHours());
        values.put(COURSES_COLUMN_DETAILS, course.getDetails());
        values.put(COURSES_COLUMN_GRADE, course.getGrade());
        values.put(COURSES_COLUMN_HAS_GRADE, course.getHasGrade());
        values.put(COURSES_COLUMN_INSTRUCTOR_ID, course.getInstructorId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_COURSES, null, values);
        db.close();
    }

    // Method to find a Course with course_id in DB
    public Course findCourse(int id) {
        String query = "SELECT * FROM " + TABLE_COURSES + " WHERE " +
                COURSES_COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Course course = new Course();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            course.setID(cursor.getInt(0));
            course.setTitle(cursor.getString(1));
            course.setSemester(cursor.getInt(2));
            course.setEcts(cursor.getFloat(3));
            course.setType(cursor.getString(4));
            course.setHours(cursor.getFloat(5));
            course.setDetails(cursor.getString(6));
            course.setGrade(cursor.getFloat(7));
            course.setHasGrade(cursor.getInt(8));
            course.setInstructorId(cursor.getInt(9));
            cursor.close();
        } else {
            course = null;
        }
        db.close();
        return course;
    }

    // Method to update a Course in DB
    public void updateCourse(int id, Course course) {
        ContentValues values = new ContentValues();
        values.put(COURSES_COLUMN_TITLE, course.getTitle());
        values.put(COURSES_COLUMN_SEMESTER, course.getSemester());
        values.put(COURSES_COLUMN_ECTS, course.getEcts());
        values.put(COURSES_COLUMN_TYPE, course.getType());
        values.put(COURSES_COLUMN_HOURS, course.getHours());
        values.put(COURSES_COLUMN_DETAILS, course.getDetails());
        values.put(COURSES_COLUMN_GRADE, course.getGrade());
        values.put(COURSES_COLUMN_HAS_GRADE, course.getHasGrade());
        values.put(COURSES_COLUMN_INSTRUCTOR_ID, course.getInstructorId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_COURSES, values, "_id=?", new String[]{ String.valueOf(id) });
        db.close();
    }

    // Method to delete a Course from DB
    public boolean deleteCourse(int id) {
        boolean result = false;
        Course course = findCourse(id);
        if (course != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_COURSES, COURSES_COLUMN_ID + " = ?", new String[] { String.valueOf(course.getID()) });
            result = true;
            db.close();
        }
        return result;
    }

    // Method to get all Courses from DB
    public List<Course> getCourses() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT *" + " FROM " + TABLE_COURSES + " ORDER BY " + COURSES_COLUMN_GRADE + " DESC, " + COURSES_COLUMN_HAS_GRADE + " DESC, " + COURSES_COLUMN_SEMESTER + " DESC, " + COURSES_COLUMN_TITLE + " DESC", null);

        List<Course> coursesList = new ArrayList<>();

        // get data from DB to create each Course and store it in arrayList
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.setID(cursor.getInt(0));
            course.setTitle(cursor.getString(1));
            course.setSemester(cursor.getInt(2));
            course.setEcts(cursor.getFloat(3));
            course.setType(cursor.getString(4));
            course.setHours(cursor.getFloat(5));
            course.setDetails(cursor.getString(6));
            course.setGrade(cursor.getFloat(7));
            course.setHasGrade(cursor.getInt(8));
            course.setInstructorId(cursor.getInt(9));
            coursesList.add(course);
        }
        cursor.close();

        return coursesList;
    }

    // Method to create an Instructor in DB
    public void createInstructor(Instructor instructor) {
        ContentValues values = new ContentValues();
        values.put(INSTRUCTORS_COLUMN_FIRST_NAME, instructor.getFirstName());
        values.put(INSTRUCTORS_COLUMN_LAST_NAME, instructor.getLastName());
        values.put(INSTRUCTORS_COLUMN_EMAIL, instructor.getEmail());
        values.put(INSTRUCTORS_COLUMN_PHONE, instructor.getPhone());
        values.put(INSTRUCTORS_COLUMN_DETAILS, instructor.getDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INSTRUCTORS, null, values);
        db.close();
    }

    // Method to find an Instructor with instructor_id in DB
    public Instructor findInstructor(int id) {
        String query = "SELECT * FROM " + TABLE_INSTRUCTORS + " WHERE " +
                INSTRUCTORS_COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Instructor instructor = new Instructor();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            instructor.setID(cursor.getInt(0));
            instructor.setFirstName(cursor.getString(1));
            instructor.setLastName(cursor.getString(2));
            instructor.setEmail(cursor.getString(3));
            instructor.setPhone(cursor.getString(4));
            instructor.setDetails(cursor.getString(5));
            cursor.close();
        } else {
            instructor = null;
        }
        db.close();
        return instructor;
    }

    // Method to update an Instructor in DB
    public void updateInstructor(int id, Instructor instructor) {
        ContentValues values = new ContentValues();
        values.put(INSTRUCTORS_COLUMN_FIRST_NAME, instructor.getFirstName());
        values.put(INSTRUCTORS_COLUMN_LAST_NAME, instructor.getLastName());
        values.put(INSTRUCTORS_COLUMN_EMAIL, instructor.getEmail());
        values.put(INSTRUCTORS_COLUMN_PHONE, instructor.getPhone());
        values.put(INSTRUCTORS_COLUMN_DETAILS, instructor.getDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_INSTRUCTORS, values, "_id=?", new String[]{ String.valueOf(id) });
        db.close();
    }

    // Method to delete an Instructor from DB
    public boolean deleteInstructor(int id) {
        boolean result = false;
        Instructor instructor = findInstructor(id);
        if (instructor != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_INSTRUCTORS, INSTRUCTORS_COLUMN_ID + " = ?", new String[] { String.valueOf(instructor.getID()) });
            result = true;
            db.close();
        }
        return result;
    }

    // Method to get all Instructors from DB
    public List<Instructor> getInstructors() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT *" + " FROM " + TABLE_INSTRUCTORS + " ORDER BY " + INSTRUCTORS_COLUMN_LAST_NAME + " ASC, " + INSTRUCTORS_COLUMN_FIRST_NAME + " DESC", null);

        List<Instructor> instructorsList = new ArrayList<>();

        // get data from DB to create each Instructor and store it in arrayList
        while (cursor.moveToNext()) {
            Instructor instructor = new Instructor();
            instructor.setID(cursor.getInt(0));
            instructor.setFirstName(cursor.getString(1));
            instructor.setLastName(cursor.getString(2));
            instructor.setEmail(cursor.getString(3));
            instructor.setPhone(cursor.getString(4));
            instructor.setDetails(cursor.getString(5));
            instructorsList.add(instructor);
        }
        cursor.close();

        return instructorsList;
    }

    // Method to create a Task in DB
    public void createTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(TASKS_COLUMN_TITLE, task.getTitle());
        values.put(TASKS_COLUMN_DEADLINE, task.getDeadline());
        values.put(TASKS_COLUMN_REMINDER, task.getReminder());
        values.put(TASKS_COLUMN_COMPLETED, task.getCompleted());
        values.put(TASKS_COLUMN_DETAILS, task.getDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Method to find a Task with task_id in DB
    public Task findTask(int id) {
        String query = "SELECT * FROM " + TABLE_TASKS + " WHERE " +
                TASKS_COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Task task = new Task();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            task.setID(cursor.getInt(0));
            task.setTitle(cursor.getString(1));
            task.setDeadline(cursor.getString(2));
            task.setReminder(cursor.getString(3));
            task.setCompleted(cursor.getInt(4));
            task.setDetails(cursor.getString(5));
            task.setCreatedAt(cursor.getString(6));
            task.setUpdatedAt(cursor.getString(7));
            cursor.close();
        } else {
            task = null;
        }
        db.close();
        return task;
    }

    // Method to update a Task in DB
    public void updateTask(int id, Task task) {
        ContentValues values = new ContentValues();
        values.put(TASKS_COLUMN_TITLE, task.getTitle());
        values.put(TASKS_COLUMN_DEADLINE, task.getDeadline());
        values.put(TASKS_COLUMN_REMINDER, task.getReminder());
        values.put(TASKS_COLUMN_COMPLETED, task.getCompleted());
        values.put(TASKS_COLUMN_DETAILS, task.getDetails());
        values.put(TASKS_COLUMN_UPDATED_AT, task.getUpdatedAt());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_TASKS, values, "_id=?", new String[]{ String.valueOf(id) });
        db.close();
    }

    // Method to delete a Task from DB
    public boolean deleteTask(int id) {
        boolean result = false;
        Task task = findTask(id);
        if (task != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_TASKS, TASKS_COLUMN_ID + " = ?", new String[] { String.valueOf(task.getID()) });
            result = true;
            db.close();
        }
        return result;
    }

    // Method to get all Tasks from DB
    public List<Task> getTasks() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT *" + " FROM " + TABLE_TASKS + " ORDER BY " + TASKS_COLUMN_CREATED_AT + " ASC", null);

        List<Task> tasksList = new ArrayList<>();

        // get data from DB to create each Task and store it in arrayList
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setID(cursor.getInt(0));
            task.setTitle(cursor.getString(1));
            task.setDeadline(cursor.getString(2));
            task.setReminder(cursor.getString(3));
            task.setCompleted(cursor.getInt(4));
            task.setDetails(cursor.getString(5));
            task.setCreatedAt(cursor.getString(6));
            task.setUpdatedAt(cursor.getString(7));
            tasksList.add(task);
        }
        cursor.close();

        return tasksList;
    }

    // Method to create a Note in DB
    public void createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_TITLE, note.getTitle());
        values.put(NOTES_COLUMN_DETAILS, note.getDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    // Method to find a Note with note_id in DB
    public Note findNote(int id) {
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " +
                NOTES_COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Note note = new Note();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            note.setID(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setDetails(cursor.getString(2));
            note.setCreatedAt(cursor.getString(3));
            note.setUpdatedAt(cursor.getString(4));
            cursor.close();
        } else {
            note = null;
        }
        db.close();
        return note;
    }

    // Method to update a Note in DB
    public void updateNote(int id, Note note) {
        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_TITLE, note.getTitle());
        values.put(NOTES_COLUMN_DETAILS, note.getDetails());
        values.put(NOTES_COLUMN_UPDATED_AT, note.getUpdatedAt());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NOTES, values, "_id=?", new String[]{ String.valueOf(id) });
        db.close();
    }

    // Method to delete a Note from DB
    public boolean deleteNote(int id) {
        boolean result = false;
        Note note = findNote(id);
        if (note != null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NOTES, NOTES_COLUMN_ID + " = ?", new String[] { String.valueOf(note.getID()) });
            result = true;
            db.close();
        }
        return result;
    }

    // Method to get all Notes from DB
    public List<Note> getNotes() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor;

        cursor = db.rawQuery("SELECT *" + " FROM " + TABLE_NOTES + " ORDER BY " + NOTES_COLUMN_UPDATED_AT + " DESC", null);

        List<Note> notesList = new ArrayList<>();

        // get data from DB to create each Note and store it in arrayList
        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setID(cursor.getInt(0));
            note.setTitle(cursor.getString(1));
            note.setDetails(cursor.getString(2));
            note.setCreatedAt(cursor.getString(3));
            note.setUpdatedAt(cursor.getString(4));
            notesList.add(note);
        }
        cursor.close();

        return notesList;
    }
}
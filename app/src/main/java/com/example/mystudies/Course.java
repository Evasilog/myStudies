package com.example.mystudies;

import android.content.Context;

public class Course {
    private int _id;
    private String _title;
    private int _semester;
    private float _ects;
    private String _type;
    private float _hours;
    private String _details;
    private float _grade;
    private int _has_grade;
    private int _instructor_id;

    public Course() {
    }

    public Course(String title, int semester, float ects, String type, float hours, String details, float grade, int has_grade, int instructor_id) {
        this._title = title;
        this._semester = semester;
        this._ects = ects;
        this._type = type;
        this._hours = hours;
        this._details = details;
        this._grade = grade;
        this._has_grade = has_grade;
        this._instructor_id = instructor_id;
    }

    public int getID() {
        return _id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getTitle() {
        return this._title;
    }

    public void setSemester(int semester) {
        this._semester = semester;
    }

    public int getSemester() {
        return this._semester;
    }

    public void setEcts(float ects) {
        this._ects = ects;
    }

    public float getEcts() {
        return this._ects;
    }

    public void setType(String type) {
        this._type = type;
    }

    public String getType() {
        return this._type;
    }

    public void setHours(float hours) {
        this._hours = hours;
    }

    public float getHours() {
        return this._hours;
    }

    public void setDetails(String details) {
        this._details = details;
    }

    public String getDetails() {
        return this._details;
    }

    public void setGrade(float grade) {
        this._grade = grade;
    }

    public float getGrade() {
        return this._grade;
    }

    public void setHasGrade(int has_grade) {
        this._has_grade = has_grade;
    }

    public int getHasGrade() {
        return this._has_grade;
    }

    public void setInstructorId(int instructor_id) {
        this._instructor_id = instructor_id;
    }

    public int getInstructorId() {
        return this._instructor_id;
    }

    public Instructor getInstructor(Context context) {
        if (this._instructor_id != 0) {
            MyDBHandler dbHandler = new MyDBHandler(context, null, null, 1);
            Instructor instructor = dbHandler.findInstructor(this._instructor_id);
            dbHandler.close();
            return instructor;
        }
        return null;
    }

}
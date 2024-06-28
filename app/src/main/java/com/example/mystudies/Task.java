package com.example.mystudies;

public class Task {
    private int _id;
    private String _title;
    private String _deadline;
    private int _completed;
    private String _details;
    private String _created_at;
    private String _updated_at;

    public Task() {
    }

    public Task(String title, String deadline, int completed, String details, String created_at, String updated_at) {
        this._title = title;
        this._deadline = deadline;
        this._completed = completed;
        this._details = details;
        this._created_at = created_at;
        this._updated_at = updated_at;
    }

    public int getID() {
        return _id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getDeadline() {
        return _deadline;
    }

    public void setDeadline(String deadline) {
        this._deadline = deadline;
    }

    public int getCompleted() {
        return _completed;
    }

    public void setCompleted(int completed) {
        this._completed = completed;
    }

    public void setDetails(String details) {
        this._details = details;
    }

    public String getDetails() {
        return this._details;
    }

    public void setUpdatedAt(String updated_at) {
        this._updated_at = updated_at;
    }

    public String getUpdatedAt() {
        return this._updated_at;
    }

    public void setCreatedAt(String created_at) {
        this._created_at = created_at;
    }

    public String getCreatedAt() {
        return this._created_at;
    }
}

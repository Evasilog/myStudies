package com.example.mystudies;

public class Note {
    private int _id;
    private String _title;
    private String _details;
    private String _created_at;
    private String _updated_at;

    public Note() {
    }

    public Note(String title, String details,String created_at, String updated_at) {
        this._title = title;
        this._details = details;
        this._created_at = created_at;
        this._updated_at = updated_at;
    }

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public String getTitle() {
        return this._title;
    }

    public void setDetails(String details) {
        this._details = details;
    }

    public String getDetails() {
        return this._details;
    }

    public void setCreatedAt(String created_at) {
        this._created_at = created_at;
    }

    public String getCreatedAt() {
        return this._created_at;
    }

    public void setUpdatedAt(String updated_at) {
        this._updated_at = updated_at;
    }

    public String getUpdatedAt() {
        return this._updated_at;
    }
}

package com.example.mystudies;

public class Instructor {
    private int _id;
    private String _first_name;
    private String _last_name;
    private String _email;
    private String _phone;
    private String _details;

    public Instructor() {
    }

    public Instructor(String first_name, String last_name, String email, String phone, String details) {
        this._first_name = first_name;
        this._last_name = last_name;
        this._email = email;
        this._phone = phone;
        this._details = details;
    }

    public int getID() {
        return _id;
    }

    public void setID(int _id) {
        this._id = _id;
    }

    public void setFirstName(String first_name) {
        this._first_name = first_name;
    }

    public String getFirstName() {
        return this._first_name;
    }

    public void setLastName(String last_name) {
        this._last_name = last_name;
    }

    public String getLastName() {
        return this._last_name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getEmail() {
        return this._email;
    }

    public void setPhone(String phone) {
        this._phone = phone;
    }

    public String getPhone() {
        return this._phone;
    }

    public void setDetails(String details) {
        this._details = details;
    }

    public String getDetails() {
        return this._details;
    }

}

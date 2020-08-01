package com.example.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anupamchugh on 09/01/17.
 */

public class User {

    @SerializedName("RollNo")
    public String rollno;
    @SerializedName("ExamEvent")
    public String examevent;
    @SerializedName("FirstName")
    public String fname;
    @SerializedName("Test City")
    public String city;
    @SerializedName("TestId")
    public int testid;
    @SerializedName("Status")
    public Boolean status;

    public User(String rollno, String examevent, String fname, String city, int testid, Boolean status) {
        this.rollno = rollno;
        this.examevent = examevent;
        this.fname = fname;
        this.city = city;
        this.testid = testid;
        this.status = status;
    }


}

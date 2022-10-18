package com.example.collegeerp;

public class AdminFee {
    String course,totalfees,outstanding,paid;

    public AdminFee(String course, String totalfees, String outstanding, String paid) {
        this.course = course;
        this.totalfees = totalfees;
        this.outstanding = outstanding;
        this.paid = paid;
    }

    public String getCourse() {
    return course;
    }

    public String getTotalfees() {
        return totalfees;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public String getPaid() {
        return paid;
    }
}
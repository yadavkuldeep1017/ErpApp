package com.example.collegeerp;

import java.io.Serializable;

public class AssignmentClass implements Serializable {
    String assgnum;
    String assgquestion;

    public String getAssgnum() {
        return assgnum;
    }

    public void setAssgnum(String assgnum) {
        this.assgnum = assgnum;
    }

    public String getAssgquestion() {
        return assgquestion;
    }

    public void setAssgquestion(String assgquestion) {
        this.assgquestion = assgquestion;
    }

}

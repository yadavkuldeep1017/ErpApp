package com.example.collegeerp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAssignment extends ArrayAdapter<AssignmentClass> {

    Activity context;
    ArrayList<AssignmentClass> arrayList;
    public CustomAssignment(Activity context, int resource, ArrayList<AssignmentClass> objects) {
        super(context, resource, objects);
        arrayList=objects;
        this.context= context;
    }
    @Override
    public int getCount() {
       return arrayList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.assignment_layout, null);
        TextView num=(TextView) v.findViewById(R.id.assignumber);
        TextView quest=(TextView) v.findViewById(R.id.question);
        num.setText("Assignment "+arrayList.get(position).getAssgnum());
        quest.setText("Question:- "+arrayList.get(position).getAssgquestion());
        return v;
    }
}

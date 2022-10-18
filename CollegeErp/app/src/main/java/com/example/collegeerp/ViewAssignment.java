package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ViewAssignment extends AppCompatActivity {

    ListView l1;
    ArrayList<AssignmentClass> arrayList = new ArrayList<>();
    String course = "", year = "";
    Faculty f;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignment);
        l1 = findViewById(R.id.listassg);
        Bundle msg = getIntent().getExtras();
        f = (Faculty) msg.get("Faculty");
        course = msg.getString("Course");
        year = msg.getString("Year");
        addData();

    }

    private void addData() {
        reference = FirebaseDatabase.getInstance().getReference("Assignment").child(course).child(year).child(f.getPrn());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    AssignmentClass a = data.getValue(AssignmentClass.class);
                    arrayList.add(a);
                }
                CustomAssignment viewAssignment = new CustomAssignment(ViewAssignment.this, R.layout.assignment_layout, arrayList);
                l1.setAdapter(viewAssignment);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
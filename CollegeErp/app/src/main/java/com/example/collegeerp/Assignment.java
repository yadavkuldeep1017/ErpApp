package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Assignment extends AppCompatActivity {

    ListView list;
    DatabaseReference reference;
    ArrayList<AssignmentClass> assignment=new ArrayList<>();
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Bundle msg=getIntent().getExtras();
        student=(Student) msg.get("Student");
        list=findViewById(R.id.listassg);
        setContent();
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(Assignment.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(Assignment.this,StudentProfile.class);
                                intent.putExtra("Student",student);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(Assignment.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void setContent(){
        reference= FirebaseDatabase.getInstance().getReference("Assignment").child(student.getCourse()).child(student.getYear());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                        for(DataSnapshot dat:data.getChildren()){
                            AssignmentClass a=new AssignmentClass();
                            a.setAssgnum(dat.child("assgnum").getValue().toString());
                            a.setAssgquestion(dat.child("assgquestion").getValue().toString());
                            assignment.add(a);
                        }
                    }
                    CustomAssignment cass=new CustomAssignment(Assignment.this,R.layout.assignment_layout,assignment);
                    list.setAdapter(cass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
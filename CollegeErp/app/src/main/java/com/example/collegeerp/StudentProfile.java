package com.example.collegeerp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
public class StudentProfile extends AppCompatActivity {
    TextView name,course,prn,rollno,studyyear,gmail,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        name=(TextView) findViewById(R.id.name2);
        course=(TextView) findViewById(R.id.course2);
        prn=(TextView)findViewById(R.id.prno2);
        rollno=(TextView)findViewById(R.id.rollno2);
        studyyear=(TextView)findViewById(R.id.year2);
        gmail=(TextView) findViewById(R.id.gmail2);
        gender=(TextView) findViewById(R.id.gender2);
        Bundle msg=getIntent().getExtras();
        Student s=(Student) msg.get("Student");
        name.setText(s.getName());
        course.setText(s.getCourse());
        prn.setText(s.getPrn());
        rollno.setText(s.getRoll());
        studyyear.setText(s.getYear());
        gmail.setText(s.getEmail());
        gender.setText(s.getGender());
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(StudentProfile.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(StudentProfile.this,StudentProfile.class);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(StudentProfile.this,LoginActivity.class);
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
}
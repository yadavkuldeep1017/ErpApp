package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.Serializable;

public class StudentDash extends AppCompatActivity {

    CardView crd1,crd2,crd3,crd4,crd5,crd6;
    ImageView img1,img2,img3,img4,img5,img6;
    Student student;
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);
        crd1 = findViewById(R.id.card1);crd5=findViewById(R.id.card5);
        crd2=findViewById(R.id.card2);crd6=findViewById(R.id.card6);
        crd3=findViewById(R.id.card3);crd4=findViewById(R.id.card4);
        head=findViewById(R.id.head);
        Bundle msg=getIntent().getExtras();
        student=(Student) msg.getSerializable("Student");
        head.setText("Welcome "+student.getName());
        crd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stintent = new Intent(StudentDash.this,RetrieveNews.class);
                startActivity(stintent);
            }
        });
        crd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stintent = new Intent(StudentDash.this,TimeTable.class);
                startActivity(stintent);
            }
        });
        crd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res=new Intent(StudentDash.this,ResultActivity.class);
                res.putExtra("Student",student);
                startActivity(res);
            }
        });
        crd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res=new Intent(StudentDash.this,StudentFee.class);
                res.putExtra("Student",student);
                startActivity(res);
            }
        });
        crd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res=new Intent(StudentDash.this,Assignment.class);
                res.putExtra("Student",student);
                startActivity(res);
            }
        });
        crd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentDash.this,PrintNotificationActivity.class);
                intent.putExtra("Student",student);
                startActivity(intent);
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(StudentDash.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(StudentDash.this,StudentProfile.class);
                                intent.putExtra("Student",student);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(StudentDash.this,LoginActivity.class);
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
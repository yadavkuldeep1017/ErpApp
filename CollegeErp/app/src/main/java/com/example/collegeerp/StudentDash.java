package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StudentDash extends AppCompatActivity {

    CardView crd1,crd2,crd3,crd4,crd5,crd6;
    ImageView img1,img2,img3,img4,img5,img6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);
        crd1 = (CardView) findViewById(R.id.card1);
        crd3=(CardView)findViewById(R.id.card3);
        crd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stintent = new Intent(StudentDash.this,NewsActivity.class);
                startActivity(stintent);
            }
        });
        crd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res=new Intent(StudentDash.this,Result.class);
                startActivity(res);
            }
        });
    }
}
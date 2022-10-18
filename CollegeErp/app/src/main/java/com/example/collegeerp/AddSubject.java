package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubject extends AppCompatActivity {

    EditText sub1,sub2,sub3,sub4;
    Spinner course,year;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        course=findViewById(R.id.course);
        year=findViewById(R.id.yer);
        sub1=findViewById(R.id.sub1);
        sub2=findViewById(R.id.sub2);
        sub3=findViewById(R.id.sub3);
        sub4=findViewById(R.id.sub4);
        btn=findViewById(R.id.sub);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                    addSubject();
                else{
                    Intent intent=new Intent(AddSubject.this,AddSubject.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(AddSubject.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(AddSubject.this,AdminDash.class);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(AddSubject.this,LoginActivity.class);
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
    private boolean validate(){
        if(TextUtils.isEmpty(sub1.getText())){
            Toast.makeText(this, "Please Enter Subject 1", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(sub2.getText())){
            Toast.makeText(this, "Please Enter Subject 2", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(sub3.getText())){
            Toast.makeText(this, "Please Enter Subject 3", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(sub4.getText())){
            Toast.makeText(this, "Please Enter Subject 4", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void addSubject(){
        String s1=sub1.getText().toString();
        String s2=sub2.getText().toString();
        String s3=sub3.getText().toString();
        String s4=sub4.getText().toString();
        String crse=String.valueOf(course.getSelectedItem());
        String yer=String.valueOf(year.getSelectedItem());
        Subject s=new Subject();
        s.setSub1(s1);s.setSub2(s2);s.setSub3(s3);s.setSub4(s4);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Subject");
        reference.child(crse).child(yer).setValue(s);
        Toast.makeText(this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,AdminDash.class));
        finish();
    }
}
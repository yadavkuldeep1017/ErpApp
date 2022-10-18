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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFaculty extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;
    Button b1;
    DatabaseReference reference;
    Faculty faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        e1=findViewById(R.id.editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPersonName2);
        e3=findViewById(R.id.editTextTextPersonName3);
        e4=findViewById(R.id.editTextTextPersonName5);
        e5=findViewById(R.id.editTextTextPersonName6);
        b1=findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                    addFaculty();
                else{
                    startActivity(new Intent(AddFaculty.this,AddFaculty.class));
                    finish();
                }
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(AddFaculty.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(AddFaculty.this,AdminDash.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.logout:intent=new Intent(AddFaculty.this,LoginActivity.class);
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
        if(TextUtils.isEmpty(e1.getText())){
            Toast.makeText(this, "Please Enter PRN", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(e2.getText())){
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(e3.getText())){
            Toast.makeText(this, "Please Enter Gmail", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(e4.getText())){
            Toast.makeText(this, "Please Enter Contact No", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(e5.getText())){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(e4.getText().toString().length()!=10){
            Toast.makeText(this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(e5.getText().toString().length()<7){
            Toast.makeText(this, "Password Minimum Length is 8 or more", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void addFaculty()
    {
        String prn,name,gmail,contactno;
        prn=e1.getText().toString();
        name=e2.getText().toString();
        gmail=e3.getText().toString();
        contactno=e4.getText().toString();
        reference= FirebaseDatabase.getInstance().getReference("Faculty");
        faculty=new Faculty();
        faculty.setPrn(prn);faculty.setPassword(e5.getText().toString());
        faculty.setName(name);
        faculty.setGmail(gmail);
        faculty.setContactno(contactno);
        reference.child(prn).setValue(faculty);
        Toast.makeText(this, "Faculty Data Added Successfully", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,AdminDash.class);
        startActivity(intent);
        finish();
    }
}
package com.example.collegeerp;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

     EditText name,prn,roll,pass,cnpass,email;
     Spinner course,year;
     Button Btn;
     DatabaseReference reference;
     Student student;
     RadioGroup rggrup;
     RadioButton gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        prn = findViewById(R.id.prn);
        roll = findViewById(R.id.roll);
        pass = findViewById(R.id.passwd);
        cnpass=findViewById(R.id.cpasswd);
        course=findViewById(R.id.crse);
        year=findViewById(R.id.year);
        email=findViewById(R.id.email);
        Btn = findViewById(R.id.btnregister);
        rggrup=findViewById(R.id.gender);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(RegisterActivity.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(RegisterActivity.this,AdminDash.class);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(RegisterActivity.this,LoginActivity.class);
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
    private void registerNewUser(){
        String nm,pr,rll,pss,cpss,crse,yer,em;
        nm = name.getText().toString();
        pr = prn.getText().toString();
        pss=pass.getText().toString();
        rll = roll.getText().toString();
        cpss=cnpass.getText().toString();
        em=email.getText().toString();
        crse=String.valueOf(course.getSelectedItem());
        yer=String.valueOf(year.getSelectedItem());
        if (TextUtils.isEmpty(nm)) {
            Toast.makeText(getApplicationContext(),"Please enter Name!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pr)) {
            Toast.makeText(getApplicationContext(),"Please enter PRN!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(rll)) {
            Toast.makeText(getApplicationContext(),"Please enter Roll No.!",Toast.LENGTH_LONG).show();
            return;
        }
        int rgselec=rggrup.getCheckedRadioButtonId();
        if(rgselec==-1){
            Toast.makeText(getApplicationContext(),"Please Select a gender!",Toast.LENGTH_LONG).show();
            return;
        }
        gender=findViewById(rgselec);
        String gen=gender.getText().toString();
        if (TextUtils.isEmpty(em)) {
            Toast.makeText(getApplicationContext(),"Please enter Email!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pss)) {
            Toast.makeText(getApplicationContext(),"Please enter Password",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(cpss)) {
            Toast.makeText(getApplicationContext(),"Please enter Confirm Password!",Toast.LENGTH_LONG).show();
            return;
        }
        if(!pss.equals(cpss)){
            Toast.makeText(getApplicationContext(),"Password and Confirm Password Not Matched!",Toast.LENGTH_LONG).show();
            return;
        }
        reference=FirebaseDatabase.getInstance().getReference("Student");
        student=new Student();
        student.setName(nm);student.setPasswd(pss);student.setPrn(pr);student.setRoll(rll);student.setCourse(crse);student.setYear(yer);student.setEmail(em);
        student.setGender(gen);
        reference.child(crse).child(pr).setValue(student);
        Toast.makeText(RegisterActivity.this, "Data Added Successfully", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,AdminDash.class);
        startActivity(intent);
    }
}
package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {

    TextView fg;
    EditText e1,e2;
    Button b1;
    String coursse="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.username);
        e2=(EditText)findViewById(R.id.passwdtxt);
        fg=(TextView)findViewById(R.id.forgotpasswd);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
        fg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
    }
    private void loginUserAccount(){
        String user=e1.getText().toString().trim();
        String pass=e2.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        String prn=user.substring(1,user.length());
        if(user.charAt(0)=='S') {
            studentLogin(prn,pass);
        }else if(user.charAt(0)=='E'){
            facultyLogin(prn,pass);
        }
        else if(user.equals("admin") && pass.equals("mitwpu")) {
            Toast.makeText(this, "Admin Login Successfull", Toast.LENGTH_SHORT).show();
            Intent l1 = new Intent(LoginActivity.this, AdminDash.class);
            startActivity(l1);
        }
        else{
            Toast.makeText(getApplicationContext(), "Login UnSuccessfull", Toast.LENGTH_LONG).show();
            e1.setText("");e2.setText("");
        }
    }
    private void forgotPassword(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        EditText emailet= new EditText(this);
        EditText prn=new EditText(this);
        prn.setHint("Enter Username");
        emailet.setHint("Enter Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(prn);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                String pr=prn.getText().toString();
                if(pr.charAt(0)=='S') {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                for(DataSnapshot data:dataSnapshot.getChildren()) {
                                    String pn = data.child("prn").getValue().toString();
                                    String em = data.child("email").getValue().toString();
                                    if (pn.equals(pr.substring(1, pr.length())) && em.equals(email)) {
                                        Student s=data.getValue(Student.class);
                                        System.out.println(s.getCourse());
                                        Toast.makeText(LoginActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, RecoverPassword.class);
                                        intent.putExtra("Flag","1");
                                        intent.putExtra("Student",s);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                Toast.makeText(LoginActivity.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(pr.charAt(0)=='E'){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Faculty");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data:snapshot.getChildren()) {

                                    String uname = data.child("prn").getValue().toString();
                                    String em = data.child("gmail").getValue().toString();
                                    if (uname.equals(pr.substring(1,pr.length())) && em.equals(email)) {
                                        Faculty f=(Faculty)data.getValue(Faculty.class);
                                        Toast.makeText(LoginActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, RecoverPassword.class);
                                        intent.putExtra("Flag", "2");
                                        intent.putExtra("Faculty",f);
                                        startActivity(intent);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void facultyLogin(String prn,String pass){
        System.out.println(prn+" "+pass);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean login=false ;
                for (DataSnapshot data : snapshot.getChildren()) {
                    String uname = data.child("prn").getValue().toString();
                    String upass = data.child("password").getValue().toString();
                    System.out.println(uname+" "+upass);
                    if (uname.equals(prn) && upass.equals(pass)) {
                        login = true;
                        break;
                    }
                }
                if (login) {
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Faculty").child(prn);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Faculty f=new Faculty();
                            for(DataSnapshot data:snapshot.getChildren()){
                                String key=data.getKey();
                                if(key.equals("name"))
                                    f.setName(data.getValue().toString());
                                else if(key.equals("gmail"))
                                    f.setGmail(data.getValue().toString());
                                else if(key.equals("contactno"))
                                    f.setContactno(data.getValue().toString());
                                else if(key.equals("prn"))
                                    f.setPrn(data.getValue().toString());
                                else if(key.equals("password"))
                                    f.setPassword(data.getValue().toString());
                            }
                            Intent l1 = new Intent(LoginActivity.this, FacultyDash.class);
                            l1.putExtra("Faculty", f);
                            startActivity(l1);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Login UnSuccessfull", Toast.LENGTH_LONG).show();
                    Intent l1 = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(l1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void studentLogin(String prn,String pass){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean login = false;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String uname = data.child("prn").getValue().toString();
                        String upass = data.child("passwd").getValue().toString();
                        System.out.println(uname+" "+upass);
                        if (uname.equals(prn) && upass.equals(pass)) {
                            login = true;
                            coursse=data.child("course").getValue().toString();
                            break;
                        }
                    }
                }
                if (login) {
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Student").child(coursse).child(prn);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Student s=new Student();
                            for(DataSnapshot data:snapshot.getChildren()){
                                String key=data.getKey();
                                if(key.equals("course"))
                                    s.setCourse(data.getValue().toString());
                                else if(key.equals("email"))
                                    s.setEmail(data.getValue().toString());
                                else if(key.equals("name"))
                                    s.setName(data.getValue().toString());
                                else if(key.equals("prn"))
                                    s.setPrn(data.getValue().toString());
                                else if(key.equals("passwd"))
                                    s.setPasswd(data.getValue().toString());
                                else if(key.equals("year"))
                                    s.setYear(data.getValue().toString());
                                else if(key.equals("roll"))
                                    s.setRoll(data.getValue().toString());
                                else
                                    s.setGender(data.getValue().toString());

                            }
                            Intent l1 = new Intent(LoginActivity.this, StudentDash.class);
                            l1.putExtra("Student", s);
                            startActivity(l1);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Login UnSuccessfull", Toast.LENGTH_LONG).show();
                    Intent l1 = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(l1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

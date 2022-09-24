package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.signin);
        e1=(EditText)findViewById(R.id.user);
        e2=(EditText)findViewById(R.id.pass);
        bar=(ProgressBar)findViewById(R.id.progressBar);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });
    }
    private void loginUserAccount(){
        bar.setVisibility(View.VISIBLE);
        String user=e1.getText().toString().trim();
        String pass=e2.getText().toString().trim();
        if(user.charAt(0)=='S') {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean login = false;
                    if (TextUtils.isEmpty(user)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Username", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        return;
                    }
                    String prn=user.substring(1,user.length());
                    System.out.println(prn);
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String uname = data.child("prn").getValue().toString();
                        String upass = data.child("passwd").getValue().toString();
                        if (uname.equals(prn) && upass.equals(pass)) {
                            login = true;
                            break;
                        }
                    }
                    if (login) {
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        Intent l1 = new Intent(LoginActivity.this, StudentDash.class);
                        l1.putExtra("Username", user);
                        startActivity(l1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login UnSuccessfull", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.GONE);
                        Intent l1 = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(l1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else if(user.charAt(0)=='T'){

        }
        else{
            Toast.makeText(getApplicationContext(), "Login UnSuccessfull", Toast.LENGTH_LONG).show();
            bar.setVisibility(View.GONE);
        }
    }
}
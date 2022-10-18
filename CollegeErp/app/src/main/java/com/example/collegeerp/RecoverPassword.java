package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecoverPassword extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    Bundle msg;
    String flag;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        e1=(EditText) findViewById(R.id.rpass);
        e2=(EditText) findViewById(R.id.rcpass);
        b1=(Button) findViewById(R.id.updpass);
        msg=getIntent().getExtras();
        flag=msg.getString("Flag");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=e1.getText().toString();
                String cpass=e2.getText().toString();
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RecoverPassword.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cpass)){
                    Toast.makeText(RecoverPassword.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.equals(cpass)){
                    if(flag.equals("1")) {

                        Student s=(Student) msg.get("Student");
                        System.out.println(s.getCourse());
                        String prn=s.getPrn();
                        reference = FirebaseDatabase.getInstance().getReference("Student").child(s.getCourse());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String p = dataSnapshot.child("prn").getValue().toString();
                                    if (p.equals(prn)) {
                                        Toast.makeText(RecoverPassword.this, "Update Done", Toast.LENGTH_SHORT).show();
                                        Student s = dataSnapshot.getValue(Student.class);
                                        s.setPasswd(pass);
                                        reference.child(p).setValue(s);
                                        Toast.makeText(RecoverPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Intent intent = new Intent(RecoverPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        msg=getIntent().getExtras();
                        Faculty f=(Faculty)msg.get("Faculty");
                        String prn=f.getPrn();
                        reference = FirebaseDatabase.getInstance().getReference("Faculty").child(prn);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Faculty f = snapshot.getValue(Faculty.class);
                                    f.setPassword(pass);
                                    reference.setValue(f);
                                    Toast.makeText(RecoverPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Intent intent = new Intent(RecoverPassword.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(RecoverPassword.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
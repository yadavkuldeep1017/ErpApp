package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    String prn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        e1=(EditText) findViewById(R.id.rpass);
        e2=(EditText) findViewById(R.id.rcpass);
        b1=(Button) findViewById(R.id.updpass);
        Bundle msg=getIntent().getExtras();
        prn=msg.getString("PRN");
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
                    Toast.makeText(RecoverPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Student");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                String p=dataSnapshot.child("prn").getValue().toString();
                                if(p.equals(prn)){
                                    Toast.makeText(RecoverPassword.this, "Update Done", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(RecoverPassword.this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
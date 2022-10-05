package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddResultActivity extends AppCompatActivity {

    TextView sub1,sub2,sub3,sub4;
    EditText mark1,mark2,mark3,mark4;
    Button btn,clear;
    String pr="",course="",year="";
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);
        sub1=findViewById(R.id.t3);sub2=findViewById(R.id.t4);
        sub3=findViewById(R.id.t5);sub4=findViewById(R.id.t6);
        mark1=findViewById(R.id.e2);mark2=findViewById(R.id.e3);
        mark3=findViewById(R.id.e4);mark4=findViewById(R.id.e5);
        btn=findViewById(R.id.addresult);
        clear=findViewById(R.id.button);
        Bundle msg=getIntent().getExtras();
        pr=msg.getString("PRN");
        course=msg.getString("Course");
        year=msg.getString("Year");
        System.out.println(pr+" "+course+""+year);
        setSubject();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mark1.setText("");mark2.setText("");mark3.setText("");mark4.setText("");
            }
        });
    }
    private void setSubject(){
        reference=FirebaseDatabase.getInstance().getReference("Subject").child(course).child("FY");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> a=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    String value=data.getValue().toString();
                    a.add(value);
                }
                sub1.setText(a.get(0));sub2.setText(a.get(1));
                sub3.setText(a.get(2));sub4.setText(a.get(3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addResult(){
        reference=FirebaseDatabase.getInstance().getReference("Result");
        HashMap<String,String> map=new HashMap<>();
        map.put(sub1.getText().toString(),mark1.getText().toString());
        map.put(sub2.getText().toString(),mark2.getText().toString());
        map.put(sub3.getText().toString(),mark3.getText().toString());
        map.put(sub4.getText().toString(),mark4.getText().toString());
        reference.child(course).child(pr).setValue(map);
        Toast.makeText(this, "Result Added Successfully", Toast.LENGTH_SHORT).show();
    }
}
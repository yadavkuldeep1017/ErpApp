package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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
    Faculty f;
    Student s;
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
        s=(Student)msg.get("Student");
        course=s.getCourse();
        pr=s.getPrn();
        year=s.getYear();
        f=(Faculty)msg.get("Faculty");
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
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(AddResultActivity.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(AddResultActivity.this,FacultyProfile.class);
                                intent.putExtra("Faculty",f);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(AddResultActivity.this,LoginActivity.class);
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
    private void setSubject(){
        reference=FirebaseDatabase.getInstance().getReference("Subject").child(course).child(year);
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
        System.out.println(course+" "+pr);
        HashMap<String,String> map=new HashMap<>();
        map.put(sub1.getText().toString().trim(),mark1.getText().toString().trim());
        map.put(sub2.getText().toString().trim(),mark2.getText().toString().trim());
        map.put(sub3.getText().toString().trim(),mark3.getText().toString().trim());
        map.put(sub4.getText().toString().trim(),mark4.getText().toString().trim());
        reference.child(course).child(pr).setValue(map);
        mark1.setText("");mark2.setText("");mark3.setText("");mark4.setText("");
        Toast.makeText(this, "Result Added Successfully", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,FacultyDash.class);
        intent.putExtra("Faculty",f);
        startActivity(intent);
    }
}
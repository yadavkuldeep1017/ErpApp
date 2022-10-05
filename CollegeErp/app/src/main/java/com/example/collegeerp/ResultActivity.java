package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> result=new ArrayList<>();
    ListView listView;
    DatabaseReference reference;
    TextView name,pr,crse,roll,year,remark,per;
    String prn="";
    String course="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView=findViewById(R.id.tablelist);
        name=findViewById(R.id.name);
        pr=findViewById(R.id.prn);
        crse=findViewById(R.id.course);
        roll=findViewById(R.id.roll);
        year=findViewById(R.id.year);
        remark=findViewById(R.id.remark);
        per=findViewById(R.id.percent);
        Bundle msg=getIntent().getExtras();
        prn=msg.getString("prn");
        course=msg.getString("course");
        pr.setText(prn);crse.setText(course);
        setDetails();
        addResult();
    }
    private void setDetails(){
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Student").child(prn);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rll="",nam="",yr="";
                for(DataSnapshot data:snapshot.getChildren()){
                    String key=data.getKey();
                    if(key.equals("roll"))
                        rll=data.getValue().toString();
                    else if(key.equals("name"))
                        nam=data.getValue().toString();
                    else if(key.equals("year"))
                        yr=data.getValue().toString();
                }
                roll.setText(rll);name.setText(nam);year.setText(yr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addResult(){
        reference= FirebaseDatabase.getInstance().getReference().child("Result").child(course).child(prn);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=1;
                double totalmark=0;
                for(DataSnapshot data:snapshot.getChildren()){
                        HashMap<String,String > r=new HashMap<>();
                        String key=data.getKey().toString();
                        String value=data.getValue().toString();
                        totalmark+=Double.parseDouble(value);
                        System.out.println(key+" kvk "+value);
                        r.put(key,value);
                        result.add(r);
                }
                double percent=totalmark/5;
                per.setText(String.valueOf(percent));
                if(percent>33)
                    remark.setText("Pass");
                else
                    remark.setText("Fail");
                CustomResult customResult=new CustomResult(ResultActivity.this,R.layout.result_layout,result);
                listView.setAdapter(customResult);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
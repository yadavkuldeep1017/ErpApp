package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<ResultSheet> result=new ArrayList<>();
    ListView listView;
    DatabaseReference reference;
    String prn="1132210238";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView=findViewById(R.id.tablelist);
        addResult();
    }
    private void addResult(){
        reference= FirebaseDatabase.getInstance().getReference().child("Result");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=1;
                for(DataSnapshot data:snapshot.getChildren()){
                    String pr=data.child("prn").getValue().toString();
                    if(prn.equals(pr)) {
                        String sub = data.child("Subject").getValue().toString();
                        String mark = data.child("Mark").getValue().toString();
                        String code = data.child("Code").getValue().toString();
                        ResultSheet r = new ResultSheet();
                        r.setCode(code);r.setMark(mark);r.setSrno(i++);r.setSubject(sub);
                        result.add(r);
                    }
                }
                CustomResult customResult=new CustomResult(ResultActivity.this,R.layout.result_layout,result);
                listView.setAdapter(customResult);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
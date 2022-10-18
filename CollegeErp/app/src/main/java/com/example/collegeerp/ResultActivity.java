package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
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
    Student student;
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
        student=(Student) msg.get("Student");
        pr.setText(student.getPrn());crse.setText(student.getCourse());
        roll.setText(student.getRoll());name.setText(student.getName());year.setText(student.getYear());
        addResult();
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(ResultActivity.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(ResultActivity.this,StudentProfile.class);
                                intent.putExtra("Student",student);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.logout:intent=new Intent(ResultActivity.this,LoginActivity.class);
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
    private void addResult(){
        reference= FirebaseDatabase.getInstance().getReference("Result").child(student.getCourse()).child(student.getPrn());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                double totalmark=0;
                for(DataSnapshot data:snapshot.getChildren()){
                        HashMap<String,String > r=new HashMap<>();
                        String key=data.getKey().toString();
                        String value=data.getValue().toString();
                        totalmark+=Double.parseDouble(value);
                        i++;
                        r.put(key,value);
                        result.add(r);
                }
                double percent=totalmark/i;
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
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrintNotificationActivity extends AppCompatActivity {

    ListView l1;
    ArrayList<String> list=new ArrayList<>();
    DatabaseReference reference;
    Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_notification);
        l1=(ListView) findViewById(R.id.l1);
        Bundle msg=getIntent().getExtras();
        s=(Student)msg.get("Student");
        printNotification();
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(PrintNotificationActivity.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(PrintNotificationActivity.this,StudentProfile.class);
                                intent.putExtra("Student",s);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.logout:intent=new Intent(PrintNotificationActivity.this,LoginActivity.class);
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
    private void printNotification(){
        reference= FirebaseDatabase.getInstance().getReference("Notification").child(s.getCourse()).child(s.getYear());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    for(DataSnapshot da:data.getChildren()){
                        list.add(da.getValue().toString());
                    }
                }
                CustomNotif customNotif=new CustomNotif(PrintNotificationActivity.this,R.layout.printnotificationlayout,list);
                l1.setAdapter(customNotif);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
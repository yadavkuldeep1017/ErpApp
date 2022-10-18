package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NotificationActivity extends AppCompatActivity {
    Spinner s1,s2;
    EditText e1;
    Button b1, b2;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        s1=(Spinner) findViewById(R.id.s1);
        s2=(Spinner) findViewById(R.id.s2);
        e1=(EditText) findViewById(R.id.editText);
        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.button);
        reference= FirebaseDatabase.getInstance().getReference("Notification");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
            }
        });

    }

    private void addNotification()
    {
        String course=s1.getSelectedItem().toString();
        String year=s2.getSelectedItem().toString();
        String notification=e1.getText().toString();
        System.out.println(notification);
        HashMap<String, String> map=new HashMap<>();
        map.put("Notification",notification);
        System.out.println(map.get("Notification"));
        reference.child(course).child(year).push().setValue(map);
        Toast.makeText(this, "Notification Added", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,AdminDash.class);
        startActivity(intent);
    }
}
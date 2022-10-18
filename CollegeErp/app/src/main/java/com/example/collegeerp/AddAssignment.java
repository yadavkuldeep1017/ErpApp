package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AddAssignment extends AppCompatActivity {

    String course="",year="";
    String prn="";
    ImageView img;
    EditText quest,num;
    Button btn1,btn2;
    Faculty f;
    ImageButton menuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        quest=findViewById(R.id.quest);num=findViewById(R.id.assgnum);
        img=findViewById(R.id.assg);btn2=findViewById(R.id.assign);
        btn1=findViewById(R.id.button);
        Bundle msg=getIntent().getExtras();
        course=msg.getString("Course");
        year=msg.getString("Year");
        f=(Faculty) msg.get("Faculty");
        prn=f.getPrn();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssignment();
            }
        });

        menuButton=(ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(AddAssignment.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(AddAssignment.this,FacultyProfile.class);
                                intent.putExtra("Faculty",f);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.logout:intent=new Intent(AddAssignment.this,LoginActivity.class);
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
    private void addAssignment(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Assignment");
        AssignmentClass assig=new AssignmentClass();
        assig.setAssgnum(num.getText().toString());
        assig.setAssgquestion(quest.getText().toString());
        reference.child(course).child(year).child(prn).child("Assignment "+num.getText().toString()).setValue(assig);
        Toast.makeText(this, "Assignment Added Successfully", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,FacultyDash.class);
        intent.putExtra("Faculty",f);
        startActivity(intent);
        finish();
    }

}
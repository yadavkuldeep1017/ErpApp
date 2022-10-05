package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyDash extends AppCompatActivity {

    CardView c1,c2,c3,c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dash);
        c1=findViewById(R.id.card1);c3=findViewById(R.id.card3);
        c2=findViewById(R.id.card2);c4=findViewById(R.id.card4);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult();
            }
        });
    }
    private void addResult(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Result");
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        EditText prn=new EditText(this);
        prn.setHint("Enter PRN");
        prn.setMinEms(16);
        prn.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(prn);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("AddResult", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pn=prn.getText().toString().trim();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Student").child(pn);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String course="",year="";
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String key=dataSnapshot.getKey().toString();
                            if(key.equals("course")){
                                course=dataSnapshot.getValue().toString();
                            }
                            else if(key.equals("year"))
                                year=dataSnapshot.getValue().toString();
                        }
                        System.out.println(course+" "+year);
                        Intent intent=new Intent(FacultyDash.this,AddResultActivity.class);
                        intent.putExtra("PRN",pn);
                        intent.putExtra("Course",course);
                        intent.putExtra("Year",year);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FacultyDash.this, "Incorrect", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
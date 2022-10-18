package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

public class FeeDetails extends AppCompatActivity implements OnItemSelectedListener {
    public TextView name,cl,roll,fee;
    private DatabaseReference dbr;
    String pd="",outst="",fe="";
    String[] courses={"MCA","MCS"};
    Spinner sp;
    EditText totalfees;
    Button b1;
    Student student;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        sp = findViewById(R.id.spn);
        totalfees = findViewById(R.id.tfees);
        b1 = findViewById(R.id.btn1);
//        Bundle msg=getIntent().getExtras();
//        student=(Student)msg.get("Student");
        sp.setOnItemSelectedListener(FeeDetails.this);
        ArrayAdapter adp = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courses);
        adp.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adp);
        dbr = FirebaseDatabase.getInstance().getReference().child("FeeDetails");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFee();
                insertStudentFee();
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(FeeDetails.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(FeeDetails.this,AdminDash.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.logout:intent=new Intent(FeeDetails.this,LoginActivity.class);
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

    private void insertFee(){
        String course = sp.getSelectedItem().toString();
        String totalfee = totalfees.getText().toString();
        AdminFee adminFee = new AdminFee(course,totalfee,totalfee,"0");
        dbr.child(course).setValue(adminFee);
    }
    private void insertStudentFee(){
        dbr = FirebaseDatabase.getInstance().getReference("StudentFee").child(sp.getSelectedItem().toString());
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Student").child(sp.getSelectedItem().toString());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String key=data.getKey().toString();
                    String course = sp.getSelectedItem().toString();
                    String totalfee = totalfees.getText().toString();
                    AdminFee adminFee = new AdminFee(course,totalfee,totalfee,"0");
                    dbr.child(key).setValue(adminFee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(FeeDetails.this, "Fees Uploaded", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(FeeDetails.this,AdminDash.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),courses[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
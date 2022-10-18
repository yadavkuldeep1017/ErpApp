package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentFee extends AppCompatActivity {
    public TextView name, cl, roll, fee, outstand, paid;
    private DatabaseReference dbr;
    EditText e1;
    String pd = "", outst = "", fe = "";
    Button btn;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_fee);
        e1=findViewById(R.id.edit1);
        name = findViewById(R.id.stname);
        fee = findViewById(R.id.ttlfees);
        cl = findViewById(R.id.cls);
        outstand = findViewById(R.id.remain);
        roll = findViewById(R.id.rollno);
        paid = findViewById(R.id.paid);
        Bundle msg=getIntent().getExtras();
        student=(Student)msg.get("Student");
        dbr = FirebaseDatabase.getInstance().getReference("StudentFee").child(student.getCourse()).child(student.getPrn());
        name.setText(student.getName());cl.setText(student.getCourse());
        roll.setText(student.getRoll());
        getFeeData();
        btn=findViewById(R.id.feepay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(StudentFee.this);
                builder.setTitle("Are You Sure You want to Pay?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateFees();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(StudentFee.this,StudentDash.class);
                                intent.putExtra("Student",student);
                                startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(StudentFee.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(StudentFee.this,StudentProfile.class);
                                intent.putExtra("Student",student);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(StudentFee.this,LoginActivity.class);
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

    private void getFeeData() {
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    if (key.equals("paid"))
                        pd = data.getValue().toString();
                    else if (key.equals("outstanding"))
                        outst = data.getValue().toString();
                    else if (key.equals("totalfees"))
                        fe = data.getValue().toString();
                }
                paid.setText(pd);
                outstand.setText(outst);
                fee.setText(fe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateFees(){
        String amount=e1.getText().toString();
        long temp=Long.parseLong(outst)-Long.parseLong(amount);
        if(temp<0){
            Toast.makeText(this, "Invalid Amount", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            long rempd=Long.parseLong(pd)+Long.parseLong(amount);
            pd=String.valueOf(rempd);
            AdminFee adminFee = new AdminFee(student.getCourse(),fe,String.valueOf(temp),pd);
            dbr.setValue(adminFee);
            Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StudentFee.this,LoginActivity.class));
            finish();
        }
    }
}
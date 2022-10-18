package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyDash extends AppCompatActivity {
    ImageButton menuButton;
    CardView c1,c2,c3,c4,c5,c6;
    Faculty f;
    Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dash);
        c1=findViewById(R.id.card1);c3=findViewById(R.id.card3);
        c2=findViewById(R.id.card2);c4=findViewById(R.id.card4);
        c5=findViewById(R.id.card5);c6=findViewById(R.id.card6);
        Bundle msg=getIntent().getExtras();
        f=(Faculty)msg.get("Faculty");
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FacultyDash.this,RetrieveNews.class);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAssignment();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAssignment();
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewResult();
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentDetails();
            }
        });
        menuButton=(ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(FacultyDash.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(FacultyDash.this,FacultyProfile.class);
                            intent.putExtra("Faculty",f);
                            startActivity(intent);
                            break;
                            case R.id.logout:intent=new Intent(FacultyDash.this,LoginActivity.class);
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
    private void studentDetails(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("View Student Details");
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout innerlayout1=new LinearLayout(this);
        LinearLayout innerlayout2=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView course=new TextView(this);
        course.setText("Select Course:");
        EditText prn=new EditText(this);
        prn.setHint("Enter Student Prn");
        Spinner crse=new Spinner(this);
        String cr[]={"MCA","MCS"};
        crse.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cr));
        innerlayout1.addView(course);innerlayout1.addView(crse);
        innerlayout2.addView(prn);
        linearLayout.addView(innerlayout1);
        linearLayout.addView(innerlayout2);

        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("ViewProfile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String c=crse.getSelectedItem().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Student").child(c).child(prn.getText().toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        s=snapshot.getValue(Student.class);
                        Toast.makeText(FacultyDash.this, "Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(FacultyDash.this,StudentProfile.class);
                        intent.putExtra("Student",s);
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
    private void viewResult(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("View Result");
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout innerlayout1=new LinearLayout(this);
        LinearLayout innerlayout2=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView course=new TextView(this);
        course.setText("Select Course:");
        EditText prn=new EditText(this);
        prn.setHint("Enter Student Prn");
        Spinner crse=new Spinner(this);
        String cr[]={"MCA","MCS"};
        crse.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cr));
        innerlayout1.addView(course);innerlayout1.addView(crse);
        innerlayout2.addView(prn);
        linearLayout.addView(innerlayout1);
        linearLayout.addView(innerlayout2);

        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("ViewResult", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String c=crse.getSelectedItem().toString();
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Student").child(c).child(prn.getText().toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        s=snapshot.getValue(Student.class);
                        Toast.makeText(FacultyDash.this, "Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(FacultyDash.this,ResultActivity.class);
                        intent.putExtra("Student",s);
                        intent.putExtra("Faculty",f);
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
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void viewAssignment(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("View Assignment");
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout innerlayout1=new LinearLayout(this);
        LinearLayout innerlayout2=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView course=new TextView(this);
        course.setText("Select Course:");
        TextView year=new TextView(this);
        year.setText("Select Year:");

        Spinner crse=new Spinner(this);
        Spinner yer=new Spinner(this);
        String cr[]={"MCA","MSC"};
        String yr[]={"FY","SY"};
        crse.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cr));
        yer.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, yr));
        innerlayout1.addView(course);innerlayout1.addView(crse);
        innerlayout2.addView(year);innerlayout2.addView(yer);

        linearLayout.addView(innerlayout1);
        linearLayout.addView(innerlayout2);

        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("ViewAssignment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String c=crse.getSelectedItem().toString();
                String y=yer.getSelectedItem().toString();
                Toast.makeText(FacultyDash.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FacultyDash.this,ViewAssignment.class);
                intent.putExtra("Course",c);
                intent.putExtra("Year",y);
                intent.putExtra("Faculty",f);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void addAssignment() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Assignment");
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout innerlayout1=new LinearLayout(this);
        LinearLayout innerlayout2=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView course=new TextView(this);
        course.setText("Select Course:");
        TextView year=new TextView(this);
        year.setText("Select Year:");

        Spinner crse=new Spinner(this);
        Spinner yer=new Spinner(this);
        String cr[]={"MCA","MCS"};
        String yr[]={"FY","SY"};
        crse.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cr));
        yer.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, yr));
        innerlayout1.addView(course);innerlayout1.addView(crse);
        innerlayout2.addView(year);innerlayout2.addView(yer);

        linearLayout.addView(innerlayout1);
        linearLayout.addView(innerlayout2);

        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("AddAssignment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String c=crse.getSelectedItem().toString();
                String y=yer.getSelectedItem().toString();
                Toast.makeText(FacultyDash.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FacultyDash.this,AddAssignment.class);
                intent.putExtra("Course",c);
                intent.putExtra("Year",y);
                intent.putExtra("Faculty",f);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void addResult(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Result");
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout innerlayout1=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView course=new TextView(this);
        course.setText("Select Course:");
        EditText prn=new EditText(this);
        prn.setHint("Enter PRN");
        Spinner crse=new Spinner(this);
        prn.setMinEms(16);
        String cr[]={"MCA","MCS"};
        crse.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cr));
        prn.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        innerlayout1.addView(course);innerlayout1.addView(crse);
        linearLayout.addView(innerlayout1);
        linearLayout.addView(prn);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("AddResult", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pn=prn.getText().toString().trim();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Student").child(crse.getSelectedItem().toString()).child(pn);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student s=snapshot.getValue(Student.class);
                        Intent intent=new Intent(FacultyDash.this,AddResultActivity.class);
                        intent.putExtra("Student",s);
                        intent.putExtra("Faculty",f);
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
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
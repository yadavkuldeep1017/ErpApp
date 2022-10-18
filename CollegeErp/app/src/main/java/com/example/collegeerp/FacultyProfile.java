package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class FacultyProfile extends AppCompatActivity {

    TextView nme,prn,eml,cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);
        nme = findViewById(R.id.name2);
        prn = findViewById(R.id.prnno2);
        eml = findViewById(R.id.gmail2);
        cnt = findViewById(R.id.cno2);
        Bundle bundle = getIntent().getExtras();
        Faculty fac = (Faculty) bundle.get("Faculty");
        nme.setText(fac.getName());
        prn.setText(fac.getPrn());
        eml.setText(fac.getGmail());
        cnt.setText(fac.getContactno());
        ImageButton menuButton = (ImageButton) findViewById(R.id.menubutton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(FacultyProfile.this,menuButton);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId())
                        {
                            case R.id.profile:intent=new Intent(FacultyProfile.this,FacultyProfile.class);
                                startActivity(intent);
                                break;
                            case R.id.logout:intent=new Intent(FacultyProfile.this,LoginActivity.class);
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
}
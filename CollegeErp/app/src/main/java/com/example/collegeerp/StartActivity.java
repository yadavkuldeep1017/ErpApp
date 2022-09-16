package com.example.collegeerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Thread thread=new Thread(){
            public void run()
            {
                try
                {
                    sleep(5000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}
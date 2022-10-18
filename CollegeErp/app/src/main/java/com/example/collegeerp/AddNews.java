package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNews extends AppCompatActivity {
    FirebaseDatabase mdatabase;
    DatabaseReference mref;
    FirebaseStorage mstorage;
    ImageButton imageButton;
    EditText heading,addnews;
    Button submit,clear;

    private static final int Gallery_Code=1;
    Uri imageUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        imageButton = findViewById(R.id.imageButton);
        heading = findViewById(R.id.heading);
        addnews = findViewById(R.id.addnewstxt);
        submit = (Button) findViewById(R.id.post);
        clear = (Button) findViewById(R.id.clear);
        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference().child("News");
        mstorage = FirebaseStorage.getInstance();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Gallery_Code);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h1=heading.getText().toString().trim();
                String n1=addnews.getText().toString().trim();

                if(!(h1.isEmpty() && n1.isEmpty()&& imageUrl!=null))
                {
                    StorageReference filepath= mstorage.getReference().child("ImagePost").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri>downloadurl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t=task.getResult().toString();
                                    DatabaseReference newPost=mref.push();
                                    newPost.child("Heading").setValue(h1);
                                    newPost.child("AddNews").setValue(n1);
                                    newPost.child("image").setValue(task.getResult().toString());

                                    Toast.makeText(AddNews.this, "News Uploaded", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(AddNews.this,AdminDash.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                }
                else
                {
                    Toast.makeText(AddNews.this, "Please Upload Image and Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton.setImageURI(Uri.parse(""));
                heading.setText("");
                addnews.setText("");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_Code && resultCode==RESULT_OK)
        {
            imageUrl=data.getData();
            imageButton.setImageURI(imageUrl);
        }
        else{
            Toast.makeText(this, "Image Not Selected", Toast.LENGTH_SHORT).show();
        }
//
    }
}
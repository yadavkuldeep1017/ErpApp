package com.example.collegeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class RetrieveNews extends AppCompatActivity {

    ListView mrecyclerView;
    private ArrayList<NewsModel> newsModelArrayList;
    private NewsAdapter newsAdapter;
    FirebaseStorage mstorage;
    List<NewsModel>newsModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_news);
        mrecyclerView=findViewById(R.id.recycleview);
        newsModelArrayList=new ArrayList<>();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("News");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                {
                    NewsModel newsModel=new NewsModel();
                    String head=dataSnapshot.child("Heading").getValue().toString();
                    String desc=dataSnapshot.child("AddNews").getValue().toString();
                    newsModel.setHeading(head);
                    newsModel.setAddNews(desc);
                    newsModel.setImage(dataSnapshot.child("image").getValue().toString());
                    newsModelArrayList.add(newsModel);
                }
                NewsAdapter newsAdapter=new NewsAdapter(getApplicationContext(),R.layout.news_layout,newsModelArrayList);
                mrecyclerView.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RetrieveNews.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
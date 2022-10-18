package com.example.collegeerp;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class NewsAdapter extends ArrayAdapter<NewsModel> {
    private Context context;
    private ArrayList<NewsModel> newsModelArrayList;
    ImageView img;
    String url="";

    public NewsAdapter(Context context,int resource, ArrayList<NewsModel> newsModelArrayList) {
        super(context,resource,newsModelArrayList);
        this.context = context;
        this.newsModelArrayList = newsModelArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=convertView;
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.news_layout,null);
        TextView head=v.findViewById(R.id.newsheading);
        TextView desc=v.findViewById(R.id.newsdescription);
        img=v.findViewById(R.id.newsimage);
        head.setText(newsModelArrayList.get(position).getHeading());
        desc.setText(newsModelArrayList.get(position).getAddNews());
        url=newsModelArrayList.get(position).getImage();
        Glide.with(context).load(url).into(img);
        return v;
    }
}

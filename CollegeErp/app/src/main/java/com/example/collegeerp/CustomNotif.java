package com.example.collegeerp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomNotif  extends ArrayAdapter<String> {
    ArrayList<String > list=new ArrayList<>();

    public CustomNotif(Context context, int txtViewResourceId, ArrayList<String> l) {
        super(context,txtViewResourceId,l);
        list=l;

    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.printnotificationlayout, null);

        TextView txt=(TextView)v.findViewById(R.id.txt1);
        txt.setText(list.get(position));
        return v;
    }
}

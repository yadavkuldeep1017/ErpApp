package com.example.collegeerp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomResult extends ArrayAdapter<HashMap<String,String>> {
    ArrayList<HashMap<String ,String>> resultSheet=new ArrayList<>();
    int i;
    public CustomResult(Context context, int textViewResourceId, ArrayList<HashMap<String,String>> objects)
    {
        super(context, textViewResourceId,objects);
            resultSheet = objects;
            i=1-resultSheet.size();
    }
    @Override
    public int getCount() {
        return super.getCount();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.result_layout, null);

        TextView srno=(TextView)v.findViewById(R.id.srno);
        TextView sub = (TextView) v.findViewById(R.id.subject);
        TextView mark = (TextView) v.findViewById(R.id.mark);
        srno.setText(String.valueOf(i++));
        String key="",value="";
        for(String s:resultSheet.get(position).keySet()){
            key=s;
            value=resultSheet.get(position).get(key);
        }
        System.out.println(key+" "+value);
        sub.setText(key);
        mark.setText(value);
        return v;
    }

}

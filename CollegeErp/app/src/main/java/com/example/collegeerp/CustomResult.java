package com.example.collegeerp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomResult extends ArrayAdapter<ResultSheet> {
    ArrayList<ResultSheet> resultSheet = new ArrayList<>();
    public CustomResult(Context context, int textViewResourceId, ArrayList<ResultSheet> objects)
    {
        super(context, textViewResourceId, objects);
            resultSheet = objects;
            System.out.println("fddd"+resultSheet.size());
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
        TextView code = (TextView) v.findViewById(R.id.code);
        TextView sub = (TextView) v.findViewById(R.id.subject);
        TextView mark = (TextView) v.findViewById(R.id.mark);
        srno.setText(String.valueOf(resultSheet.get(position).getSrno()));
        code.setText(resultSheet.get(position).getCode());
        sub.setText(resultSheet.get(position).getSubject());
        mark.setText(resultSheet.get(position).getMark());
        return v;
    }

}

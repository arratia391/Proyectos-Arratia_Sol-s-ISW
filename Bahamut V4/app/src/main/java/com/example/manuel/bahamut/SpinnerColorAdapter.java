package com.example.manuel.bahamut;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.graphics.Color;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Manuel on 18/11/2014.
 */
public class SpinnerColorAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Integer> listaColores;


    public SpinnerColorAdapter(Context context){
        this.context = context;

        listaColores=new ArrayList<Integer>();
        int retrieve []=context.getResources().getIntArray(R.array.androidcolors);
        for(int re:retrieve)
        {
            listaColores.add(re);
        }
    }

    @Override
    public int getCount() {
        return listaColores.size();
    }

    @Override
    public Object getItem(int position) {
        return listaColores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_colorspinner, null);
        }

        if(position>0) {
            convertView.setBackgroundColor(listaColores.get(position));
        }
        return convertView;
    }


}

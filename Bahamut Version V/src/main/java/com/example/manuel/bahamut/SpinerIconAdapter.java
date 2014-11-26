package com.example.manuel.bahamut;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Manuel on 19/11/2014.
 */
public class SpinerIconAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> listaIconos;
    TypedArray iconos;

    public SpinerIconAdapter(Context context){
        this.context=context;
        iconos = context.getResources().obtainTypedArray(R.array.iconosAsignatura);
        listaIconos=new ArrayList<Integer>();
        listaIconos.add(new Integer(iconos.getResourceId(0,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(1,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(2,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(3,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(4,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(5,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(6,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(7,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(8,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(9,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(10,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(11,-1)));
        listaIconos.add(new Integer(iconos.getResourceId(12,-1)));
        iconos.recycle();
    }

    @Override
    public int getCount() {
        return listaIconos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaIconos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_iconspinner, null);
        }


            ImageView icon = (ImageView) convertView.findViewById(R.id.ivIconSpinner);
            icon.setImageResource(listaIconos.get(position));


        return convertView;
    }


}

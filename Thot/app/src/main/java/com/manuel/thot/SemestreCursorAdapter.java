package com.manuel.thot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manuel on 08/11/2014.
 */
public class SemestreCursorAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SemestreItem> arraySemestre;

    public SemestreCursorAdapter(Context context, ArrayList<SemestreItem> arraySemestre){
        this.context = context;
        this.arraySemestre = arraySemestre;

    }

    @Override
    public int getCount() {
        return arraySemestre.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySemestre.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void eliminar(int position){
        arraySemestre.remove(position);
    }

    public void actualizar(int position, String nombre, String fecha_desde, String fecha_hasta){
        SemestreItem sem = new SemestreItem(nombre,fecha_desde,fecha_hasta);
        arraySemestre.set(position, sem);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(R.layout.item_listsemestre,null);
        }

        TextView texto = (TextView) convertView.findViewById(R.id.tvListSemestre);

        texto.setText(arraySemestre.get(position).getNombre());



        return convertView;
    }
}
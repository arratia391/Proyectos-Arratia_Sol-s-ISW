package com.example.manuel.bahamut;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manuel on 25/11/2014.
 */
public class ClaseCursorAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Clase> arrayClase;
    private int icon;
    private int colorA;

    public ClaseCursorAdapter(Context context, ArrayList<Clase> arrayClase, int icon, int color) {
        this.context = context;
        this.arrayClase = arrayClase;
        this.icon = icon;
        this.colorA=color;
    }

    @Override
    public int getCount() {
        return arrayClase.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayClase.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void eliminar(int position){
        arrayClase.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(R.layout.item_clases,null);
        }

        TextView tipo = (TextView) convertView.findViewById(R.id.tvTipoClase);

        TextView Lun = (TextView) convertView.findViewById(R.id.tvLunClase);
        TextView Mar = (TextView) convertView.findViewById(R.id.tvMarClase);
        TextView Mie = (TextView) convertView.findViewById(R.id.tvMieClase);
        TextView Jue = (TextView) convertView.findViewById(R.id.tvJueClase);
        TextView Vie = (TextView) convertView.findViewById(R.id.tvVieClase);
        TextView Sab = (TextView) convertView.findViewById(R.id.tvSabClase);
        TextView Dom = (TextView) convertView.findViewById(R.id.tvDomClase);

        ImageView icono = (ImageView) convertView.findViewById(R.id.ivIconClase);
        TextView periodo = (TextView) convertView.findViewById(R.id.tvPeriodoClase);
        TextView sala = (TextView) convertView.findViewById(R.id.tvSalaClase);
        TextView color= (TextView) convertView.findViewById(R.id.tvColorClase);

        tipo.setText(arrayClase.get(position).getTipo());
        periodo.setText(arrayClase.get(position).getTiempoDesde()+"  -  "+arrayClase.get(position).getTiempoHasta());
        sala.setText(arrayClase.get(position).getSala());
        icono.setImageResource(icon);
        color.setBackgroundColor(colorA);

        for(int i=0; i<arrayClase.get(position).getSemana().length();i++){

            char aux = arrayClase.get(position).getSemana().charAt(i);

            if(aux == '1'){
                Lun.setTypeface(null, Typeface.BOLD);
                Lun.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '2'){
                Mar.setTypeface(null, Typeface.BOLD);
                Mar.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '3'){
                Mie.setTypeface(null, Typeface.BOLD);
                Mie.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '4'){
                Jue.setTypeface(null, Typeface.BOLD);
                Jue.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '5'){
                Vie.setTypeface(null, Typeface.BOLD);
                Vie.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '6'){
                Sab.setTypeface(null, Typeface.BOLD);
                Sab.setTextColor(Color.parseColor("#FF276A70"));
            }
            if(aux == '7'){
                Dom.setTypeface(null, Typeface.BOLD);
                Dom.setTextColor(Color.parseColor("#FF276A70"));
            }
        }

        return convertView;
    }
}

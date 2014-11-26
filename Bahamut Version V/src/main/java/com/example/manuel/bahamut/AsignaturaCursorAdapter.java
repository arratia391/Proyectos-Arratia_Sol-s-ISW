package com.example.manuel.bahamut;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Manuel on 21/11/2014.
 */
public class AsignaturaCursorAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AsignaturaItem> arrayAsignatura;
    private TypedArray iconos;

    public AsignaturaCursorAdapter(Context context, ArrayList<AsignaturaItem> arrayAsignatura){
        this.context = context;
        this.arrayAsignatura = arrayAsignatura;
    }

    @Override
    public int getCount() {
        return arrayAsignatura.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayAsignatura.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void eliminar(int position){
        arrayAsignatura.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(R.layout.item_listasignatura,null);
        }

        ImageView imagenIzq = (ImageView) convertView.findViewById(R.id.ivAsignaturaIzq);
        ImageView imagenDer = (ImageView) convertView.findViewById(R.id.ivAsignaturaDer);
        TextView texto = (TextView) convertView.findViewById(R.id.tvAsignaturaList);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.ivAsignaturaIcon);


        imagenIzq.setBackgroundColor(arrayAsignatura.get(position).getColor());
        imagenDer.setBackgroundColor(arrayAsignatura.get(position).getColor());
        texto.setText(arrayAsignatura.get(position).getNombre());
        imagen.setImageResource(arrayAsignatura.get(position).getIcono());




        return convertView;
    }
}

package com.example.manuel.bahamut;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
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
public class HoyCursorAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Hoy> arrayHoy;
    private Boolean tiempoFinalizado;
    private int posicionFinal;
    private int inicio;
    private Boolean verificar;

    public HoyCursorAdapter(Context context, ArrayList<Hoy> arrayHoy, Boolean tiempoFinalizado) {
        this.context = context;
        this.arrayHoy = arrayHoy;
        this.tiempoFinalizado = tiempoFinalizado;
        verificar=true;
    }

    @Override
    public int getCount() {
        return arrayHoy.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayHoy.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Boolean getTiempoFinalizado(){
        return tiempoFinalizado;
    }

    public int getPosicionFinal(){return posicionFinal;}

    public void eliminar(int position){
        arrayHoy.remove(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflador.inflate(R.layout.item_hoy,null);
            inicio=0;

        }

        TextView asignatura = (TextView) convertView.findViewById(R.id.tvTituloAsignaturaHoy);
        TextView periodo = (TextView) convertView.findViewById(R.id.tvPeriodoHoy);
        final TextView periodoFaltante = (TextView) convertView.findViewById(R.id.tvPeriodoFaltanteHoy);
        TextView sala = (TextView) convertView.findViewById(R.id.tvSalaHoy);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.ivHoy);
        final TextView queda = (TextView) convertView.findViewById(R.id.tvTituloQuedanHoy);

        asignatura.setText(arrayHoy.get(position).getNombreAsignatura());
        asignatura.setBackgroundColor(arrayHoy.get(position).getColor());
        sala.setText(arrayHoy.get(position).getSala());
        imagen.setImageResource(arrayHoy.get(position).getIcon());
        periodo.setText(arrayHoy.get(position).getPeriodoDesde()+"  -  "+arrayHoy.get(position).getPeriodoHasta());

        if(position==inicio && verificar){
            tiempoFinalizado=false;
            verificar=false;
            String Desde = arrayHoy.get(position).getPeriodoDesde();
            String Hasta = arrayHoy.get(position).getPeriodoHasta();
            String HDesde = Desde.replace(":","");
            String HHasta = Hasta.replace(":","");


            int intDesde = Integer.parseInt(HDesde);
            int intHasta = Integer.parseInt(HHasta);

            int minutos = intDesde%100;
            int Horas = intDesde/100;
            int minutosHasta = intHasta%100;
            int HorasHasta = intHasta/100;

            int PeriodoTotal = Math.abs((HorasHasta-Horas)*3600) + Math.abs((minutosHasta-minutos)*60);

            final CountDownTimer cT =  new CountDownTimer(PeriodoTotal*1000, 1000) {

                public void onTick(long millisUntilFinished) {

                    //Horas
                    String hora = String.format("%02d", millisUntilFinished/3600000);
                    //Minutos
                    String min = String.format("%02d", (millisUntilFinished%3600000)/60000);
                    //Segundos
                    //String seg = String.format("%02d", ((millisUntilFinished%60000)/1000));

                    periodoFaltante.setText(hora+":"+min);
                    queda.setText("Quedan: ");
                }

                public void onFinish() {
                    tiempoFinalizado=true;
                    inicio++;
                    verificar=true;
                    periodoFaltante.setText("Fin");
                }
            };
            cT.start();
        }

        if(position>inicio){
            queda.setText("");
            periodoFaltante.setText(arrayHoy.get(position).getPeriodoDesde());
        }

        return convertView;
    }
}

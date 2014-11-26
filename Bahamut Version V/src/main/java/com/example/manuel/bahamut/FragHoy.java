package com.example.manuel.bahamut;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Manuel on 02/11/2014.
 */
public class FragHoy extends Fragment {
    private ListView listaHoy;
    private AdministradorDB administrador;
    private ArrayList<Hoy> arrayHoy;
    private int mYear, mMonth, mDay;
    private String diaSemana;
    private HoyCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_hoy, container, false);
        listaHoy = (ListView) rootView.findViewById(R.id.lvHoy);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        diaSemana = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        actualizarLista();

    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarLista();

    }


    public void actualizarLista() {
        administrador = new AdministradorDB(getActivity());
        Cursor c = administrador.mostrarClaseDelDia();
        arrayHoy = new ArrayList<Hoy>();
        if (c.moveToFirst()) {

            do {

                String tipo = c.getString(c.getColumnIndex("tipo"));
                String FechaDesde = c.getString(c.getColumnIndex("CfechaDesde"));
                String FechaHasta = c.getString(c.getColumnIndex("CfechaHasta"));
                String HoraDesde = c.getString(c.getColumnIndex("CtiempoDesde"));
                String HoraHasta = c.getString(c.getColumnIndex("CtiempoHasta"));
                String sala = c.getString(c.getColumnIndex("sala"));
                String semana = c.getString(c.getColumnIndex("semana"));

                int hola = 23;
                int colorAsginatura = c.getInt(c.getColumnIndex("color"));
                int iconoAsignatura = c.getInt(c.getColumnIndex("icono"));
                String nombreAsignatura = c.getString(c.getColumnIndex("nombre"));

                //Verifica si la fecha de hoy esta contenida en algun rango de fecha de las asignaturas.
                if (Fecha.fechaActual(mDay, mMonth, mYear, FechaDesde, FechaHasta)) {
                    if (semana.contains(diaSemana)) {

                        arrayHoy.add(new Hoy(nombreAsignatura, tipo, HoraDesde, HoraHasta, sala, iconoAsignatura, colorAsginatura));
                    }
                }
            } while (c.moveToNext());
            adapter = new HoyCursorAdapter(getActivity(), arrayHoy, false);
            listaHoy.setAdapter(adapter);
            if(adapter.getTiempoFinalizado()){
                adapter.eliminar(adapter.getPosicionFinal());
                listaHoy.invalidateViews();
            }
        }
    }
}
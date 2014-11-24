package com.example.manuel.bahamut;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Manuel on 23/11/2014.
 */
public class fm_crearClase extends FragmentActivity implements View.OnClickListener{

    private ImageView info;
    private int mYear,mMonth,mDay,hora,min;
    private TextView tv_FechaDesde;
    private TextView tv_FechaHasta;
    private TextView tv_HoraDesde;
    private TextView tv_HoraHasta;
    private Fecha fecha;
    private Boolean fechaSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_clase);
        info = (ImageView) findViewById(R.id.ivInfoClase);
        tv_FechaDesde = (TextView) findViewById(R.id.tvFechaDesdeClase);
        tv_FechaHasta = (TextView) findViewById(R.id.tvFechaHastaClase);
        tv_HoraDesde= (TextView) findViewById(R.id.tvHoraDesdeClase);
        tv_HoraHasta= (TextView) findViewById(R.id.tvHoraHastaClase);
        Calendar aux = Calendar.getInstance();

        tv_FechaDesde.setText( new Fecha(aux.get(Calendar.DAY_OF_MONTH),aux.get(Calendar.MONTH),aux.get(Calendar.YEAR)).getFecha());
        tv_FechaHasta.setText( new Fecha(aux.get(Calendar.DAY_OF_MONTH),aux.get(Calendar.MONTH),aux.get(Calendar.YEAR)).getFecha());

        tv_HoraDesde.setText(new Tiempo(aux.get(Calendar.HOUR_OF_DAY),aux.get(Calendar.MINUTE)).getTiempo());
        tv_HoraHasta.setText(new Tiempo(aux.get(Calendar.HOUR_OF_DAY), aux.get(Calendar.MINUTE)).getTiempo());

        tv_FechaDesde.setOnClickListener(this);
        tv_FechaHasta.setOnClickListener(this);
        tv_HoraDesde.setOnClickListener(this);
        tv_HoraHasta.setOnClickListener(this);

        fechaSeleccionada=false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Ejemplo: Ayudantia, Laboratorio, Catedra, Seminario, etc...",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvFechaDesdeClase:
                // Proceso para obtener los datos.
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Lanzar Date Picker Dialog
                DatePickerDialog lanzarDesde = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Setear valor en editText
                                tv_FechaDesde.setText(new Fecha(dayOfMonth,monthOfYear,year).getFecha());

                                //Se guarda la fecha escogida en un objeto fecha para definir un rango.
                                fecha = new Fecha(dayOfMonth,monthOfYear,year);
                            }
                        }, mYear, mMonth, mDay);
                lanzarDesde.setCancelable(false);
                lanzarDesde.show();
                fechaSeleccionada=true;
                break;

            case R.id.tvFechaHastaClase:
                // Process to get Current Date

                if(fechaSeleccionada) {
                    //Instancia el calendario comenzando con el dia siguiente de la fecha escogida en "Desde".
                    final Calendar c2 = new GregorianCalendar(fecha.getAño(), fecha.getMes(), fecha.getDia());

                    mYear = c2.get(Calendar.YEAR);
                    mMonth = c2.get(Calendar.MONTH);
                    mDay = c2.get(Calendar.DAY_OF_MONTH);


                    // Lanzar Date Picker Dialog
                    DatePickerDialog lanzarHasta = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // Setear valor en editText
                                    tv_FechaHasta.setText(new Fecha(dayOfMonth,monthOfYear,year).getFecha());;
                                }
                            }, mYear, mMonth, mDay);

                    lanzarHasta.getDatePicker().setMinDate(c2.getTimeInMillis());  //Limita el DataPickerDialog a un dia siguiente de la fecha puesta en "Desde".
                    lanzarHasta.setCancelable(false);
                    lanzarHasta.show();
                }else{
                    alerta("fechadesde vacia");
                }
                break;

            case R.id.tvHoraDesdeClase:
                final Calendar d = Calendar.getInstance();
                hora = d.get(Calendar.HOUR_OF_DAY);
                min = d.get(Calendar.MINUTE);

                TimePickerDialog lanzarTiempoDesde = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv_HoraDesde.setText(new Tiempo(hourOfDay,minute).getTiempo());
                    }
                },hora,min,true);
                lanzarTiempoDesde.setCancelable(false);
                lanzarTiempoDesde.show();
                break;

            case R.id.tvHoraHastaClase:
                final Calendar d2 = Calendar.getInstance();
                hora = d2.get(Calendar.HOUR_OF_DAY);
                min = d2.get(Calendar.MINUTE);

                TimePickerDialog lanzarTiempoHasta = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv_HoraHasta.setText(new Tiempo(hourOfDay,minute).getTiempo());
                    }
                },hora,min,true);
                lanzarTiempoHasta.setCancelable(false);
                lanzarTiempoHasta.show();
                break;
            }
    }

    public void alerta(String alerta){

        if(alerta.equals("fechadesde vacia")){
            final AlertDialog.Builder dialogoAlertaFecha = new AlertDialog.Builder(this);
            dialogoAlertaFecha.setTitle("Fecha");
            dialogoAlertaFecha.setMessage("Seleccione una fecha de inicio primero.");
            dialogoAlertaFecha.setCancelable(false);
            dialogoAlertaFecha.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogoAlertaFecha.show();
        }

    }


}

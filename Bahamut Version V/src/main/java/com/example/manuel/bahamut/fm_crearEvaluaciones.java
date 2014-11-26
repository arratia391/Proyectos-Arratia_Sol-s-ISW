package com.example.manuel.bahamut;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Manuel on 25/11/2014.
 */
public class fm_crearEvaluaciones extends FragmentActivity implements View.OnClickListener{

    private EditText et_nombre;
    private TextView tv_fecha;
    private TextView tv_hora;
    private EditText et_ponderacion;
    private int mYear,mMonth,mDay,hora,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_evaluaciones);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        et_nombre = (EditText) findViewById(R.id.etNombreEvaluacion);
        tv_fecha = (TextView) findViewById(R.id.tvFechaEvaluacion);
        tv_hora = (TextView) findViewById(R.id.tvHoraEvaluacion);
        et_ponderacion = (EditText) findViewById(R.id.etPonderacionEvaluacion);

        tv_fecha.setOnClickListener(this);
        tv_hora.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.confirmar:
                if(validarPonderacion()){
                    //cargar();
                    finish();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvFechaEvaluacion:
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
                                tv_fecha.setText(new Fecha(dayOfMonth, monthOfYear, year).getFecha());
                            }
                        }, mYear, mMonth, mDay);
                lanzarDesde.setCancelable(false);
                lanzarDesde.show();
                break;

            case R.id.tvHoraEvaluacion:
                final Calendar d = Calendar.getInstance();
                hora = d.get(Calendar.HOUR_OF_DAY);
                min = d.get(Calendar.MINUTE);

                TimePickerDialog lanzarTiempoDesde = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv_hora.setText(new Tiempo(hourOfDay,minute).getTiempo());
                    }
                },hora,min,true);
                lanzarTiempoDesde.setCancelable(false);
                lanzarTiempoDesde.show();
                break;

        }
    }

    public Boolean validarPonderacion(){
        int ponderacion = Integer.parseInt(et_ponderacion.getText().toString());
        if(ponderacion>100){
            alerta("ponderacion mala");
            return false;
        }else{
            return true;
        }
    }

    public void alerta(String alerta) {

        if (alerta.equals("ponderacion mala")) {
            final AlertDialog.Builder dialogoAlerta = new AlertDialog.Builder(this);
            dialogoAlerta.setTitle("Atención");
            dialogoAlerta.setMessage("La ponderacion no puede exeder del 100%.");
            dialogoAlerta.setCancelable(false);
            dialogoAlerta.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialogoAlerta.show();
        }
    }
}

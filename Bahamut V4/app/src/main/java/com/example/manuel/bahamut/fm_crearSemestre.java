package com.example.manuel.bahamut;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Manuel on 05/11/2014.
 */


public class fm_crearSemestre extends FragmentActivity implements View.OnClickListener{

    private EditText et_Semestre;
    private AdministradorDB administrador;
    private String verificar;
    private Fecha fecha;
    private EditText et_Desde;
    private EditText et_Hasta;
    private ImageView iv_Desde;
    private ImageView iv_Hasta;
    private int mYear,mMonth,mDay;
    private Boolean fechaSeleccionada;
    private Boolean fechaCancelada;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_semestre);

        //Añade el icono de flecha a la izquierda del Action Bar para poder volver
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema", "#992416"))));

        et_Semestre = (EditText) findViewById(R.id.etSemestre);
        et_Desde = (EditText) findViewById(R.id.etDesde);
        et_Hasta = (EditText) findViewById(R.id.etHasta);
        iv_Desde = (ImageView) findViewById(R.id.ivDesde);
        iv_Hasta = (ImageView) findViewById(R.id.ivHasta);

        Calendar aux = Calendar.getInstance();

        et_Desde.setText( new Fecha(aux.get(Calendar.DAY_OF_MONTH),aux.get(Calendar.MONTH),aux.get(Calendar.YEAR)).getFecha());
        et_Hasta.setText( new Fecha(aux.get(Calendar.DAY_OF_MONTH)+1,aux.get(Calendar.MONTH),aux.get(Calendar.YEAR)).getFecha());

        Bundle bundle = getIntent().getExtras();
        verificar = bundle.getString("verificar");
        fechaSeleccionada = false;
        fechaCancelada=false;

        iv_Desde.setOnClickListener(this);
        iv_Hasta.setOnClickListener(this);

        if(!verificar.equals("crear")){
            et_Semestre.setText(verificar);
            administrador = new AdministradorDB(getApplicationContext());
            Cursor t = administrador.cargarFecha(verificar);
            String auxiliarfechaD;
            String auxiliarfechaH;

            if(t.moveToFirst()) {
                do {
                    auxiliarfechaD = t.getString(t.getColumnIndex("fechadesde"));
                    auxiliarfechaH = t.getString(t.getColumnIndex("fechahasta"));
                }while(t.moveToNext());
                et_Desde.setText(auxiliarfechaD);
                et_Hasta.setText(auxiliarfechaH);
            }



        }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }


    //Menu Seleccionable
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
            case R.id.confirmar:

                //Verifica si el EditText esta vacio. Solo se guardara si se escribio algo.
                if (et_Semestre.getText().length() > 0) {

                        if (verificar.equals("crear"))  {
                        //Si se inicio para crear, se guarda en la BD como un elemento mas.
                        //Si se inicio de otro lado, entonces modificara la BD.
                        if (validarBD(et_Semestre.getText().toString())){
                            cargar();
                            finish();
                            return true;
                        }
                            return false;
                        }
                        else {
                            if (validarBD(et_Semestre.getText().toString()) || verificar.equals(et_Semestre.getText().toString())) {
                                modificar();
                                finish();
                                return true;
                            }

                            return false;
                        }
                }

                    return false;

        }
        return super.onOptionsItemSelected(item);
    }



    public void cargar() {
            administrador = new AdministradorDB(getApplicationContext());
            administrador.insertar(et_Semestre.getText().toString(),et_Desde.getText().toString(),et_Hasta.getText().toString());
            et_Semestre.setText("");
    }

    public void modificar() {

        administrador = new AdministradorDB(getApplicationContext());
        administrador.modificarSemestre(verificar, et_Semestre.getText().toString(), et_Desde.getText().toString(),et_Hasta.getText().toString());
        et_Semestre.setText("");

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ivDesde:
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
                                                        //et_Desde.setText(dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year);
                                                        et_Desde.setText(new Fecha(dayOfMonth,monthOfYear,year).getFecha());

                                                        //Se guarda la fecha escogida en un objeto fecha para definir un rango.
                                                        fecha = new Fecha(dayOfMonth+1,monthOfYear,year);
                                                    }
                                                }, mYear, mMonth, mDay);

                                        lanzarDesde.setCancelable(false);
                                        lanzarDesde.show();
                                        fechaSeleccionada=true;
                                        break;

            case R.id.ivHasta:
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
                                                            et_Hasta.setText(new Fecha(dayOfMonth,monthOfYear,year).getFecha());
                                                        }
                                                    }, mYear, mMonth, mDay);

                                            lanzarHasta.getDatePicker().setMinDate(c2.getTimeInMillis());  //Limita el DataPickerDialog a un dia siguiente de la fecha puesta en "Desde".
                                            lanzarHasta.setCancelable(false);
                                            lanzarHasta.show();
                                        }else{
                                            alerta("fechadesde vacia");
                                        }
                                        break;


        }
    }
/*
    public String validarFecha(String f){
        String aux = new String();
        aux = f.replace("/","");
        return aux;
    }*/

    public Boolean validarBD(String nombre){
        administrador = new AdministradorDB(this);
        Cursor c = administrador.cargar();
        if(c.moveToFirst()) {

            do {
                String aux = c.getString(c.getColumnIndex("nombre"));
                if (aux.equals(nombre)) {
                    alerta("semestre igual");
                    return false;
                }
            } while (c.moveToNext());
        }

        if(et_Desde.getText().toString().equals("") || et_Hasta.getText().toString().equals("")){
            alerta("sin fecha");
            return false;
        }

        return true;
    }


    public void alerta(String alerta){

        if(alerta.equals("semestre igual")){
            final AlertDialog.Builder dialogoAlerta = new AlertDialog.Builder(this);
            dialogoAlerta.setTitle("Semestre");
            dialogoAlerta.setMessage("El semestre ya existe.");
            dialogoAlerta.setCancelable(false);
            dialogoAlerta.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogoAlerta.show();
        }

        if(alerta.equals("sin fecha")){
            final AlertDialog.Builder dialogoAlertaFecha = new AlertDialog.Builder(this);
            dialogoAlertaFecha.setTitle("Fecha");
            dialogoAlertaFecha.setMessage("Complete el periodo de duracion del semestre.");
            dialogoAlertaFecha.setCancelable(false);
            dialogoAlertaFecha.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogoAlertaFecha.show();
        }

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

















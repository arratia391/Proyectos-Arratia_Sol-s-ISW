package com.example.manuel.bahamut;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.graphics.Color;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Lara on 15-11-2014.
 */
public class fm_crearAsignatura extends FragmentActivity {


    private Spinner spinnerColor;
    private Spinner spinnerIcon;
    private SpinnerColorAdapter adapterColor;
    private SpinerIconAdapter adapterIcon;
    private EditText et_asignatura;
    private int id_Semestre;
    private String verificar;
    private AdministradorDB administrador;
    private int posicionSpinnerColor;
    private int posicionSpinnerIcon;
    int id_asignatura;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_asignatura);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema","#992416"))));

        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerIcon = (Spinner) findViewById(R.id.spinnerIcono);

        adapterColor = new SpinnerColorAdapter(this);
        spinnerColor.setAdapter(adapterColor);

        adapterIcon = new SpinerIconAdapter(this);
        spinnerIcon.setAdapter(adapterIcon);

        et_asignatura = (EditText) findViewById(R.id.etAsignaturaTitulo);

        Bundle bundle = getIntent().getExtras();
        id_Semestre = bundle.getInt("id_Semestre");
        verificar = bundle.getString("verificar");


        Log.i("Funciona1", "Verificar: "+ verificar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int[] colores = getResources().getIntArray(R.array.androidcolors);
                posicionSpinnerColor= colores[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerIcon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray iconos  = getResources().obtainTypedArray(R.array.iconosAsignatura);
                posicionSpinnerIcon=iconos.getResourceId(position,-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.confirmar:
                if (et_asignatura.getText().length() > 0) {
                    if (verificar.equals("crearAsignatura"))  {
                        if(cargar()) {
                            finish();
                        }
                        return true;
                    }}
        }
        return super.onOptionsItemSelected(item);
    }

    public Boolean cargar() {
        administrador = new AdministradorDB(getApplicationContext());
        administrador.insertarAsignatura(et_asignatura.getText().toString(),posicionSpinnerColor,posicionSpinnerIcon);
        Cursor cursor = administrador.cargarIdAsignatura(et_asignatura.getText().toString());
        if(cursor.moveToLast()){
            id_asignatura = cursor.getInt(cursor.getColumnIndex("id"));
            Cursor cursorBooleano = administrador.contarRepetidos(id_Semestre,et_asignatura.getText().toString());
            cursorBooleano.moveToFirst();
                int aux = cursorBooleano.getInt(0);
                if(aux>0) {
                    alerta("Ya existe asignatura en este semestre");
                    administrador.eliminarAsignatura(id_asignatura);
                    administrador.Close();
                    return false;
                }else{
                    administrador.insertarGestionSA(id_Semestre,id_asignatura);
                    administrador.Close();
                    return true;

                }


        }
        return false;

    }

    public void alerta(String alerta) {

        if (alerta.equals("Ya existe asignatura en este semestre")) {
            final AlertDialog.Builder dialogoAlerta = new AlertDialog.Builder(this);
            dialogoAlerta.setTitle("Atención");
            dialogoAlerta.setMessage("La asignatura ya existe en este semestre.");
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

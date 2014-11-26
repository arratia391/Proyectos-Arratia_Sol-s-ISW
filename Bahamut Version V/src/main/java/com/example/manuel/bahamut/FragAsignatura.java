package com.example.manuel.bahamut;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Manuel on 18/11/2014.
 */
public class FragAsignatura extends FragmentActivity {

    private ListView listaAsignatura;
    private int id_Semestre;
    private AdministradorDB administrador;
    private Cursor c;
    private ArrayList<AsignaturaItem> arrayAsignatura;
    private AsignaturaCursorAdapter adapter;
    AdapterView.AdapterContextMenuInfo info;
    AsignaturaItem uno;
    private String nombreActualizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_asignatura);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema", "#992416"))));

        RelativeLayout rl= (RelativeLayout) findViewById(R.id.rlFragmentAsignatura);
        rl.setBackgroundResource(prefe.getInt("fondoTema",R.drawable.madera));

        listaAsignatura = (ListView) findViewById(R.id.lvAsignatura);
        listaAsignatura.setEmptyView(findViewById(R.id.tvMensajeSinAsignatura));
        Bundle bundle = getIntent().getExtras();
        id_Semestre=bundle.getInt("idSemestre");

        administrador = new AdministradorDB(this);

        c = administrador.mostrarAsignaturas(id_Semestre);
        arrayAsignatura = new ArrayList<AsignaturaItem>();
        if(c.moveToFirst()) {

            do {
                int id_asignatura=c.getInt(c.getColumnIndex("id"));
                String nombre = c.getString(c.getColumnIndex("nombre"));
                int color = c.getInt(c.getColumnIndex("color"));
                int icono = c.getInt(c.getColumnIndex("icono"));

                arrayAsignatura.add(new AsignaturaItem(id_asignatura,nombre,color,icono));
            }while(c.moveToNext());

            adapter = new AsignaturaCursorAdapter(this, arrayAsignatura);
            listaAsignatura.setAdapter(adapter);
            administrador.Close();
        }
        registerForContextMenu(listaAsignatura);


    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
        listaAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent u = new Intent(FragAsignatura.this,infoAsignatura.class);
                u.putExtra("nombreAsignatura",arrayAsignatura.get(position).getNombre());
                u.putExtra("iconoAsignatura",arrayAsignatura.get(position).getIcono());
                u.putExtra("idAsignatura",arrayAsignatura.get(position).getId());
                u.putExtra("colorAsignatura",arrayAsignatura.get(position).getColor());
                startActivity(u);
            }
        });

        listaAsignatura.setEmptyView(findViewById(R.id.tvMensajeSinAsignatura));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.asignatura,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_azul:
                Intent intent = new Intent(this, fm_crearAsignatura.class);
                intent.putExtra("id_Semestre", id_Semestre);
                intent.putExtra("verificar","crearAsignatura");
                startActivity(intent);
                return true;

            case android.R.id.home:
                finish();
                return true;

    }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecciona");
        menu.add(0,v.getId(),0,"Eliminar");

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        uno = (AsignaturaItem) listaAsignatura.getItemAtPosition(info.position);

        if(item.getTitle() == "Eliminar"){
            final AlertDialog.Builder dialogoEliminar = new AlertDialog.Builder(this);
            dialogoEliminar.setTitle("Eliminar");
            dialogoEliminar.setMessage("¿Desea eliminar la asignatura?");
            dialogoEliminar.setCancelable(false);
            dialogoEliminar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eliminar(uno.getNombre());
                    adapter.eliminar(info.position);
                    listaAsignatura.invalidateViews();

                }
            });

            dialogoEliminar.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialogoEliminar.show();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    public void actualizarLista(){
        administrador = new AdministradorDB(this);

        c = administrador.mostrarAsignaturas(id_Semestre);
        arrayAsignatura = new ArrayList<AsignaturaItem>();
        if(c.moveToFirst()) {

            do {

                int id_asignatura=c.getInt(c.getColumnIndex("id"));
                String nombre = c.getString(c.getColumnIndex("nombre"));
                int color = c.getInt(c.getColumnIndex("color"));
                int icono = c.getInt(c.getColumnIndex("icono"));
                arrayAsignatura.add(new AsignaturaItem(id_asignatura,nombre,color,icono));
            }while(c.moveToNext());

            adapter = new AsignaturaCursorAdapter(this, arrayAsignatura);
            listaAsignatura.setAdapter(adapter);
            administrador.Close();
        }
    }

    public void eliminar(String nombre){
        administrador = new AdministradorDB(this);
        //administrador.eliminarAsignatura(nombre);
        Cursor f = administrador.mostrarAsignaturaDeUnSemestre(id_Semestre,nombre);
        f.moveToFirst();
        int id = f.getInt(f.getColumnIndex("id"));
        administrador.eliminarAsignatura(id);
    }


}

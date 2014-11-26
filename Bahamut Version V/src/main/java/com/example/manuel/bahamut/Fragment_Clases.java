package com.example.manuel.bahamut;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Manuel on 22/11/2014.
 */
public class Fragment_Clases extends Fragment{

    private ImageView agregar;
    private AdministradorDB administrador;
    private ListView listaClases;
    private int idAsignatura;
    private ArrayList<Clase> arrayClase;
    private ClaseCursorAdapter adapter;
    private int iconoAsignatura;
    private int colorAsignatura;
    AdapterView.AdapterContextMenuInfo info;
    Clase uno;

    public static Fragment_Clases newInstance(Bundle argumento){
        Fragment_Clases f = new Fragment_Clases();
        if(argumento!=null){
            f.setArguments(argumento);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clases,container,false);
        agregar = (ImageView) rootView.findViewById(R.id.ivAgregarClase);
        listaClases = (ListView) rootView.findViewById(R.id.lvClases);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idAsignatura = getArguments().getInt("idAsignatura");
        iconoAsignatura = getArguments().getInt("iconoAsignatura");
        colorAsignatura = getArguments().getInt("colorAsignatura");

        actualizarLista();
        registerForContextMenu(listaClases);
    }

    @Override
    public void onResume() {
        super.onResume();
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),fm_crearClase.class);

                i.putExtra("idAsignatura",idAsignatura);
                startActivity(i);
            }
        });
        actualizarLista();
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
        uno = (Clase) listaClases.getItemAtPosition(info.position);

        if(item.getTitle() == "Eliminar"){
            final AlertDialog.Builder dialogoEliminar = new AlertDialog.Builder(getActivity());
            dialogoEliminar.setTitle("Eliminar");
            dialogoEliminar.setMessage("¿Desea eliminar la clase?");
            dialogoEliminar.setCancelable(false);
            dialogoEliminar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eliminar(uno.getId());
                    adapter.eliminar(info.position);
                    listaClases.invalidateViews();

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

        administrador= new AdministradorDB(getActivity());
        Cursor c = administrador.mostrarClases(idAsignatura);
        arrayClase = new ArrayList<Clase>();
        if(c.moveToFirst()){

            do{
                int id = c.getInt(c.getColumnIndex("id_clase"));
                String tipo = c.getString(c.getColumnIndex("tipo"));
                String FechaDesde =c.getString(c.getColumnIndex("CfechaDesde"));
                String FechaHasta =c.getString(c.getColumnIndex("CfechaHasta"));
                String HoraDesde =c.getString(c.getColumnIndex("CtiempoDesde"));
                String HoraHasta =c.getString(c.getColumnIndex("CtiempoHasta"));
                String sala =c.getString(c.getColumnIndex("sala"));
                String semana =c.getString(c.getColumnIndex("semana"));
                arrayClase.add(new Clase(id,tipo,sala,FechaDesde,FechaHasta,HoraDesde,HoraHasta,semana));

            }while(c.moveToNext());

            adapter = new ClaseCursorAdapter(getActivity(),arrayClase,iconoAsignatura,colorAsignatura);
            listaClases.setAdapter(adapter);
        }
    }

    public void eliminar(int id){
        administrador = new AdministradorDB(getActivity());
        administrador.eliminarClase(id);
        administrador.Close();
    }
}

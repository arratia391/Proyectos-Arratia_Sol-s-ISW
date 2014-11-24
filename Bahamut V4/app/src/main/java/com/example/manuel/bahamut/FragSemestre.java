package com.example.manuel.bahamut;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Manuel on 02/11/2014.
 */
public class FragSemestre extends Fragment {

    private AdministradorDB administrador;
    private Cursor c;
    private SemestreCursorAdapter adapter;
    ListView listaSemestre;
    private ArrayList<SemestreItem> arraySemestre;
    AdapterView.AdapterContextMenuInfo info;
    SemestreItem uno;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.frag_semestre,container,false);

        listaSemestre = (ListView) rootView.findViewById((R.id.lvSemestres));
        listaSemestre.setEmptyView(rootView.findViewById(R.id.tvMensajeSinSemestre));

        registerForContextMenu(listaSemestre);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        administrador = new AdministradorDB(getActivity().getApplicationContext());
        c = administrador.cargar();
        arraySemestre = new ArrayList<SemestreItem>();
        if(c.moveToFirst()) {

            do {

                String nombre = c.getString(c.getColumnIndex("nombre"));
                String fecha_desde = c.getString(c.getColumnIndex("fechadesde"));
                String fecha_hasta = c.getString(c.getColumnIndex("fechahasta"));
                arraySemestre.add(new SemestreItem(nombre,fecha_desde,fecha_hasta));
            }while(c.moveToNext());

            adapter = new SemestreCursorAdapter(getActivity().getApplicationContext(), arraySemestre);
            listaSemestre.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.semestre,menu);
    }

//Desactiva los elementos del Action Bar que no corresponden al fragment.
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    //Actualiza los datos del ListView luego de llenar el formulario.
    @Override
    public void onResume() {
        super.onResume();
        actualizarLista();

        listaSemestre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent o = new Intent(getActivity(),FragAsignatura.class);
                o.putExtra("idSemestre",position+1);
                startActivity(o);
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Selecciona");
        menu.add(0,v.getId(),0,"Eliminar");
        menu.add(0,v.getId(),0,"Editar");

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)menuInfo;
    }

    //Arranca el formulario al presionar la tecla +.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add:
                Intent intent = new Intent(getActivity(),fm_crearSemestre.class);
                intent.putExtra("verificar","crear");
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onContextItemSelected(MenuItem item) {

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        uno = (SemestreItem) listaSemestre.getItemAtPosition(info.position);

        if(item.getTitle() == "Eliminar"){
            final AlertDialog.Builder dialogoEliminar = new AlertDialog.Builder(getActivity());
            dialogoEliminar.setTitle("Eliminar");
            dialogoEliminar.setMessage("Si se elimina un semestre se eliminaran todas las asignaturas contenidas en el.\n¿Desea " +
                    "eliminarlo de todos   modos?");
            dialogoEliminar.setCancelable(false);
            dialogoEliminar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eliminar(uno.getNombre());
                    adapter.eliminar(info.position);
                    listaSemestre.invalidateViews();

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
        if(item.getTitle() == "Editar"){
            Intent intent = new Intent(getActivity(),fm_crearSemestre.class);
            intent.putExtra("verificar",uno.getNombre());
            startActivity(intent);
            return true;
        }

                return super.onContextItemSelected(item);
   }


    public void eliminar(String nombre){
        administrador = new AdministradorDB(getActivity());
        administrador.eliminar(nombre);
    }

    public void actualizarLista(){

        administrador = new AdministradorDB(getActivity().getApplicationContext());

        c = administrador.cargar();
        arraySemestre = new ArrayList<SemestreItem>();
        if(c.moveToFirst()) {
            do {
                String nombre = c.getString(c.getColumnIndex("nombre"));
                String fecha_desde = c.getString(c.getColumnIndex("fechadesde"));
                String fecha_hasta = c.getString(c.getColumnIndex("fechahasta"));
                arraySemestre.add(new SemestreItem(nombre,fecha_desde,fecha_hasta));
            }while(c.moveToNext());

            adapter = new SemestreCursorAdapter(getActivity().getApplicationContext(), arraySemestre);
            listaSemestre.setAdapter(adapter);

        }

    }
}

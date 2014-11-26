package com.example.manuel.bahamut;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
public class FragListaAsignaturas extends Fragment {


    private ListView listaAsignatura;
    private int id_Semestre;
    private AdministradorDB administrador;
    private Cursor c;
    private ArrayList<AsignaturaItem> arrayAsignatura;
    private AsignaturaCursorAdapter adapter;
    AdapterView.AdapterContextMenuInfo info;
    AsignaturaItem uno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_listasignaturas,container,false);

        listaAsignatura = (ListView) rootView.findViewById((R.id.lvListaAsignaturas));
        listaAsignatura.setEmptyView(rootView.findViewById(R.id.tvMensajeSinAsignatura2));
        registerForContextMenu(listaAsignatura);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        administrador = new AdministradorDB(getActivity());

        c = administrador.cargarAsignatura();
        arrayAsignatura = new ArrayList<AsignaturaItem>();
        if(c.moveToFirst()) {

            do {

                String nombre = c.getString(c.getColumnIndex("nombre"));
                int color = c.getInt(c.getColumnIndex("color"));
                int icono = c.getInt(c.getColumnIndex("icono"));

                arrayAsignatura.add(new AsignaturaItem(nombre,color,icono));
            }while(c.moveToNext());

            adapter = new AsignaturaCursorAdapter(getActivity(), arrayAsignatura);
            listaAsignatura.setAdapter(adapter);
            administrador.Close();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarLista();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.asignatura,menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        super.onPrepareOptionsMenu(menu);
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        uno = (AsignaturaItem) listaAsignatura.getItemAtPosition(info.position);

        if(item.getTitle() == "Eliminar"){
            final AlertDialog.Builder dialogoEliminar = new AlertDialog.Builder(getActivity());
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
        if(item.getTitle() == "Editar") {
            return true;
        }

        return super.onContextItemSelected(item);


    }

    public void actualizarLista(){
        administrador = new AdministradorDB(getActivity());
        c = administrador.cargarAsignatura();
        arrayAsignatura = new ArrayList<AsignaturaItem>();
        if(c.moveToFirst()) {

            do {

                String nombre = c.getString(c.getColumnIndex("nombre"));
                int color = c.getInt(c.getColumnIndex("color"));
                int icono = c.getInt(c.getColumnIndex("icono"));

                arrayAsignatura.add(new AsignaturaItem(nombre,color,icono));
            }while(c.moveToNext());

            adapter = new AsignaturaCursorAdapter(getActivity(), arrayAsignatura);
            listaAsignatura.setAdapter(adapter);
            administrador.Close();
        }
    }

    public void eliminar(String nombre){
        administrador = new AdministradorDB(getActivity());
        Cursor aux = administrador.mostrarAsignaturas(id_Semestre);

        administrador.eliminarAsignatura(nombre);
    }

}


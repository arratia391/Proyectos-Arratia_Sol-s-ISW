package com.example.manuel.bahamut;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Manuel on 22/11/2014.
 */
public class Fragment_Evaluaciones extends Fragment {

    private ImageView agregar;
    private int idAsignatura;

    public static Fragment_Evaluaciones newInstance(Bundle argumento){
        Fragment_Evaluaciones f = new Fragment_Evaluaciones();
        if(argumento!=null){
            f.setArguments(argumento);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evaluaciones,container,false);
        agregar = (ImageView) rootView.findViewById(R.id.ivagregarEvaluaciones);
        idAsignatura = getArguments().getInt("idAsignatura");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),fm_crearEvaluaciones.class);
                i.putExtra("idAsignatura",idAsignatura);
                startActivity(i);
            }
        });

    }


}

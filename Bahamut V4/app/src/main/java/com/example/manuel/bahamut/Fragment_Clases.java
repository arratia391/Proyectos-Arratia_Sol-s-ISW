package com.example.manuel.bahamut;

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
public class Fragment_Clases extends Fragment{

    private ImageView agregar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clases,container,false);
        agregar = (ImageView) rootView.findViewById(R.id.ivAgregarClase);
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),fm_crearClase.class);
                startActivity(i);
            }
        });
    }
}

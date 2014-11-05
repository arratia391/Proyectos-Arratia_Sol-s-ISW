package com.example.manuel.bahamut;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Manuel on 02/11/2014.
 */
public class FragSemestre extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.frag_semestre,container,false);

        ListView listaSemestre = (ListView) rootView.findViewById((R.id.lvSemestres));
        listaSemestre.setEmptyView(rootView.findViewById(R.id.tvMensajeSinSemestre));



        return rootView;
    }
}

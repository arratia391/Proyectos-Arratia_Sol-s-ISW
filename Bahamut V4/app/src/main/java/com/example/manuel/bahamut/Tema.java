package com.example.manuel.bahamut;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Manuel on 24/11/2014.
 */
public class Tema extends Fragment implements View.OnClickListener{

    private ImageView uno;
    private ImageView dos;
    private ImageView tres;
    private ImageView cuatro;
    private ImageView cinco;
    private ImageView seis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temas,container,false);

        uno = (ImageView) rootView.findViewById(R.id.ivuno);
        dos = (ImageView) rootView.findViewById(R.id.ivdos);
        tres = (ImageView) rootView.findViewById(R.id.ivtres);
        cuatro = (ImageView) rootView.findViewById(R.id.ivcuatro);
        cinco = (ImageView) rootView.findViewById(R.id.ivcinco);
        seis = (ImageView) rootView.findViewById(R.id.ivseis);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivuno:
                ActionBar actionBar = getActivity().getActionBar();
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D34A54")));
                SharedPreferences preferencias = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = preferencias.edit();
                ed.putString("colortema", "#D34A54");
                ed.commit();
                break;

            case R.id.ivdos:
                ActionBar actionBar2 = getActivity().getActionBar();
                actionBar2.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EC6F53")));
                SharedPreferences preferencias2 = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed2 = preferencias2.edit();
                ed2.putString("colortema", "#EC6F53");
                ed2.commit();
                break;

            case R.id.ivtres:
                ActionBar actionBar3 = getActivity().getActionBar();
                actionBar3.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EEC04E")));
                SharedPreferences preferencias3 = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed3 = preferencias3.edit();
                ed3.putString("colortema", "#EEC04E");
                ed3.commit();
                break;

            case R.id.ivcuatro:
                ActionBar actionBar4 = getActivity().getActionBar();
                actionBar4.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#547762")));
                SharedPreferences preferencias4 = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed4 = preferencias4.edit();
                ed4.putString("colortema", "#547762");
                ed4.commit();
                break;

            case R.id.ivcinco:
                ActionBar actionBar5 = getActivity().getActionBar();
                actionBar5.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#54B487")));
                SharedPreferences preferencias5 = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed5 = preferencias5.edit();
                ed5.putString("colortema", "#54B487");
                ed5.commit();
                break;

            case R.id.ivseis:
                ActionBar actionBar6 = getActivity().getActionBar();
                actionBar6.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#697DA8")));
                SharedPreferences preferencias6 = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed6 = preferencias6.edit();
                ed6.putString("colortema", "#697DA8");
                ed6.commit();
                break;
        }
    }
}

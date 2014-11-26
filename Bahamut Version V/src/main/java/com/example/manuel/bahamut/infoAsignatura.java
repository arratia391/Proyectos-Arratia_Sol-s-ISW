package com.example.manuel.bahamut;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Manuel on 22/11/2014.
 */



public class infoAsignatura extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;

    // Host de todo el contenido
    ViewPager mViewPager;
    private int posicionMenu;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_infoasignatura);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);



        bundle = getIntent().getExtras();
        String nombreAsignatura =bundle.getString("nombreAsignatura");
        int iconoAsignatura = bundle.getInt("iconoAsignatura");

        getActionBar().setDisplayShowHomeEnabled(true);  // hides action bar icon
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle(nombreAsignatura);
        getActionBar().setIcon(iconoAsignatura);



        // Crea el Adapter que devuelve un Fragment por cada sección
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());

        // Establece el ViewPager con las secciones del Adapter
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        //Obtener una referencia a la barra de acciones de la Activity porque en ella se situarán
        //  las pestañas, y se especifica que se va a usar el modo de navegación con pestañas (tabs)
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema","#992416"))));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema","#992416"))));




        // Se debe crear un TabListener para que se puedan añadir acciones a los cambios de
        //  selección que realice el usuario en las pestañas.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            //Se ha seleccionado una pestaña
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                //Cambiar a la página correspondiente a la pestaña seleccionada
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

        };

        //Indica aquí los títulos que van a tener las pestañas. Se crearán tantas pestañas como
        //  títulos se indiquen en este array.


        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_templo).setText("Clases").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.icon_lapiz).setText("Evaluaciones").setTabListener(tabListener));


        //Cuando el usuario cambie de página usando el arrastre lateral en lugar de las pestañas,
        //  también se debe cambiar de pestaña seleccionada
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Establece por Default la Posición 1
        mViewPager.setCurrentItem(0);
    }

    /**
     * Devuelve un Fragment Correspondiente en cada Seccion/Tab/Pagina
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = new Fragment();


            switch (position) {
                case 0:
                    posicionMenu=0;
                    fragment = Fragment_Clases.newInstance(bundle); // Fragment Clases
                    break;
                case 1:
                    posicionMenu=1;
                    fragment = new Fragment_Evaluaciones().newInstance(bundle); // Fragment Evaluaciones
                    break;

                default:
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Total de páginas
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Clases";
                case 1:
                    return "Evaluaciones";

            }

            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infoasignatura,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.add3);
        if(posicionMenu==0)
            item.setTitle("Clase Regular");
        if(posicionMenu==1)
            item.setTitle("Evaluacion");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.add3:
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
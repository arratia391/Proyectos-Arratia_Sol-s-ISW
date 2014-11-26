package com.example.manuel.bahamut;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//Implementa la clase interfaz.
public class MyActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment nNavigationDrawerFragment;
    private CharSequence mTitle;
    private int posicionActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hereda el metodo onCreate.
        super.onCreate(savedInstanceState);
        //Enlaza la clase java con su respectivo layout
        setContentView(R.layout.activity_my);

        mTitle = getTitle();

        View dra= findViewById(R.id.drawer_layout);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(prefe.getString("colortema","#992416"))));
        dra.setBackgroundResource(prefe.getInt("fondoTema",R.drawable.madera));
        //Instancia el fragment contenido en el layout "activity_my.
        nNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);


        /* navigation_drawer = fragment contenido en el layout "activity_my".
        *  drawer_Layout = layout "my_activity".
        * */
        nNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
    }



    /* Funcion que nos permite navegar por el
        Navigation Drawable y sus secciones.*/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        /*  Nos sirve para testear y verificar el valor de las variables
            Log.i("verificar", "posicion" + position);*/


        posicionActual = position;
        Fragment fragment = null;
        switch (position) {

            //Cada caso debe llamar un fragment, de lo contrario se cae.
            case 0:
                mTitle = "About";
                fragment = new FragHoy();
                break;

            case 1:
                mTitle = getString(R.string.title_section1);
                fragment = new FragHoy();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                fragment = new FragCalendario();
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
               fragment = new FragSemestre();
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                fragment = new FragListaAsignaturas();
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                fragment = new Tema();
                break;
        }
        if (!isFinishing()) {
            FragmentManager fragmentManager = getFragmentManager();
            //Inicia los fragmentos, si el caso 0 no llama a nadie, no inicia.
            fragmentManager.beginTransaction().replace(R.id.container, fragment)
                    .commitAllowingStateLoss();
            restoreActionBar();
        }



    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!nNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.my, menu);

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId())
        {
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);


    }




}



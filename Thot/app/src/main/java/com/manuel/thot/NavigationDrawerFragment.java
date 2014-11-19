package com.manuel.thot;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Recuerda la posicion del item seleccionado.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     * Puntero a la actual llamada de la actividad.
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private ArrayList<NavDrawerItem> arrayDrawer;
    //arreglos de los titulos y iconos de las secciones
    private String[] nMenuTitulos;
    private TypedArray nMenuIcons;
    private NavDrawerListAdapter adapter;

    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);

    }

    //Se toma una referencia al Activity para poder después llamar a mCallbacks y que se ejecute el código en el Activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /*En el metodo onCreate, recuperamos de las preferencias si el usuario ya había abierto el
    Drawer alguna vez (porque si no hay que mostrarselo,
    para que sepa que está ahi) y también recuperamos del
    savedInstanceState la posición del Drawer en la que estabamos puestos.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Se especifica que el fragment tiene un menu de opciones.
        setHasOptionsMenu(true);
    }

    //retorna la posicion del drawer en la cual se encuentra.
    public int getNavigationIndexPosition(){
        return mCurrentSelectedPosition;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Se instancia la lista a usar como Drawer.
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //Se instancia la cabecera y se agrega a la lista a la vez.
        View header = getActivity().getLayoutInflater().inflate(R.layout.header, null);
        mDrawerListView.addHeaderView(header);

        nMenuTitulos = getResources().getStringArray(R.array.secciones);
        nMenuIcons = getResources().obtainTypedArray(R.array.iconos);

        //Funcion al presionar una seccion
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        arrayDrawer = new ArrayList<NavDrawerItem>();
        //Hoy
        arrayDrawer.add(new NavDrawerItem(nMenuTitulos[0], nMenuIcons.getResourceId(0, -1)));
        //Calendario
        arrayDrawer.add(new NavDrawerItem(nMenuTitulos[1], nMenuIcons.getResourceId(1, -1)));
        //Semestre
        arrayDrawer.add(new NavDrawerItem(nMenuTitulos[2], nMenuIcons.getResourceId(2, -1)));
        //Evaluaciones
        arrayDrawer.add(new NavDrawerItem(nMenuTitulos[3], nMenuIcons.getResourceId(3, -1)));

        // Recicla el Typed Array.
        nMenuIcons.recycle();

        adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(), arrayDrawer);

        //Añadir la estructura al navigation Drawer (iconos y titulos)
        mDrawerListView.setAdapter(adapter);


        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        //Se intancia un fragment, por ejemplo: el que se usara sera el del activity_my.
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // se le agrega una sombra a la derecha al deslizar el drawer.
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Se activa el boton del Action Bar para deslizar el drawer.
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //Estructura del action bar (Icono y titulos)
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* Icono del Action Bar del drawer */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {

                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Establece la accion del icono del action bar del drawer..
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    private void selectItem(int position) {

        mCurrentSelectedPosition = position;

        if (mDrawerListView != null) {
            //Selecciona las secciones del ListView dependiendo su posicion (al tocar una seccion se pone de color).
            mDrawerListView.setItemChecked(position, true);
        }

        if (mDrawerLayout != null) {
            //mFragmentContainerView es la vista del fragment instanciado del activity_my.
            //Cierra el drawer despues de seleccionar una seccion.
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            /*Llama al callback que hemos creado antes para comunicarse con el
             Activity, y decirle que llame al método onNavigationDrawerITemSelected(position).*/
            mCallbacks.onNavigationDrawerItemSelected(position);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);

            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    //Acciones del Action Bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */

}
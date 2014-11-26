package com.example.manuel.bahamut;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Manuel on 06/11/2014.
 */

//Interfaz para que el usuario escoja una fecha y la retorne como dato.
public class Date_Picker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface DatePickerFragmentListener{
        Fecha onFinishDatePickerDialog(int year, int month, int day);
    }

    public interface DatePickerFragmentListenerDesde{
        Fecha onFinishDatePickerDialogDesde(int year, int month, int day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DatePickerFragmentListenerDesde activity = (DatePickerFragmentListenerDesde) getActivity();
        activity.onFinishDatePickerDialogDesde(year, monthOfYear, dayOfMonth);


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Datos que seleccionara el usuario.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Creamos la instancia del DatePicker para devolverla.
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }




}

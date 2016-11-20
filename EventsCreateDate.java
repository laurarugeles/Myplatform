package com.example.laura.myplatform;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by laura on 17/10/2016.
 */

public class EventsCreateDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText dateEditText;
    int yearDialog, monthDialog, dayDialog;
    Bundle bundle;
    public EventsCreateDate(View view){
        dateEditText= (EditText) view;
    }


    public Dialog onCreateDialog(Bundle saveInstanceState){
        final Calendar calendar= Calendar.getInstance();
        yearDialog = calendar.get(Calendar.YEAR);
        monthDialog = calendar.get(Calendar.MONTH);
        dayDialog = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getContext(),this,yearDialog,monthDialog,dayDialog);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String resultado = year+"-"+(month+1)+"-"+dayOfMonth;
        dateEditText.setText(resultado);
    }
}

package com.example.laura.myplatform;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Created by laura on 17/10/2016.
 */

public class EventsCreateTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Context context;
    EditText timeEditText;
    public EventsCreateTime(View view){
        timeEditText = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(getContext(),this,hour,minute, android.text.format.DateFormat.is24HourFormat(getContext()));
        return dialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String resultado = hourOfDay+":"+minute;
        timeEditText.setText(resultado);
    }
}

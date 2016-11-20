package com.example.laura.myplatform;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsCreateCategories extends Fragment {

    int intRBLocation;
    RadioButton radioButtonLocation;
    EditText editTextLocationDetails,editTextDate, editTextTime,editTextTimeInitial;
    Button buttonSend;

    public EventsCreateCategories() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_events_create_categories, container, false);


        final RadioGroup radioGroupLocation = (RadioGroup) rootView.findViewById(R.id.radio_group_location_categories);
        //se definen los edittext en los cuales se mostraran la fecha y la hora del DatePicker y TimePicker
        //respectivamente
        editTextDate = (EditText) rootView.findViewById(R.id.editText_date_categories);
        editTextTime = (EditText) rootView.findViewById(R.id.editText_time_categories);
        editTextTimeInitial = (EditText) rootView.findViewById(R.id.editText_Time_Initial_categories);
        editTextLocationDetails = (EditText) rootView.findViewById(R.id.edittext_detalles_categories);
        //define los buttons que se encuentran en el layout
        buttonSend = (Button) rootView.findViewById(R.id.buttonEventCreate_categories);
        //Cuando el editText sea seleccionado se mostrara un dialogfragment para seleccionar fecha

        editTextDate.setRawInputType(InputType.TYPE_NULL);
        editTextTime.setRawInputType(InputType.TYPE_NULL);
        editTextTimeInitial.setRawInputType(InputType.TYPE_NULL);
        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    EventsCreateDate eventsCreateDate = new EventsCreateDate(v);
                    ((DialogFragment) eventsCreateDate).show(getFragmentManager(),"DatePicker");

                }
            }
        });

        //Cuando el editText sea seleccionado se mostrara un dialogfragment para seleccionar hora
        editTextTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    EventsCreateTime eventsCreateTime = new EventsCreateTime(v);
                    ((DialogFragment)eventsCreateTime).show(getFragmentManager(),"HourPicker");
                }
            }
        });


        editTextTimeInitial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    EventsCreateTimeInitial eventsCreateTimeInitial = new EventsCreateTimeInitial(v);
                    ((DialogFragment)eventsCreateTimeInitial).show(getFragmentManager(),"HourPicker");
                }
            }
        });

        //define los dos spinner que se encuentran en el layout
        final MaterialBetterSpinner spinMin = (MaterialBetterSpinner) rootView.findViewById(R.id.spinMinPeople_categories);
        final MaterialBetterSpinner spinMax = (MaterialBetterSpinner) rootView.findViewById(R.id.spinMaxPeople_categories);

        TextView textCategory = (TextView) rootView.findViewById(R.id.textviewSubject_categories);
        textCategory.setText(Communicator.interesSelectionCreateEvent);

        //Obtiene la consulta de las materias del estudiante
        MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> subjectFromAct = activity.getSubjectAct();
        //Cadena que se mostrara en los spinner
        String[] spinNumsMin = getResources().getStringArray(R.array.numMinP);
        String[] spinNumsMax = getResources().getStringArray(R.array.numMaxP);
        //adapter que permitira mostrar las cadenas en los spinner
        final ArrayAdapter<String> arrayAdapterSpinMin = new ArrayAdapter<String>(getContext(),R.layout.events_create_spinner_textview_min,R.id.textviewspinnerMin,spinNumsMin);
        final ArrayAdapter<String> arrayAdapterSpinMax = new ArrayAdapter<String>(getContext(),R.layout.events_create_spinner_textview_max,R.id.textviewspinnerMax,spinNumsMax);
        //muestra contenido de spinner
        spinMin.setAdapter(arrayAdapterSpinMin);
        spinMax.setAdapter(arrayAdapterSpinMax);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultspinmin = spinMin.getText().toString();
                String resultspinmax = spinMax.getText().toString();
                String resulteditTextDate = editTextDate.getText().toString();
                String resulteditTextTime = editTextTime.getText().toString();
                String resulteditTextTimeInitial = editTextTimeInitial.getText().toString();
                String resulteditTextLocationDetails = editTextLocationDetails.getText().toString();
                int resultMin = 3;
                int resultMax = 3;


                //comprobar que radiobutton tomo algo
                //String radiobutton = getString(intRBLocation);
                int maxlength = 250;
                //Colocar filtro de maximo 250 caracteres se pueden escribir
                editTextLocationDetails.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});

                if (resultspinmin.length() == 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alertDialog.setIcon(R.drawable.ic_info_outline);
                    alertDialog.setTitle("Alerta").setCancelable(false);
                    alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.setMessage("No se ha seleccionado minimo de estudiantes").create();
                    alertDialog.show();
                } else {

                    if (resultspinmax.length() == 0) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        alertDialog.setIcon(R.drawable.ic_info_outline);
                        alertDialog.setTitle("Alerta").setCancelable(false);
                        alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.setMessage("No se ha seleccionado máximo de estudiantes").create();
                        alertDialog.show();
                    } else {
                        resultMin = Integer.parseInt(resultspinmin);
                        resultMax = Integer.parseInt(resultspinmax);

                        if (resultMax < resultMin) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                            alertDialog.setIcon(R.drawable.ic_info_outline);
                            alertDialog.setTitle("Alerta").setCancelable(false);
                            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.setMessage("El máximo de integrantes no puede ser menor que el minimo de integrantes").create();
                            alertDialog.show();
                        } else {
                            if (resulteditTextDate.equals("")) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                alertDialog.setIcon(R.drawable.ic_info_outline);
                                alertDialog.setTitle("Alerta").setCancelable(false);
                                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.setMessage("No se ha seleccionado fecha para el evento").create();
                                alertDialog.show();
                            } else {
                                if (resulteditTextTimeInitial.equals("")) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                    alertDialog.setIcon(R.drawable.ic_info_outline);
                                    alertDialog.setTitle("Alerta").setCancelable(false);
                                    alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialog.setMessage("No se ha seleccionado hora de inicio para el evento").create();
                                    alertDialog.show();
                                } else {
                                    if (resulteditTextTime.equals("")) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                        alertDialog.setIcon(R.drawable.ic_info_outline);
                                        alertDialog.setTitle("Alerta").setCancelable(false);
                                        alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        alertDialog.setMessage("No se ha seleccionado hora de fin para el evento").create();
                                        alertDialog.show();
                                    } else {
                                        if (editTextLocationDetails.equals("")) {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                            alertDialog.setIcon(R.drawable.ic_info_outline);
                                            alertDialog.setTitle("Alerta").setCancelable(false);
                                            alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            alertDialog.setMessage("No se ha ingresado informacion detallada de la ubicacion").create();
                                            alertDialog.show();
                                        } else {
                                            //toma el id numerico del radiobutton que este seleccionado
                                            intRBLocation = radioGroupLocation.getCheckedRadioButtonId();
                                            //toma el id que extrae del radio group e identifica cual de los radiobutton's es el
                                            //que esta seleccionado
                                            radioButtonLocation = (RadioButton) rootView.findViewById(intRBLocation);
                                            String resultradiobuttonLocation = radioButtonLocation.getText().toString();
                                            if (resultradiobuttonLocation.equals("")) {
                                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                                alertDialog.setIcon(R.drawable.ic_info_outline);
                                                alertDialog.setTitle("Alerta").setCancelable(false);
                                                alertDialog.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                alertDialog.setMessage("No se ha seleccionado la ubicacion").create();
                                                alertDialog.show();
                                            } else {
                                                ((MainActivity) getActivity()).onStartEventsCreationCategory(resultspinmin,
                                                        resultspinmax,
                                                        resulteditTextDate,
                                                        resulteditTextTimeInitial,
                                                        resultradiobuttonLocation,
                                                        Communicator.categoriesSelectionCreateEvent,
                                                        Communicator.interesSelectionCreateEvent,
                                                        resulteditTextLocationDetails,
                                                        resulteditTextTime);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        });


        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                        CategoriesDetailEvents categoriesDetailEvents = new CategoriesDetailEvents();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,categoriesDetailEvents).commit();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }


}

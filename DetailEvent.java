package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEvent extends Fragment {


    public DetailEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_detail_event, container, false);

        getActivity().setTitle("Detalle del evento");

        EditText idgroup = (EditText) rootView.findViewById(R.id.detailEventidgroup);
        EditText fecha = (EditText) rootView.findViewById(R.id.detailEventfecha);
        EditText horainicio = (EditText) rootView.findViewById(R.id.detailEventhorainicio);
        EditText minstud = (EditText) rootView.findViewById(R.id.detailEventminstud);
        EditText maxstud = (EditText) rootView.findViewById(R.id.detailEventmaxstud);
        EditText lugar = (EditText) rootView.findViewById(R.id.detailEventlugar);
        EditText descripcion = (EditText) rootView.findViewById(R.id.detailEventdescripcion);
        EditText horafin = (EditText) rootView.findViewById(R.id.detailEventhorafin);
        EditText studname = (EditText) rootView.findViewById(R.id.detailEventstudname);
        EditText email = (EditText) rootView.findViewById(R.id.detailEventEmail);
        EditText inscritos = (EditText) rootView.findViewById(R.id.detailEventinscrito);
        EditText materia = (EditText) rootView.findViewById(R.id.detailEventmateria);


        idgroup.setText(Communicator.EventsDetailidgrupo);
        fecha.setText(Communicator.EventsDetailfecha);
        horainicio.setText(Communicator.EventsDetailhorainicio);
        minstud.setText(Communicator.EventsDetailminstud);
        maxstud.setText(Communicator.EventsDetailmaxstud);
        lugar.setText(Communicator.EventsDetaillugar);
        descripcion.setText(Communicator.EventsDetaildescripcion);
        horafin.setText(Communicator.EventsDetailhorafin);
        studname.setText(Communicator.EventsDetailstudname);
        email.setText(Communicator.EventsDetailEmail);
        inscritos.setText(Communicator.EventsDetailinscritos);
        materia.setText(Communicator.subjectSelectionCreateEvent);

        Button buttonSubscribe = (Button) rootView.findViewById(R.id.buttonInscribirse);

        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Communicator.subscribeSubjectorEvent == true){
                    ((MainActivity)getActivity()).onSubscribeDetailEventSubject(Communicator.EventsDetailidgrupo);
                }else{
                    ((MainActivity)getActivity()).onSubscribeDetailEventInteres(Communicator.IDeventsDetailSelectedInteres);
                }
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        switch (Communicator.subjectCatergoriesSuggestionsIdentifier){
                            case "CDE":
                                CategoriesDetailEvents categoriesDetailEvents = new CategoriesDetailEvents();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,categoriesDetailEvents).commit();
                                break;

                            case "SSDE":
                                ((MainActivity)getActivity()).onSuggestionBegin();
                                break;

                            case "MLVE":
                                MainListviewEvents mainListviewEvents = new MainListviewEvents();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,mainListviewEvents).commit();
                                break;
                            case "SuggInt":
                                ((MainActivity)getActivity()).onStartSuggestionInteres();
                                break;

                        }

                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

}

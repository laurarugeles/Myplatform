package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEventProfileUnsubscribe extends Fragment {


    public DetailEventProfileUnsubscribe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_event_profile_unsubscribe, container, false);

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
        materia.setText(Communicator.profileNameSubjectEventIdentifier);

        Button buttonEstudInscritos = (Button) rootView.findViewById(R.id.buttonStudentPUs);
        Button buttonUnsusbcribeGroup = (Button) rootView.findViewById(R.id.buttonUnsubscribeGroup);

        buttonEstudInscritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Communicator.profileSelectionIdentifierUnsubscribe == "PSES"){
                    Communicator.profileSelectionIdentifierStudentInscritos="PSES";
                    ((MainActivity)getActivity()).onDetailStudentInformation(Communicator.EventsDetailidgrupo);
                }else{
                    Communicator.profileSelectionIdentifierStudentInscritos="PIES";
                    ((MainActivity)getActivity()).onDetailStudentInformationInteres(Communicator.EventsDetailidgrupo);
                }

            }
        });

        buttonUnsusbcribeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Communicator.profileSelectionIdentifierUnsubscribe == "PSES"){
                    ((MainActivity)getActivity()).onDesinscribirseProfileEventsCreate(Communicator.EventsDetailidgrupo);
                }else{
                    ((MainActivity)getActivity()).onDesinscribirseProfileEventsCreateInteres(Communicator.EventsDetailidgrupo);
                }
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {

                    switch (Communicator.profileSelectionIdentifierUnsubscribe){
                        case "PSES":
                            ProfileSubjectEventSubscribe profileSubjectEventSubscribe = new ProfileSubjectEventSubscribe();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,profileSubjectEventSubscribe).commit();
                            break;
                        case "PIES":
                            ProfileInteresEventSubscribe profileInteresEventSubscribe = new ProfileInteresEventSubscribe();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,profileInteresEventSubscribe).commit();
                            break;
                        default:
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

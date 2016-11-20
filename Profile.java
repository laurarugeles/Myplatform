package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Perfil");

        Button buttonsubInscritos = (Button) rootView.findViewById(R.id.buttonEventosMateriasInscritos);
        Button buttoninterInscritos = (Button) rootView.findViewById(R.id.buttonEventosinteresesInscritos);
        Button buttonsubCreados = (Button) rootView.findViewById(R.id.buttonEventosMateriasCreados);
        Button buttoninterCreados = (Button) rootView.findViewById(R.id.buttonEventosInteresesCreados);

        buttonsubInscritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Communicator.IDeventProfileSubjectEventSubscribe.clear();
                ((MainActivity)getActivity()).onProfileSubjectEventsSubscribe();
            }
        });

        buttoninterInscritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Communicator.IDeventProfileInteresEventSubscribe.clear();
                ((MainActivity)getActivity()).onProfileInteresEventSubscribe();
            }
        });

        buttonsubCreados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator.IDeventProfileOwnSubjectEventCreate.clear();
                ((MainActivity)getActivity()).onProfileOwnSubjectEventsCreate();
            }
        });

        buttoninterCreados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //probando detalle de perfil
                Communicator.IDeventProfileOwnInteresEventCreate.clear();
                ((MainActivity)getActivity()).onProfileOwnInteresEventCreate();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;
    }

}

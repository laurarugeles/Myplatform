package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileOwnInteresEventCreate extends Fragment {


    public ProfileOwnInteresEventCreate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_own_interes_event_create, container, false);

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<String> interesEventCreate = activity.getFinishProfileOwnInteresEventsCreate();

        getActivity().setTitle("Eventos de interes creados");

        ListView listViewInteresCreate = (ListView) rootView.findViewById(R.id.listviewProfileOwnInteresEventCreate);

        ArrayAdapter<String> adapterInteresCreate = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,interesEventCreate);

        listViewInteresCreate.setAdapter(adapterInteresCreate);

        listViewInteresCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.profileNameSubjectEventIdentifier = Communicator.NameProfIntEventCreate.get(position);
                String idprofileOwnInteresCreate =  Communicator.IDeventProfileOwnInteresEventCreate.get(position);
                Communicator.profileSelectionIdentifier = "POIEC";
                ((MainActivity)getActivity()).onProfileOwnInteresEventCreateDetailEvent(idprofileOwnInteresCreate);
            }
        });


        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Profile profile = new Profile();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,profile).commit();
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;
    }

}

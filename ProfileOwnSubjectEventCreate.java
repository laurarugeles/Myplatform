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
public class ProfileOwnSubjectEventCreate extends Fragment {


    public ProfileOwnSubjectEventCreate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_own_subject_event_create, container, false);
        getActivity().setTitle("Grupos de estudio creados");

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<String> profileOwnSubject = activity.getFinishProfileOwnSubjectEventsCreate();

        ListView listviewOwnSubjectCreate = (ListView) rootView.findViewById(R.id.listviewProfileOwnSubjectEventCreate);

        ArrayAdapter<String> adapterlistviewOwnSubjectCreate = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,profileOwnSubject);

        listviewOwnSubjectCreate.setAdapter(adapterlistviewOwnSubjectCreate);

        listviewOwnSubjectCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.profileNameSubjectEventIdentifier =Communicator.NameProfSubEventCreate.get(position);
                String idOwnSubjectCreate = Communicator.IDeventProfileOwnSubjectEventCreate.get(position);
                Communicator.profileSelectionIdentifier = "POSEC";
                ((MainActivity)getActivity()).onProfileOwnSubjectEventCreateDetailEvent(idOwnSubjectCreate);
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

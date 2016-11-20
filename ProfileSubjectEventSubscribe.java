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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSubjectEventSubscribe extends Fragment {


    public ProfileSubjectEventSubscribe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_subject_event_subscribe, container, false);
        getActivity().setTitle("Grupos de estudio inscritos");

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<String> profileSubjectSubscribe = activity.getFinishProfileSubjectEventsSubscribe();

        ListView listViewsubjectSubscribe = (ListView) rootView.findViewById(R.id.listviewProfileSubjectEventSubscribe);

        ArrayAdapter<String> adapterSubjectSubscribe = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,profileSubjectSubscribe);

        listViewsubjectSubscribe.setAdapter(adapterSubjectSubscribe);

        listViewsubjectSubscribe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.profileNameSubjectEventIdentifier = Communicator.NameProfSubEventSubscribe.get(position);
                String idSubjectEventSubscribe = Communicator.IDeventProfileSubjectEventSubscribe.get(position);
                Communicator.profileSelectionIdentifierUnsubscribe = "PSES";
                ((MainActivity)getActivity()).onProfileSubjectEventSubscribeDetailEvent(idSubjectEventSubscribe);
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

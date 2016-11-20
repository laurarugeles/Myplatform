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
public class ProfileInteresEventSubscribe extends Fragment {


    public ProfileInteresEventSubscribe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_interes_event_subscribe, container, false);

        getActivity().setTitle("Eventos de interés inscritos");

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<String> profilesInteresSubs = activity.getFinishProfileInteresEventsSubscribe();

        ListView listViewInteresSubs = (ListView) rootView.findViewById(R.id.listviewProfileInteresEventSubscribe);

        ArrayAdapter<String> adapterInteresSubs = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,profilesInteresSubs);

        listViewInteresSubs.setAdapter(adapterInteresSubs);

        listViewInteresSubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.profileNameSubjectEventIdentifier = Communicator.NameProfIntEventSubscribe.get(position);
                String resultIDInteres =  Communicator.IDeventProfileInteresEventSubscribe.get(position);
                Communicator.profileSelectionIdentifierUnsubscribe = "PIES";
                ((MainActivity)getActivity()).onProfileInteresEventSubscribeDetailEvent(resultIDInteres);
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

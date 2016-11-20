package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telecom.Call;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainListviewEvents extends Fragment {


    public MainListviewEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_listview_events, container, false);

        final MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> subjectEventFromAct = activity.getSubjectEvents();

        final ListView listViewSugg = (ListView) rootView.findViewById(R.id.listviewmateriasevents);

        final ArrayAdapter<String> listviewadaptersugg = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,subjectEventFromAct);

        listViewSugg.setAdapter(listviewadaptersugg);

        getActivity().setTitle("Eventos relacionados");

        Button eventsCreation = (Button) rootView.findViewById(R.id.buttonCrearEvento);

        listViewSugg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.IDeventsDetailSelected = Communicator.IDeventsDetail.get(position);
                Communicator.subjectCatergoriesSuggestionsIdentifier= "MLVE";
                Communicator.subscribeSubjectorEvent = true;
                ((MainActivity)getActivity()).onSubjectSelectedEventsDetail();
            }
        });

        eventsCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsCreate eventsCreate = new EventsCreate();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,eventsCreate).commit();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        MainListview mainListview = new MainListview();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,mainListview).commit();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

}

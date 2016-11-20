package com.example.laura.myplatform;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesDetailEvents extends Fragment {


    public CategoriesDetailEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_categories_detail_events, container, false);

        MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> subjectFromAct = activity.getCategoryDetailEventAct();

        Button createEvent = (Button) rootView.findViewById(R.id.buttonCrearEventoCategories);
        Button buttonAddInteres = (Button) rootView.findViewById(R.id.buttonAddIntereses);
        final ListView listView = (ListView) rootView.findViewById(R.id.listviewcategoriesdetailevents);

        final ArrayAdapter<String> listviewadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,subjectFromAct);

        listView.setAdapter(listviewadapter);

        getActivity().setTitle("Eventos "+Communicator.interesSelectionCreateEvent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.IDeventsDetailSelectedInteres = Communicator.IDeventsCategoriesSuggestion.get(position);
                Communicator.subjectCatergoriesSuggestionsIdentifier = "CDE";
                Communicator.subscribeSubjectorEvent = false;
                Communicator.suggestionInteresorSubject =true;
                ((MainActivity)getActivity()).onInteresSelectedEventsDetail();
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsCreateCategories eventsCreateCategories = new EventsCreateCategories();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,eventsCreateCategories).addToBackStack("catDetEvt").commit();
            }
        });

        buttonAddInteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onAddInteresLikes();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                    CategoriesDetail categoriesDetail = new CategoriesDetail();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,categoriesDetail).commit();
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;

    }

}

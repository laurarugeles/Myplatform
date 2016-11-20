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
public class SuggestionInteres extends Fragment {


    public SuggestionInteres() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_suggestion_interes, container, false);

        MainActivity activity = (MainActivity) getActivity();
        ArrayList<String> suggesstionInteres = activity.getSuggestionInteres();

        ListView listViewSuggInt = (ListView) rootView.findViewById(R.id.listviewSuggestionsInteres);

        ArrayAdapter<String> adapterSuggInt = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,suggesstionInteres);

        listViewSuggInt.setAdapter(adapterSuggInt);

        Communicator.subjectCatergoriesSuggestionsIdentifier = "SuggInt";

        listViewSuggInt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.subscribeSubjectorEvent = false;
                Communicator.suggestionInteresorSubject = false;
                Communicator.IDeventsDetailSelectedInteres = Communicator.IDeventsSuggestionDetailInteres.get(position);
                ((MainActivity)getActivity()).onInteresSelectedEventsDetail();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ((MainActivity)getActivity()).onSuggestionBegin();
                    //Suggestion suggestion = new Suggestion();
                    //getFragmentManager().beginTransaction().replace(R.id.fragment_container,suggestion).commit();
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;
    }

}

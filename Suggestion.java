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
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Suggestion extends Fragment {


    public Suggestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_suggestion, container, false);

        final MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> suggesstionFromAct = activity.getSuggestionsDetail();

        ListView listViewSugg = (ListView) rootView.findViewById(R.id.listviewSuggestions);

        ArrayAdapter<String> listviewadaptersugg = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,suggesstionFromAct);

        listViewSugg.setAdapter(listviewadaptersugg);

        getActivity().setTitle("Sugerencias");

        Button buttonSuggInteres = (Button) rootView.findViewById(R.id.buttonVerSugerenciasInteres);

        listViewSugg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Communicator.itemSelectedSuggestion = Communicator.IDeventsSuggestionDetail.get(position);
                Communicator.subjectCatergoriesSuggestionsIdentifier="SSDE";
                ((MainActivity)getActivity()).onSuggestionSelectedEventsDetail();
            }
        });

        buttonSuggInteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onStartSuggestionInteres();
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

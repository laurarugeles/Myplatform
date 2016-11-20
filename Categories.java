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
public class Categories extends Fragment {


    public Categories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        final MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> categoryFromAct = activity.getCategoryAct();

        ListView listViewcat = (ListView) rootView.findViewById(R.id.listviewcategories);

        ArrayAdapter<String> listviewadaptercat = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,categoryFromAct);

        listViewcat.setAdapter(listviewadaptercat);

        getActivity().setTitle("Intereses");

        listViewcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Subjectpos = (String) parent.getItemAtPosition(position);
                Communicator.categoriesSelectionCreateEvent = Subjectpos;
                ((MainActivity) getActivity()).onCategoriaSelected(Subjectpos);

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

package com.example.laura.myplatform;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.tag;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainListview extends Fragment{

    public MainListview() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View rootView = inflater.inflate(R.layout.fragment_main_listview, container, false);

        final MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> subjectFromAct = activity.getSubjectAct();

        final ListView listView = (ListView) rootView.findViewById(R.id.listviewmaterias);



        final ArrayAdapter<String> listviewadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,subjectFromAct);


        listView.setAdapter(listviewadapter);

        getActivity().setTitle("Materias");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameSubject = (String) parent.getItemAtPosition(position);
                Communicator.NrcMateriasEnviados = Communicator.NrcMaterias.get(position);
                Communicator.subjectSelectionCreateEvent = nameSubject;
                Communicator.IDeventsDetail.clear();
                ((MainActivity)getActivity()).onSubjectSelected();

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

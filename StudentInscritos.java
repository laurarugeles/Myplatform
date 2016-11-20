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
public class StudentInscritos extends Fragment {


    public StudentInscritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_student_inscritos, container, false);

        MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> DetailStudents = activity.getDetailStudentInformation();

        ListView listViewStudents = (ListView) rootView.findViewById(R.id.listviewStudentInscritos);

        ArrayAdapter<String> adapterStudentsInscritos = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,DetailStudents);

        listViewStudents.setAdapter(adapterStudentsInscritos);

        //Communicator.CodeStudentInscritos;

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(Communicator.codeStudentFromSubjectOrInteres == true){
                    Communicator.codeStudentInscritoSelected = Communicator.CodeStudentInscritos.get(position);
                }else{
                    Communicator.codeStudentInscritoSelected = Communicator.CodeStudentInscritosInteres.get(position);
                }
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {

                    switch (Communicator.profileSelectionIdentifierStudentInscritos){
                        case "POSEC":
                            Communicator.profileSelectionIdentifier="POSEC";
                            DetailEventProfile detailEventProfile = new DetailEventProfile();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfile).commit();
                            break;

                        case "POIEC":
                            Communicator.profileSelectionIdentifier = "POIEC";
                            DetailEventProfile detailEventProfile2 = new DetailEventProfile();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfile2).commit();
                            break;
                        case "PSES":
                            Communicator.profileSelectionIdentifierUnsubscribe = "PSES";
                            DetailEventProfileUnsubscribe detailEventProfileUnsubscribe = new DetailEventProfileUnsubscribe();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfileUnsubscribe).commit();
                            break;
                        case "PIES":
                            Communicator.profileSelectionIdentifierUnsubscribe = "PIES";
                            DetailEventProfileUnsubscribe detailEventProfileUnsubscribeInteres = new DetailEventProfileUnsubscribe();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,detailEventProfileUnsubscribeInteres).commit();
                            break;

                        default:
                            break;
                    }
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;
    }

}

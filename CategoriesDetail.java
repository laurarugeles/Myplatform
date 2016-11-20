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
public class CategoriesDetail extends Fragment {


    public CategoriesDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_categories_detail, container, false);

        MainActivity activity = (MainActivity) getActivity();
        final ArrayList<String> categorydetailFromAct = activity.getCategoryDetailAct();

        final ListView listViewCatDetail = (ListView) rootView.findViewById(R.id.listviewcategoriesdetail);
        final ArrayAdapter<String> ArrayCatDetail = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,categorydetailFromAct);

        listViewCatDetail.setAdapter(ArrayCatDetail);

        getActivity().setTitle("Detalle intereses");

        listViewCatDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = (String) parent.getItemAtPosition(position);
                Communicator.NrcInteresEnviados = Communicator.NrcInteres.get(position);
                Communicator.subjectSelectionCreateEvent = categoryName;
                Communicator.interesSelectionCreateEvent =categoryName;
                ((MainActivity)getActivity()).onCategoryDetailSelected();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Categories categories = new Categories();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,categories).commit();
                    return true;
                }
                }
                return false;
            }
        });

        return rootView;
    }
}

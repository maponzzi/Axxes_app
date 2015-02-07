package com.axxesinternational.vias.axxes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axxesinternational.vias.axxes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrabajadoresFragment extends Fragment {


    public TrabajadoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trabajadores, container, false);
    }


}

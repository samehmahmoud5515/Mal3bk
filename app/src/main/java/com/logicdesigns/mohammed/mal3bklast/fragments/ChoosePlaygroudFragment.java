package com.logicdesigns.mohammed.mal3bklast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.HeaderFragment;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.ChoosePlaygroundAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;

import java.util.ArrayList;


public class ChoosePlaygroudFragment extends Fragment {


    public ChoosePlaygroudFragment() {
    }

     RecyclerView choose_playground_rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_choose_playgroud, container, false);

        choose_playground_rv = (RecyclerView) view.findViewById(R.id.choose_playground_rv);
        if (getArguments().getString(FragmentsTags.playgroundJsonResults)!=null) {
            Playground_Search_reservation playgroundSearchReservation = new Playground_Search_reservation(getActivity(), "13", "15");
            ArrayList<Playground_Search_reservation> playgroundsList =
                    playgroundSearchReservation.Parse(getArguments().getString(FragmentsTags.playgroundJsonResults),false);
            ChoosePlaygroundAdapter choosePlaygroundAdapter = new ChoosePlaygroundAdapter(playgroundsList,getActivity());
            LinearLayoutManager mLayoutManager;
            mLayoutManager = new LinearLayoutManager(getActivity());
            choose_playground_rv.setHasFixedSize(true);

            choose_playground_rv.setLayoutManager(mLayoutManager);

            choose_playground_rv.setAdapter(choosePlaygroundAdapter);
        }


        return view;
    }


    @Override
    public void onDestroyView() {
        MainContainer.headerNum=2;
        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.placeholder1, new HeaderFragment())
                .commit();
        super.onDestroyView();

    }

}

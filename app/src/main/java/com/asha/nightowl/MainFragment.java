package com.asha.nightowl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asha.nightowllib.NightOwl;

/**
 * Created by hzqiujiadi on 15/11/5.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewGroup vp = (ViewGroup) view.findViewById(R.id.fragment_container);
        CustomView customView = new CustomView(getActivity());
        vp.addView(customView,ViewGroup.LayoutParams.MATCH_PARENT,160);

        NightOwl.owlRegisterView(customView);
    }
}

package com.asha.nightowl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hzqiujiadi on 15/11/9.
 * hzqiujiadi ashqalcn@gmail.com
 */
public class MainDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_main,container,false);
        return v;
    }

    public static MainDialogFragment newInstance() {
        Bundle args = new Bundle();
        MainDialogFragment fragment = new MainDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

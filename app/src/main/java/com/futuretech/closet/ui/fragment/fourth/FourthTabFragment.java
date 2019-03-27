package com.futuretech.closet.ui.fragment.fourth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;

public class FourthTabFragment extends BaseMainFragment {
    public static FourthTabFragment newInstance() {

        Bundle args = new Bundle();

        FourthTabFragment fragment = new FourthTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fourth, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}

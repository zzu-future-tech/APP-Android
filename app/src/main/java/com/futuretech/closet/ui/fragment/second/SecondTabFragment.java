package com.futuretech.closet.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;

public class SecondTabFragment extends BaseMainFragment {

    Toolbar toolbar;

    public static SecondTabFragment newInstance() {

        Bundle args = new Bundle();

        SecondTabFragment fragment = new SecondTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.outfits);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}

package com.futuretech.closet.ui.fragment.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;

public class ThirdTabFragment extends BaseMainFragment {

    Toolbar toolbar;

    public static ThirdTabFragment newInstance() {

        Bundle args = new Bundle();

        ThirdTabFragment fragment = new ThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_third, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.community);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}

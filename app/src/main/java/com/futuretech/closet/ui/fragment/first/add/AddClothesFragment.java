package com.futuretech.closet.ui.fragment.first.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;

public class AddClothesFragment extends BaseMainFragment {
    private static String clothesClassName;
    Toolbar toolbar;

    public static AddClothesFragment newInstance(String name) {

        Bundle args = new Bundle();
        AddClothesFragment fragment = new AddClothesFragment();
        fragment.setArguments(args);

        clothesClassName = name;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clothes_add, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(clothesClassName);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }
}

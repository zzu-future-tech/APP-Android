package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.ClothesAdapterTwo;
import com.futuretech.closet.base.BaseBackFragment;

import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.model.Clothes;
import com.futuretech.closet.ui.fragment.first.add.AddClothesFragment;

import java.util.ArrayList;
import java.util.List;


public class ClothesFragment extends BaseBackFragment {

    private Toolbar toolbar;
    private GridView gridView;
    private ClothesAdapterTwo adapter;
    private static String className;
    private FloatingActionButton addBtn;


    public static ClothesFragment newInstance(String name) {

        Bundle args = new Bundle();
        ClothesFragment fragment = new ClothesFragment();
        fragment.setArguments(args);

        className = name;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_clothes, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(className);

        gridView = view.findViewById(R.id.grid_clothes);

        //添加工具栏返回箭头
        initToolbarNav(toolbar);

        addBtn = view.findViewById(R.id.add_clothes);

        addBtn.setOnClickListener(v -> {
            //((MainFragment) getParentFragment()).startBrotherFragment(AddClothesFragment.newInstance(className));
            start(AddClothesFragment.newInstance(className));
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        List<Clothes> list=null;
        try {
            DataBase db = new DataBase("clothes",getContext());
            list = db.queryClothesByStyle(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ClothesAdapterTwo(getActivity(), list);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String className = adapter.getItem(position).getName();
                start(ClothesInfoFragment.newInstance(className));
                //Toast.makeText(getActivity(), "点击了"+adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });


    }





}

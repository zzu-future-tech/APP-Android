package com.futuretech.closet.ui.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.GridView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.MatchAdapter;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.model.SuitClass;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchClothesFragment extends BaseBackFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.grid_match)
    GridView gridView;

    private MatchAdapter adapter;
    private static List<SuitClass> list;

    public static MatchClothesFragment newInstance(List<SuitClass> l) {
        Bundle args = new Bundle();
        MatchClothesFragment fragment = new MatchClothesFragment();
        fragment.setArguments(args);

        list = l;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second_match, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar.setTitle("生成套装");
        //添加工具栏返回箭头
        initToolbarNav(toolbar);


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setView();
    }

    private void setView() {
        if (list != null) {
            adapter = new MatchAdapter(getActivity(), list);
        }

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(MatchInfoFragment.newInstance(adapter.getItem(position)));
                //Toast.makeText(getActivity(), "点击了"+adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.futuretech.closet.ui.fragment.first;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;

public class ClothesInfoFragment extends BaseBackFragment {
    private Toolbar toolbar;
    private static String className;


    public static ClothesInfoFragment newInstance(String name) {
        Bundle args = new Bundle();
        ClothesInfoFragment fragment = new ClothesInfoFragment();
        fragment.setArguments(args);
        className=name;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_clothes_info, container, false);
        initView(view);

        ImageView iv=(ImageView)view.findViewById(R.id.imageView);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.mea));

        TextView t1=(TextView)view.findViewById(R.id.text1);
        t1.setText("颜色:"+className+"'s color");

        TextView t2=(TextView)view.findViewById(R.id.text2);
        t2.setText("场合:"+className+"'s occation");



        return view;
    }

    private void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(className);
        toolbar.inflateMenu(R.menu.clothes_info_menu);


        //添加工具栏返回箭头
        initToolbarNav(toolbar);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.item1:
                        Toast.makeText(getActivity(), "你点击了删除", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2:
                        Toast.makeText(getActivity(), "你点击了修改", Toast.LENGTH_SHORT).show();
                        return true;
                }return false;
            }
        });
    }




}

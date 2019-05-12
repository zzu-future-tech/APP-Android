package com.futuretech.closet.ui.fragment.central;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.ClothesAdapterOne;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.model.ClothesClass;
import com.futuretech.closet.ui.fragment.MainFragment;
import com.futuretech.closet.ui.fragment.first.ClothesFragment;


import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

public class FirstTabFragment_Bottom extends BaseBackFragment {

    private Toolbar toolbar;
    private ListView listView;
    private ClothesAdapterOne adapter;

    //创建fragment使用的方法，可传参
    public static FirstTabFragment_Bottom newInstance() {
        Bundle args = new Bundle();
        FirstTabFragment_Bottom fragment = new FirstTabFragment_Bottom();
        fragment.setArguments(args);
        return fragment;
    }

    //重写onCreateView方法，绑定对应的layout
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定layout
        View view = inflater.inflate(R.layout.fragment_tab_first, container, false);
        initView(view);
        return view;
    }

    //其他需要初始化的view相关也放在onCreateView里
    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("选择下衣");
        //添加工具栏返回箭头
        initToolbarNav(toolbar);
        listView = view.findViewById(R.id.list);
    }

    //fragment的懒加载，加载数量大又慢的丢进去
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //建立衣服类型的名称和图片对应的数组
        String[] names={"短裤","休闲裤","裙子","牛仔裤","西裤"};
        int[] resImages={R.drawable.clothes_duanku,R.drawable.clothes_changku,R.drawable.clothes_skirt,R.drawable.clothes_niuzaiku,R.drawable.clothes_xiku};

        final ArrayList<ClothesClass> words = new ArrayList<>();
        for(int i=0;i<5;i++) {
            words.add(new ClothesClass(names[i], resImages[i]));
        }


        //初始化adapter
        adapter = new ClothesAdapterOne(getActivity(), words);

        //listview加载adapter
        listView.setAdapter(adapter);

        //设置listview的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String className = adapter.getItem(position).getName();
                //启动兄弟fragment
                //((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance(className));
                start(ClothesFragment_Bottom.newInstance(className));
            }
        });

    }
}

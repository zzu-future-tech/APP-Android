package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.ClothesAdapterOne;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.model.ClothesOne;
import com.futuretech.closet.ui.fragment.MainFragment;

import java.util.ArrayList;

//继承了自己写的BaseMainFragment，实现按两次返回退出app功能
//其他非主页fragment继承SupportFragment
public class FirstTabFragment extends BaseMainFragment {

    private Toolbar toolbar;
    private ListView listView;
    private ClothesAdapterOne adapter;

    //创建fragment使用的方法，可传参
    public static FirstTabFragment newInstance() {
        Bundle args = new Bundle();
        FirstTabFragment fragment = new FirstTabFragment();
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
        toolbar.setTitle(R.string.clothes);
        listView = view.findViewById(R.id.list);
    }

    //fragment的懒加载，加载数量大又慢的丢进去
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final ArrayList<ClothesOne> words = new ArrayList<>();
        words.add(new ClothesOne("RUA!0"));
        words.add(new ClothesOne("RUA!1"));
        words.add(new ClothesOne("RUA!2"));
        words.add(new ClothesOne("RUA!3"));
        words.add(new ClothesOne("RUA!4"));
        words.add(new ClothesOne("RUA!5"));
        words.add(new ClothesOne("RUA!6"));
        words.add(new ClothesOne("RUA!7"));
        words.add(new ClothesOne("RUA!8"));
        words.add(new ClothesOne("RUA!9"));

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
                ((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance(className));
            }
        });

    }
}

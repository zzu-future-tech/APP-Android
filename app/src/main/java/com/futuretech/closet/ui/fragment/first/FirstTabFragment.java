package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.PagerFragmentAdapter;
import com.futuretech.closet.base.BaseMainFragment;


public class FirstTabFragment extends BaseMainFragment {
    private TabLayout mTab;

    private ViewPager mViewPager;

    public static FirstTabFragment newInstance() {

        Bundle args = new Bundle();

        FirstTabFragment fragment = new FirstTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_first, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);


        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);



        mViewPager.setAdapter(new PagerFragmentAdapter(getChildFragmentManager()
                , getString(R.string.all), getString(R.string.more)));
        mTab.setupWithViewPager(mViewPager);
    }
}

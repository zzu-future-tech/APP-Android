package com.futuretech.closet.ui.fragment.fourth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.ui.fragment.fourth.child.AvatarFragment;
import com.futuretech.closet.ui.fragment.fourth.child.MeFragment;

public class FourthTabFragment extends BaseMainFragment {

    private Toolbar mToolbar;
    private View mView;

    public static FourthTabFragment newInstance() {

        Bundle args = new Bundle();

        FourthTabFragment fragment = new FourthTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_fourth, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View view) {
    }



    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(AvatarFragment.class) == null) {
            loadFragment();
        }

        mToolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.me);
    }

    private void loadFragment() {
        loadRootFragment(R.id.fl_fourth_container_upper, AvatarFragment.newInstance());
        loadRootFragment(R.id.fl_fourth_container_lower, MeFragment.newInstance());
    }


}

package com.futuretech.closet.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.futuretech.closet.R;
import com.futuretech.closet.ui.fragment.central.CentralTabFragment;
import com.futuretech.closet.ui.fragment.first.FirstTabFragment;
import com.futuretech.closet.ui.fragment.fourth.FourthTabFragment;
import com.futuretech.closet.ui.fragment.second.SecondTabFragment;
import com.futuretech.closet.ui.fragment.third.ThirdTabFragment;
import com.futuretech.closet.ui.view.BottomBar;
import com.futuretech.closet.ui.view.BottomBarTab;


import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends SupportFragment{

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int CENTRAL = 2;
    public static final int THIRD = 3;
    public static final int FOURTH = 4;


    private SupportFragment[] mFragments = new SupportFragment[5];

    private BottomBar mBottomBar;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(FirstTabFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FirstTabFragment.newInstance();
            mFragments[SECOND] = SecondTabFragment.newInstance();
            mFragments[CENTRAL] = CentralTabFragment.newInstance();
            mFragments[THIRD] = ThirdTabFragment.newInstance();
            mFragments[FOURTH] = FourthTabFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[CENTRAL],
                    mFragments[THIRD],
                    mFragments[FOURTH]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(SecondTabFragment.class);
            mFragments[CENTRAL] = findChildFragment(CentralTabFragment.class);
            mFragments[THIRD] = findChildFragment(ThirdTabFragment.class);
            mFragments[FOURTH] = findChildFragment(FourthTabFragment.class);
        }
    }

    private void initView(View view) {
        mBottomBar = view.findViewById(R.id.bottomBar);

        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_tshirt_white, getString(R.string.clothes)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_human_white, getString(R.string.outfits)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_wardrobe, getString(R.string.suit)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, getString(R.string.community)))
                .addItem(new BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, getString(R.string.me)));


        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                //Log.d(TAG, "onTabSelected: "+position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);

    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
}
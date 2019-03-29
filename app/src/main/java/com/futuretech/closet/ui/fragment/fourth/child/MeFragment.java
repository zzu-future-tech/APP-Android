package com.futuretech.closet.ui.fragment.fourth.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.yokeyword.fragmentation.SupportFragment;
import com.futuretech.closet.R;


/**
 * Created by YoKeyword on 16/6/6.
 */
public class MeFragment extends SupportFragment {
    private TextView mTvBtnSettings;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_fourth_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvBtnSettings = view.findViewById(R.id.tv_btn_settings);
        mTvBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SettingsFragment.newInstance());

            }
        });
    }


}
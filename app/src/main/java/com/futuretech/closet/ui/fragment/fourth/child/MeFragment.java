package com.futuretech.closet.ui.fragment.fourth.child;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuretech.closet.MainActivity;
import com.futuretech.closet.R;

import me.yokeyword.fragmentation.SupportFragment;

import static android.support.constraint.Constraints.TAG;


public class MeFragment extends SupportFragment {
    private TextView mTvBtnSettings;
    private TextView mTvBtnLogout;
    private SharedPreferences sharedPreferences;

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

        mTvBtnLogout = view.findViewById(R.id.tv_btn_logout);
        mTvBtnLogout.setOnClickListener(v -> {
            //注销登录
            Log.d(TAG, "注销成功！");
            sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("LoginBool", false);
            editor.apply();
            Intent outIntent = new Intent(getActivity(),
                    MainActivity.class);
            outIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(outIntent);
        });
    }


}

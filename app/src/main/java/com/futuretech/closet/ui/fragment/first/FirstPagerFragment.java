package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;
import com.futuretech.closet.R;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class FirstPagerFragment extends SupportFragment {
    public static FirstPagerFragment newInstance() {

        Bundle args = new Bundle();
        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_first_pager_first, container, false);
        return view;
    }

}

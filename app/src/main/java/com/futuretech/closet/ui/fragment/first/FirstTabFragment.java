package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.ui.fragment.MainFragment;


public class FirstTabFragment extends BaseMainFragment {

    Toolbar toolbar;

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

        ImageView imageView = view.findViewById(R.id.mea);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance());
                //start(ClothesFragment.newInstance());

            }
        });

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.clothes);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}

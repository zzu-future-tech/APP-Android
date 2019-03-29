package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.ClothesAdapterOne;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.model.ClothesOne;
import com.futuretech.closet.ui.fragment.MainFragment;

import java.util.ArrayList;


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

//        ImageView imageView = view.findViewById(R.id.mea);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance());
//                //start(ClothesFragment.newInstance());
//
//            }
//        });

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.clothes);

        final ArrayList<ClothesOne> words = new ArrayList<ClothesOne>();
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


        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        ClothesAdapterOne adapter = new ClothesAdapterOne(getActivity(), words);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(adapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}

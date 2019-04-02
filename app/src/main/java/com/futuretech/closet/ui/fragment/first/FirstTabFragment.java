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


public class FirstTabFragment extends BaseMainFragment {

    private Toolbar toolbar;
    private ListView listView;
    private ClothesAdapterOne adapter;


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

        listView = view.findViewById(R.id.list);

    }

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


        adapter = new ClothesAdapterOne(getActivity(), words);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String className = adapter.getItem(position).getName();
                ((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance(className));
            }
        });

    }
}

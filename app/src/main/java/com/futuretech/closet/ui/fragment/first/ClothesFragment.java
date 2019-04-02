package com.futuretech.closet.ui.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.ClothesAdapterOne;
import com.futuretech.closet.adapter.ClothesAdapterTwo;
import com.futuretech.closet.model.ClothesOne;
import com.futuretech.closet.model.ClothesTwo;
import com.futuretech.closet.ui.fragment.MainFragment;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;



public class ClothesFragment extends SupportFragment {

    private Toolbar toolbar;
    private GridView gridView;
    private ClothesAdapterTwo adapter;
    private static String className;

    public static ClothesFragment newInstance(String name) {

        Bundle args = new Bundle();
        ClothesFragment fragment = new ClothesFragment();
        fragment.setArguments(args);

        className = name;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_clothes, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        TextView textView = view.findViewById(R.id.text);
//        textView.setText(
//                "getTopFragment:"+getTopFragment()+
//                "\ngetPreFragment:"+getPreFragment());

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(className);

        gridView = view.findViewById(R.id.grid_clothes);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        final ArrayList<ClothesTwo> words = new ArrayList<>();
//        words.add(new ClothesTwo("RUA!0"));
//        words.add(new ClothesTwo("RUA!1"));
//        words.add(new ClothesTwo("RUA!2"));
//        words.add(new ClothesTwo("RUA!3"));
//        words.add(new ClothesTwo("RUA!4"));
//        words.add(new ClothesTwo("RUA!5"));
//        words.add(new ClothesTwo("RUA!6"));
//        words.add(new ClothesTwo("RUA!7"));
//        words.add(new ClothesTwo("RUA!8"));
//        words.add(new ClothesTwo("RUA!9"));

        for(int i=0;i<20;i++){
            words.add(new ClothesTwo("RUA!"+i));
        }


        adapter = new ClothesAdapterTwo(getActivity(), words);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((MainFragment) getParentFragment()).startBrotherFragment(ClothesFragment.newInstance());
                Toast.makeText(getActivity(), "点击了"+adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}

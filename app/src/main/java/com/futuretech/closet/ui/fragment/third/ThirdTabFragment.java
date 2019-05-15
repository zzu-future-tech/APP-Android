package com.futuretech.closet.ui.fragment.third;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.FriendAdapter;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.model.Friend;
import com.futuretech.closet.ui.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThirdTabFragment extends BaseMainFragment {

    Toolbar toolbar;
    private FloatingActionButton fab;

    private Friend[] friends={new Friend(R.drawable.pic2),new Friend(R.drawable.pic7),new Friend(R.drawable.pic7)};
    private List<Friend> friendList=new ArrayList<>();
    private FriendAdapter adapter;
    private RecyclerView recyclerView;

    public static ThirdTabFragment newInstance() {

        Bundle args = new Bundle();

        ThirdTabFragment fragment = new ThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_third, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        toolbar = view.findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.community);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        initFriends();
        fab = view.findViewById(R.id.add);
        fab.setOnClickListener(v -> {
            ((MainFragment) getParentFragment()).startBrotherFragment(FriendAddFragment.newInstance());
        });

        AppBarLayout appBarLayout = view.findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.textColorSecondary));
                ImageView imageView = view.findViewById(R.id.image1);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // 折叠状态
                    collapsingToolbar.setTitle("社区");
                    imageView.setVisibility(View.GONE);
                } else { // 非折叠状态
                    collapsingToolbar.setTitle("");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);

    }

    private void initFriends() {
        friendList.clear();
        for (int i = 0; i < 40; i++) {
            Random random = new Random();
            int index = random.nextInt(friends.length);
            friendList.add(friends[index]);
        }
    }
}

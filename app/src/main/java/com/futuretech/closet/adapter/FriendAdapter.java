package com.futuretech.closet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.futuretech.closet.R;
import com.futuretech.closet.model.Friend;

import java.util.List;
import java.util.Random;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{

    private Context mContext;
    private List<Friend> mFriendList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image1;
        ImageView image2;
        ImageView image3;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            image1 = (ImageView) view.findViewById(R.id.friend_image);
            image2 = (ImageView) view.findViewById(R.id.friend_imag);
            image3 = (ImageView) view.findViewById(R.id.friend_ima);
        }
    }
    public FriendAdapter(List<Friend> friendList) {
        mFriendList = friendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_friends_adapter,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        getRandomLength();
        holder.image2.setVisibility(View.GONE);
        holder.image3.setVisibility(View.GONE);
        if (getRandomLength() == 1) {
            holder.image1.setVisibility(View.VISIBLE);
        } else if (getRandomLength() == 2) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
        } else if (getRandomLength() == 3) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image3.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
    private int getRandomLength() {
        Random random = new Random();
        int length = random.nextInt(3) + 1;
        return length;
    }
}

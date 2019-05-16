package com.futuretech.closet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
        ImageView avatar;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            avatar = view.findViewById(R.id.avatar);
            image1 = view.findViewById(R.id.friend_image);
            image2 = view.findViewById(R.id.friend_imag);
            image3 = view.findViewById(R.id.friend_ima);
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

        Glide
                .with(mContext)
                .load(R.drawable.pic7)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.avatar);

        getRandomLength();
        holder.image2.setVisibility(View.GONE);
        holder.image3.setVisibility(View.GONE);
        if (getRandomLength() == 1) {
            holder.image1.setVisibility(View.VISIBLE);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic2)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image1);
        } else if (getRandomLength() == 2) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic2)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image1);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic8)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image2);
        } else if (getRandomLength() == 3) {
            holder.image1.setVisibility(View.VISIBLE);
            holder.image2.setVisibility(View.VISIBLE);
            holder.image3.setVisibility(View.VISIBLE);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic2)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image1);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic8)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image2);
            Glide
                    .with(mContext)
                    .load(R.drawable.pic9)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.image3);
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

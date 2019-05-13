package com.futuretech.closet.ui.fragment.second;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.db.SuitsInformationPack;
import com.futuretech.closet.model.SuitClass;
import com.futuretech.closet.utils.PhotoUtils;
import com.futuretech.closet.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchInfoFragment extends BaseBackFragment {
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView1)
    ImageView iv1;
    @BindView(R.id.imageView2)
    ImageView iv2;

    private static SuitClass suit;

    public static MatchInfoFragment newInstance(SuitClass s) {
        Bundle args = new Bundle();
        MatchInfoFragment fragment = new MatchInfoFragment();
        fragment.setArguments(args);
        suit = s;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second_info, container, false);
        ButterKnife.bind(this,view);
        initView(view);

        return view;
    }

    private void initView(View view) {
        toolbar.setTitle("套装信息");
        //添加工具栏返回箭头
        initToolbarNav(toolbar);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        add.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("添加")
                    //setMessage是用来显示字符串的
                    .setMessage("确认添加进我的搭配？")
                    .setPositiveButton("确定", (dialog1, which) -> addSuit())
                    .setNegativeButton("取消", null)
                    .create();
            dialog.show();
        });

        setPic();
    }


    private void addSuit(){
        DataBase db = null;
        //userid
        SharedPreferences share = getActivity().getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        String userid = share.getString("Email",null);

        SuitsInformationPack suitPack = new SuitsInformationPack(suit.getTopId(),suit.getBottomId(),userid);
        try {
            db = new DataBase("clothes",getContext());
            db.insertSuit(suitPack.getValues());
            db.close();
            ToastUtils.showShort(getContext(),"添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getContext(),"添加失败！");
        }
    }



    private void setPic(){

        Uri uriTop = PhotoUtils.getPhotoUri(suit.getTopId());
        if(uriTop!=null){
            //iv1.setImageURI(uriTop);
            Glide
                    .with(getContext())
                    .load(uriTop)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv1);
        }
        Uri uriBottom = PhotoUtils.getPhotoUri(suit.getBottomId());
        if(uriBottom!=null){
            //iv2.setImageURI(uriBottom);
            Glide
                    .with(getContext())
                    .load(uriBottom)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv2);
        }

    }

}

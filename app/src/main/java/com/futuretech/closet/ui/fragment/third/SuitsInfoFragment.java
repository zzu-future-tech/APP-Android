package com.futuretech.closet.ui.fragment.third;

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

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.model.SuitClass;
import com.futuretech.closet.utils.PhotoUtils;
import com.futuretech.closet.utils.ToastUtils;

public class SuitsInfoFragment extends BaseBackFragment {
    private CardView cardView;
    private Toolbar toolbar;
    private View view;

    private Handler handler;

    private Button delete;

    private static SuitClass suit;


    public static SuitsInfoFragment newInstance(SuitClass s) {
        Bundle args = new Bundle();
        SuitsInfoFragment fragment = new SuitsInfoFragment();
        fragment.setArguments(args);
        suit = s;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_third_suits_info, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("套装信息");
        //toolbar.inflateMenu(R.menu.clothes_info_menu);

        //添加工具栏返回箭头
        initToolbarNav(toolbar);

        cardView = view.findViewById(R.id.cardView);
        delete = view.findViewById(R.id.delete);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        delete.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("删除")
                    //setMessage是用来显示字符串的
                    .setMessage("确认删除此套装？")
                    .setPositiveButton("确定", (dialog1, which) -> deleteClothes())
                    .setNegativeButton("取消", null)
                    .create();
            dialog.show();
        });

        setView();
    }


    private void deleteClothes(){
        DataBase db = null;
        try {
            db = new DataBase("clothes",getContext());
            db.deleteSuitsByid(suit.getId());
            db.close();
            ToastUtils.showShort(getContext(),"删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(getContext(),"删除失败！");
        }
        pop();
    }



    private void setView(){

        ImageView iv1= view.findViewById(R.id.imageView1);
        ImageView iv2= view.findViewById(R.id.imageView2);

        Uri uriTop = PhotoUtils.getPhotoUri(suit.getTopId());
        if(uriTop!=null){
            iv1.setImageURI(uriTop);
        }
        Uri uriBottom = PhotoUtils.getPhotoUri(suit.getBottomId());
        if(uriBottom!=null){
            iv2.setImageURI(uriBottom);
        }

}

    @Override
    public void onSupportVisible(){
        super.onSupportVisible();
        setView();
    }

}

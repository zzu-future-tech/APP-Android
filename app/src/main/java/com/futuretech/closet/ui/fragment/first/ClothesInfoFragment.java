package com.futuretech.closet.ui.fragment.first;

import android.support.v7.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.futuretech.closet.ui.fragment.first.update.UpdateClothesFragment;
import com.futuretech.closet.utils.JsonUtils;
import com.futuretech.closet.utils.PhotoUtils;
import com.futuretech.closet.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class ClothesInfoFragment extends BaseBackFragment {
    private CardView cardView;
    private Toolbar toolbar;
    private static String className;
    private static int dressid;
    private View view;

    private Handler handler;

    private Button edit;
    private Button delete;


    public static ClothesInfoFragment newInstance(String name,int id) {
        Bundle args = new Bundle();
        ClothesInfoFragment fragment = new ClothesInfoFragment();
        fragment.setArguments(args);
        className = name;
        dressid = id;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_clothes_info, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("衣物信息");
        //toolbar.inflateMenu(R.menu.clothes_info_menu);

        //添加工具栏返回箭头
        initToolbarNav(toolbar);

        cardView = view.findViewById(R.id.cardView);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch(menuItem.getItemId()) {
//                    case R.id.delete:
//                        //Toast.makeText(getActivity(), "你点击了删除", Toast.LENGTH_SHORT).show();
//                        deleteClothes();
//                        return true;
//                    case R.id.edit:
//                        //Toast.makeText(getActivity(), "你点击了修改", Toast.LENGTH_SHORT).show();
//                        start(UpdateClothesFragment.newInstance(dressid));
//                        return true;
//                }return false;
//            }
//        });

        edit.setOnClickListener(v -> {
            start(UpdateClothesFragment.newInstance(dressid));
        });

        delete.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("删除")
                    //setMessage是用来显示字符串的
                    .setMessage("确认删除此衣物？")
                    .setPositiveButton("确定", (dialog1, which) -> deleteClothes())
                    .setNegativeButton("取消", null)
                    .create();
            dialog.show();
        });

        setView();
    }


    private void deleteClothes(){
        //发送json
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("dressid",dressid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJson(jsonObject.toString());

        //接受异步线程的消息
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String resp = (String) msg.obj;
                        //删本地
                        try {
                            DataBase db = new DataBase("clothes",getContext());
                            db.deleteClothesByDressid(dressid);
                            db.close();
                            //删除本地图片
                            PhotoUtils.deletePhoto(dressid);

                            //删远程
                            if(JsonUtils.getStatusCode(resp).equals("1")){
                                ToastUtils.showShort(getContext(),"删除成功！");
                                pop();
                            }else{
                                ToastUtils.showShort(getContext(),"删除失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(getContext(),"删除失败！");
                        }

                        break;
                    default:
                        break;
                }
            }
        };

    }

    private void postJson(String json) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://39.105.83.165/service/clothes/deleteClothesByDressid.action")
                .post(body)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "deleteClothes 网络请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "deleteClothes 网络请求返回码:" + response.code());
                //Log.d(TAG, "AddClothes 网络请求返回:" + resp);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = resp;
                handler.sendMessage(message);
            }
        });
    }


    private void setView(){
//        Clothes clothes=null;
//        try {
//            DataBase db = new DataBase("clothes",getContext());
//            clothes= db.queryByDressid(dressid);
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ImageView iv= view.findViewById(R.id.imageView);
        Uri uri = PhotoUtils.getPhotoUri(dressid);
        if(uri!=null){
            //iv.setImageURI(uri);
            Glide
                    .with(getContext())
                    .load(uri)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv);
        }
    }

    @Override
    public void onSupportVisible(){
        super.onSupportVisible();
        setView();
    }

}

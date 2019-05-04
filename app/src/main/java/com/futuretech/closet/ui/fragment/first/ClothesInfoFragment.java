package com.futuretech.closet.ui.fragment.first;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.utils.JsonUtils;
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
    private Toolbar toolbar;
    private static String className;
    private static int dressid;

    private Handler handler;


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
        View view = inflater.inflate(R.layout.fragment_tab_clothes_info, container, false);
        initView(view);

        ImageView iv= view.findViewById(R.id.imageView);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.mea));

        TextView t1= view.findViewById(R.id.text1);
        t1.setText("颜色:"+className+"'s color");

        TextView t2= view.findViewById(R.id.text2);
        t2.setText("场合:"+className+"'s occation");

        TextView t3= view.findViewById(R.id.text3);
        t2.setText("ID:"+ dressid);



        return view;
    }

    private void initView(View view) {

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(className);
        toolbar.inflateMenu(R.menu.clothes_info_menu);


        //添加工具栏返回箭头
        initToolbarNav(toolbar);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.delete:
                        //Toast.makeText(getActivity(), "你点击了删除", Toast.LENGTH_SHORT).show();
                        deleteClothes();
                        return true;
                    case R.id.edit:
                        Toast.makeText(getActivity(), "你点击了修改", Toast.LENGTH_SHORT).show();
                        return true;
                }return false;
            }
        });
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

    public void postJson(String json) {
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
}

package com.futuretech.closet.ui.fragment.third;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.futuretech.closet.R;
import com.futuretech.closet.adapter.SuitAdapter;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.db.SuitsInformationPack;
import com.futuretech.closet.event.MessageEvent;
import com.futuretech.closet.model.SuitClass;
import com.futuretech.closet.ui.fragment.MainFragment;
import com.futuretech.closet.utils.JsonUtils;
import com.futuretech.closet.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class ThirdTabFragment extends BaseMainFragment {

    private List<Integer> list = new ArrayList<>();
    private SuitAdapter adapter;

    @BindView(R.id.grid_suits)
    GridView gridView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_suits)
    FloatingActionButton addBtn;

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
        ButterKnife.bind(this,view);
        initView(view);
        setView();
        return view;
    }

    private void initView(View view) {
        toolbar.setTitle(R.string.suit);

        addBtn.setOnClickListener(v -> {
            ((MainFragment) getParentFragment()).startBrotherFragment(FirstTabFragment_Top.newInstance());
        });


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this); // 将该对象注册到 EventBus
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this); // 解除注册关系
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent event) { // 监听 MessageEvent 事件
        int dressid = event.dressid;
        Log.v(TAG, "GET event: "+dressid);
        list.add(dressid);
    }

    public static void addSuit(int dressid1, int dressid2, Activity activity,Context context){

        //userid
        SharedPreferences share = activity.getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        String userid = share.getString("Email",null);

        try {
            DataBase db = new DataBase("clothes",context);
            SuitsInformationPack suit = new SuitsInformationPack(dressid1,dressid2,userid);
            boolean isNotDuplicate = db.insertSuit(suit.getValues());
            if(!isNotDuplicate){
                ToastUtils.showShort(context,"套装已存在！");
            }else{
                ToastUtils.showShort(context,"添加成功");
                //发送至服务器
                sentSuit(Integer.toString(dressid1),Integer.toString(dressid2));
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(context,"添加失败");
        }
    }

    private void setView(){
        List<SuitClass> list=null;
        try {
            DataBase db = new DataBase("clothes",getContext());
            list = db.queryAllSuits();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list != null) {
            adapter = new SuitAdapter(getActivity(), list);
        }

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int dressid = adapter.getItem(position).getDressid();
                ((MainFragment) getParentFragment()).startBrotherFragment(SuitsInfoFragment.newInstance(adapter.getItem(position)));
                //Toast.makeText(getActivity(), "点击了"+adapter.getItem(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(list!=null){
            if(list.size()==2){
                addSuit(list.get(0),list.get(1),getActivity(),getContext());
                list.clear();
            }
        }
        setView();
    }

    private static void sentSuit(String dressid1,String dressid2){
        JSONObject json = JsonUtils.sendSuitJson(dressid1,dressid2);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url("http://39.105.83.165/service/suit/updateWeightAboutType.action")
                .post(body)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "sentSuit 网络请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "sentSuit 网络请求返回码:" + response.code());
                //Log.d(TAG, "AddClothes 网络请求返回:" + resp);
            }
        });
    }

}

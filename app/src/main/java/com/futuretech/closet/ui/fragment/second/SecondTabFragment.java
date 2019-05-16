package com.futuretech.closet.ui.fragment.second;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseMainFragment;
import com.futuretech.closet.model.SuitClass;
import com.futuretech.closet.ui.fragment.MainFragment;
import com.futuretech.closet.utils.JsonUtils;
import com.futuretech.closet.utils.ToastUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
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

public class SecondTabFragment extends BaseMainFragment {
    @BindView(R.id.selectCityBtn)
    Button selectCityBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.resultV)
    TextView resultV;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.genSuitBtn)
    Button genSuitBtn;

    private String city;
    private String attr;
    private String past;
    private Handler handler;
    private ProgressDialog progressDialog;
    private String userid;
    private boolean isCitySelected = false;

    public static SecondTabFragment newInstance() {

        Bundle args = new Bundle();

        SecondTabFragment fragment = new SecondTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_second, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar.setTitle(R.string.outfits);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.attribute, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //ToastUtils.showShort(getContext(),parent.getItemAtPosition(position).toString());
                    attr = "休闲";
                }else{
                    attr = parent.getItemAtPosition(position).toString();
                }
                //ToastUtils.showShort(getContext(),attr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.past, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //ToastUtils.showShort(getContext(),parent.getItemAtPosition(position).toString());
                    past = "0";
                }else{
                    past = parent.getItemAtPosition(position).toString();
                }
                //ToastUtils.showShort(getContext(),past);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectCityBtn.setOnClickListener(v -> {
            CityPicker.from(this)
                    .enableAnimation(true)
                    .setLocatedCity(null)
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onPick(int position, City data) {
                            //currentTV.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
                            city = data.getName();
                            resultV.setText("目标城市："+city);
                            isCitySelected = true;
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onLocate() {
                            //开始定位，这里模拟一下定位
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    CityPicker.from(SecondTabFragment.this).locateComplete(new LocatedCity("郑州", "河南", "101180101"), LocateState.SUCCESS);
                                }
                            }, 2000);
                        }
                    })
                    .show();
        });

        genSuitBtn.setOnClickListener(v -> {
            if(!isCitySelected){
                resultV.setText("请选择目标城市！！！");
                return;
            }
            JSONObject json = JsonUtils.reqMatchJson(userid,city,attr,past);
            postJson(json.toString());
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            String resp = (String) msg.obj;
                            Log.d(TAG, "搭配返回: "+resp);
                            //接收response处理
                            progressDialog.dismiss();
                            List<SuitClass> list = JsonUtils.matchJsonResolve(resp);
                            ((MainFragment) getParentFragment()).startBrotherFragment(MatchClothesFragment.newInstance(list));
                            break;
                        default:
                            break;
                    }
                }
            };
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        //userid
        SharedPreferences share = getActivity().getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        userid = share.getString("Email",null);
    }

    private void postJson(String json) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在生成......");
        progressDialog.show();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://39.105.83.165/service/suit/getSortsByLocation.action")
                .post(body)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "reqMatch 网络请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "reqMatch 网络请求返回码:" + response.code());
                //Log.d(TAG, "AddClothes 网络请求返回:" + resp);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = resp;
                handler.sendMessage(message);
            }
        });
    }
}

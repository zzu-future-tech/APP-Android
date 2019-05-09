package com.futuretech.closet.ui.fragment.first.update;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.db.ClothesInformationPack;
import com.futuretech.closet.db.DataBase;
import com.futuretech.closet.model.Clothes;
import com.futuretech.closet.ui.fragment.first.add.AddClothesFragment;
import com.futuretech.closet.utils.JsonUtils;
import com.futuretech.closet.utils.PhotoUtils;
import com.futuretech.closet.utils.ToastUtils;
import com.lzt.flowviews.view.FlowView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateClothesFragment extends BaseBackFragment {
    private static final String TAG = "PhotoImageFragment";
    //衣物信息
    private static int dressid;
    private String style;
    private int color;
    @BindView(R.id.thickness)
    SeekBar thickness;

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.takePic)
    Button takePic;
    @BindView(R.id.gallery)
    Button gallery;
    @BindView(R.id.color)
    CardView colorDisplay;
    @BindView(R.id.save)
    Button saveBtn;

    Unbinder unbinder;
    private Toolbar toolbar;


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory() + "/Pictures/closet/", "photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory() + "/Pictures/closet/", "crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 800;
    private static final int OUTPUT_Y = 800;


    private static String[] attribute = new String[]{"全选", "工作", "休闲", "运动", "其他"};
    private FlowView fv_attribute;

    private Handler handler;

    public static UpdateClothesFragment newInstance(int id) {

        Bundle args = new Bundle();
        UpdateClothesFragment fragment = new UpdateClothesFragment();
        fragment.setArguments(args);

        dressid = id;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clothes_add, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        setView(view);
        initFlowView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("更新" + dressid);
        //添加工具栏返回箭头
        initToolbarNav(toolbar);

        //获取图片颜色
        photo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int pixel;
                Bitmap bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
//                int x = (int)event.getX();
//                int y = (int)event.getY();
                Matrix inverse = new Matrix();
                photo.getImageMatrix().invert(inverse);
                float[] touchPoint = new float[]{event.getX(), event.getY()};
                inverse.mapPoints(touchPoint);
                int x = Integer.valueOf((int) touchPoint[0]);
                int y = Integer.valueOf((int) touchPoint[1]);
                //Log.d(TAG, "x:"+x);
                //Log.d(TAG, "y:"+y);
                //Log.d(TAG, "photoWidth: "+bitmap.getWidth());
                //Log.d(TAG, "photoHeight: "+bitmap.getHeight());
                if (x < 0 || x > bitmap.getWidth() || y < 0 || y > bitmap.getHeight()) {
                    pixel = Color.WHITE;
                } else {
                    pixel = bitmap.getPixel(x, y);
                }
                //then do what you want with the pixel data, e.g
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                color = Color.rgb(redValue, greenValue, blueValue);
                Log.d(TAG, "selectColor:" + color);
                colorDisplay.setCardBackgroundColor(color);
                return false;
            }
        });

        //保存按钮
        saveBtn.setOnClickListener(v -> {
            saveClothes();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.takePic, R.id.gallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takePic:
                autoObtainCameraPermission();
                break;
            case R.id.gallery:
                autoObtainStoragePermission();
                break;
            default:
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (!fileUri.getParentFile().exists())
            fileUri.getParentFile().mkdirs();
    }

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    ToastUtils.showShort(getActivity(), "您已经拒绝过一次");
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (hasSdcard()) {
                    //通过FileProvider创建一个content类型的Uri

                    imageUri = FileProvider.getUriForFile(getActivity(), "com.futuretech.provider", fileUri);
                    PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider.getUriForFile(getActivity(), "com.futuretech.provider", fileUri);
                        }
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.showShort(getActivity(), "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + "  resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult: resultCode!=RESULT_OK");
            return;
        }
        switch (requestCode) {
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                break;
            //相册返回
            case CODE_GALLERY_REQUEST:

                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(getActivity(), "com.futuretech.provider", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    ToastUtils.showShort(getActivity(), "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());
                if (bitmap != null) {
                    showImages(bitmap);
                    //删除旧的原图
                    PhotoUtils.deleteOriginPhoto();
                }
                break;
            default:
        }
    }

    private void showImages(Bitmap bitmap) {
        photo.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    //标签初始化
    public void initFlowView(View view) {
        fv_attribute = view.findViewById(R.id.fv_style0);

        List list = new ArrayList();
        fv_attribute.setAttr(R.color.color4dBlack, R.drawable.shape_rectangle_corner4_gray_solid)
                .setSelectedAttr(R.color.colorWhite, R.drawable.shape_rectangle_corner4_green_solid)
                .setButtonAttr(R.color.colorWhite, R.drawable.shape_rectangle_corner4_blue_solid)
                .addViewMutileAll(attribute, R.layout.textview_flow, list, 5, true);
    }

    public void saveClothes() {
        //厚薄
        String thicknessStr = String.valueOf(thickness.getProgress() + 1);
        Log.d(TAG, "thickness: " + thicknessStr);
        //场合
        List list = fv_attribute.getSelecteds();
        StringBuilder attStr = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals("0"))
                continue;
            attStr.append(attribute[Integer.parseInt(list.get(i).toString())]);
            attStr.append("|");
        }
        if (attStr.length() != 0)
            attStr.deleteCharAt(attStr.length() - 1);
        Log.d(TAG, "attribute: " + attStr.toString());
        //用户
        SharedPreferences share = getActivity().getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        String userid = share.getString("Email", null);
        Log.d(TAG, "userid: " + userid);
        //颜色
        String colorStr = String.format("%06X", 0xFFFFFF & color);
        Log.d(TAG, "color: " + colorStr);

        //构建json
        ClothesInformationPack clothes = new ClothesInformationPack(dressid, style, colorStr, thicknessStr, attStr.toString(), userid);
        JSONObject json = JsonUtils.clothes2JsonWithDressid(clothes.getValues());
        try {
            System.out.println(json.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //发送json
        postJson(json.toString());
        //接受异步线程的消息
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String resp = (String) msg.obj;
                        //Log.d(TAG, "AddClothesResp: "+resp);
                        //操作数据库
                        try {
                            DataBase db = new DataBase("clothes",getContext());
                            db.update(clothes.getValues());
                            db.close();
                            ToastUtils.showShort(getContext(),"修改成功");
                            pop();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(getContext(),"修改失败");
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
                .url("http://39.105.83.165/service/clothes/updateClothesByDressid.action")
                .post(body)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "EditClothes 网络请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "EditClothes 网络请求返回码:" + response.code());
                //Log.d(TAG, "AddClothes 网络请求返回:" + resp);
                Message message = Message.obtain();
                message.what = 1;
                message.obj = resp;
                handler.sendMessage(message);
            }
        });
    }

    private void setView(View view) {
        Clothes clothes = null;
        try {
            DataBase db = new DataBase("clothes", getContext());
            clothes = db.queryByDressid(dressid);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int colorStr = Integer.parseInt(clothes.getColor(), 16);
        int red = (colorStr & 0xff0000) >> 16;
        int green = (colorStr & 0x00ff00) >> 8;
        int blue = (colorStr & 0x0000ff);
        color = Color.rgb(red, green, blue);//一个按无符号数，一个按有符号数，所以从新组了一遍
        colorDisplay.setCardBackgroundColor(color);
        style = clothes.getStyle();
        Uri uri = PhotoUtils.getPhotoUri(dressid);
        if (uri != null) {
            photo.setImageURI(uri);
        }
        thickness.setProgress(Integer.parseInt(clothes.getThickness()) - 1);
    }

}

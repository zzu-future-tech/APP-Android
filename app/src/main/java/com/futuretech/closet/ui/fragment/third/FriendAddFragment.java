package com.futuretech.closet.ui.fragment.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.futuretech.closet.R;
import com.futuretech.closet.base.BaseBackFragment;
import com.futuretech.closet.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static android.support.constraint.Constraints.TAG;

public class FriendAddFragment extends BaseBackFragment {
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> upload=new ArrayList<>();

    private GridView gridView;
    private GridAdapter gridAdapter;
    private Toolbar toolbar;


    public static FriendAddFragment newInstance() {
        Bundle args = new Bundle();
        FriendAddFragment fragment = new FriendAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_third_add, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        initToolbarNav(toolbar);
        toolbar.setTitle("发送动态");
        gridView = view.findViewById(R.id.gridviews);

        Button sendBtn= view.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String  string=tv_click.getText().toString();
                //doLogin();
                ToastUtils.showShort(getContext(),"发送成功！");
                pop();
            }
        });

        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    imagePaths.remove("000000");

                    Intent intent= new Intent(getContext(), MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,true);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,9);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,MultiImageSelectorActivity.MODE_MULTI);
                    intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,imagePaths);
                    startActivityForResult(intent,9);


                    if(!imagePaths.contains("000000")){
                        imagePaths.add("000000");
                    }

                }catch(Exception e){
                    Toast.makeText(getContext(),"failed" , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            Log.d(TAG, "数量："+list.size());
            loadAdpater(list);
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        paths.remove("000000");
        if(paths.size()<9)
            paths.add("000000");
        imagePaths.addAll(paths);
        upload.addAll(paths);
        upload.remove("000000");
        gridAdapter  = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        String path1=imagePaths.get(0);
        File p1=new File(path1);
        File p2=new File(p1.getParent());
//        Toast.makeText(getContext(),p2.getParent() , Toast.LENGTH_SHORT).show();

    }
    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
           /* if(listUrls.size() == 11){
                listUrls.remove(listUrls.size()-1);
            }*/
            inflater = LayoutInflater.from(getContext());
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.listview_friends_adapter_photo, parent,false);
                holder.image = convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.drawable.find_add_img);
            }else {
                Glide
                        .with(getContext())
                        .load(path)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(holder.image);
            }
            return convertView;
        }
        class ViewHolder {
            ImageView image;
        }
    }
}


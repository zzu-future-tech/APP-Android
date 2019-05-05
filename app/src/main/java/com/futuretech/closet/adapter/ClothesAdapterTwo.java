package com.futuretech.closet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuretech.closet.R;
import com.futuretech.closet.model.Clothes;
import com.futuretech.closet.model.ClothesClass;
import com.futuretech.closet.utils.PhotoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class ClothesAdapterTwo extends ArrayAdapter<Clothes> {
    public ClothesAdapterTwo(@NonNull Context context, @NonNull List<Clothes> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_clothes_adapter_clothes, parent, false);
        }

        //设置背景色
        Clothes currentItem = getItem(position);
        CardView cardView=listItemView.findViewById(R.id.cardView);
        int color = Color.parseColor("#"+currentItem.getColor());
        cardView.setCardBackgroundColor(color);

        //设置图片
        ImageView imageView = listItemView.findViewById(R.id.imageView);
        int dressid = currentItem.getDressid();
        Uri uri = PhotoUtils.getPhotoUri(dressid);
        if(uri!=null){
            imageView.setImageURI(uri);
        }

        return listItemView;
    }
}

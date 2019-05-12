package com.futuretech.closet.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuretech.closet.R;
import com.futuretech.closet.model.ClothesClass;
import com.futuretech.closet.model.SuitClass;
import com.futuretech.closet.utils.PhotoUtils;

import java.util.List;
import java.util.Objects;

public class SuitAdapter extends ArrayAdapter<SuitClass> {
    public SuitAdapter(@NonNull Context context, @NonNull List<SuitClass> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_clothes_adapter_suit, parent, false);
        }

        SuitClass currentItem = getItem(position);
        //设置图片
        ImageView top=listItemView.findViewById(R.id.suitTop);
        ImageView bottom=listItemView.findViewById(R.id.suitBottom);

        Uri uriTop = PhotoUtils.getPhotoUri(currentItem.getTopId());
        if(uriTop!=null){
            top.setImageURI(uriTop);
        }
        Uri uriBottom = PhotoUtils.getPhotoUri(currentItem.getBottomId());
        if(uriBottom!=null){
            bottom.setImageURI(uriBottom);
        }
        return listItemView;

    }
}

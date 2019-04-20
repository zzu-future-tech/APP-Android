package com.futuretech.closet.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuretech.closet.R;
import com.futuretech.closet.model.ClothesOne;

import java.util.List;
import java.util.Objects;

public class ClothesAdapterOne extends ArrayAdapter<ClothesOne> {



    public ClothesAdapterOne(@NonNull Context context, @NonNull List<ClothesOne> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview_clothes_adapter_one, parent, false);
        }

        ClothesOne currentItem = getItem(position);
        //设置图片和文字
        ImageView imageView=listItemView.findViewById(R.id.imageView);
        imageView.setImageDrawable(this.getContext().getResources().getDrawable(Objects.requireNonNull(currentItem).getImageSrc()));

        TextView textView = listItemView.findViewById(R.id.textView);
        textView.setText(Objects.requireNonNull(currentItem).getName());



        return listItemView;

    }

}

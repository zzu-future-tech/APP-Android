package com.futuretech.closet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.futuretech.closet.R;
import com.futuretech.closet.model.ClothesTwo;

import java.util.List;
import java.util.Objects;

public class ClothesAdapterTwo extends ArrayAdapter<ClothesTwo> {
    public ClothesAdapterTwo(@NonNull Context context, @NonNull List<ClothesTwo> objects) {
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

        ClothesTwo currentItem = getItem(position);
        TextView textView = listItemView.findViewById(R.id.textView);
        textView.setText(Objects.requireNonNull(currentItem).getName());



        return listItemView;

    }
}

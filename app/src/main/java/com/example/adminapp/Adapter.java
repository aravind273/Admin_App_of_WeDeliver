package com.example.adminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<CardItemClass> {
    public Adapter(@NonNull Context context, ArrayList<CardItemClass> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }
        CardItemClass cardItem = getItem(position);
        TextView cardName = listitemView.findViewById(R.id.cardName);
        ImageView cardImage = listitemView.findViewById(R.id.cardImage);
        cardName.setText(cardItem.getcard_name());
        cardImage.setImageResource(cardItem.getCard_image());
        return listitemView;
    }
}
package com.example.adminapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userAdapter extends ArrayAdapter<CardUserClass> {
    public userAdapter(@NonNull Context context, ArrayList<CardUserClass> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_user, parent, false);
        }
        CardUserClass cardItem = getItem(position);
        TextView userName = listitemView.findViewById(R.id.user_name);
        TextView userAddress = listitemView.findViewById(R.id.user_address);
        TextView userphone = listitemView.findViewById(R.id.user_phonenumber);

        ImageView userImage=listitemView.findViewById(R.id.userImage);
        userName.setText(cardItem.getUser_name());
        userAddress.setText(cardItem.getUser_address());
        userphone.setText(cardItem.getUser_phoneNumber());
        Picasso.get().load(cardItem.getUser_image()).into(userImage);
        return listitemView;
    }
}
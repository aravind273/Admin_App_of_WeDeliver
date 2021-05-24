package com.example.adminapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class orderAdapter extends ArrayAdapter<CardOrderClass> {
    public orderAdapter(@NonNull Context context, ArrayList<CardOrderClass> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_orders, parent, false);
        }
        CardOrderClass cardItem = getItem(position);
        TextView orderName = listitemView.findViewById(R.id.order_user_name);
        TextView orderAddress = listitemView.findViewById(R.id.order_user_address);
        TextView orderphone = listitemView.findViewById(R.id.order_user_phonenumber);
        TextView orderemail = listitemView.findViewById(R.id.order_email);
        TextView orderDetails = listitemView.findViewById(R.id.order_details);
        TextView orderTotalPayableAmount = listitemView.findViewById(R.id.order_total_payable_amount);



        orderName.setText(cardItem.getOrder_name());
        orderAddress.setText(cardItem.getOrder_address());
        orderphone.setText(cardItem.getOrder_phone());
        orderemail.setText(cardItem.getOrder_email());
        orderTotalPayableAmount.setText("₹"+cardItem.getTotal_payable_amount());
        orderDetails.setText(cardItem.getOrder_item_details());
        return listitemView;
    }
}
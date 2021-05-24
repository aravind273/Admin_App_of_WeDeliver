package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         GridView gridView = findViewById(R.id.home);

        ArrayList<CardItemClass> arrayList = new ArrayList<CardItemClass>();
        arrayList.add(new CardItemClass("Add Grocery Items", R.drawable.add));
        arrayList.add(new CardItemClass("Add Laundry Service", R.drawable.ic_baseline_local_laundry_service_24));
        arrayList.add(new CardItemClass("Add Rental Service", R.drawable.rental));

        arrayList.add(new CardItemClass("Show Orders", R.drawable.cart));
        arrayList.add(new CardItemClass("Show Users", R.drawable.users2));

        Adapter adapter = new Adapter(this, arrayList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayList.get(i).getcard_name().equals("Add Grocery Items"))
                {
                    Intent intent=new Intent(getApplicationContext(),add_items.class);
                    startActivity(intent);
                }
                else if(i==1)
                {
                    Intent intent=new Intent(getApplicationContext(),laundry.class);
                    startActivity(intent);
                }
                else if(i==2)
                {
                    Intent intent=new Intent(getApplicationContext(),rental.class);
                    startActivity(intent);
                }
                else if(i==3)
                {
                   Intent intent=new Intent(getApplicationContext(),show_orders.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),show_users.class);
                    startActivity(intent);
                }
            }
        });
    }
}
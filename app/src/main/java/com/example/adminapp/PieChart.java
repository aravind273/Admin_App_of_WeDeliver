package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class PieChart extends AppCompatActivity {
    AnyChartView anyChartView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        getUsers_OrdersCount();
        setUpPieChart();
    }

    private void getUsers_OrdersCount() {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("order");
        ArrayList<Pair<String,Integer>> arrayList_users_order_count=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DatabaseReference databaseReference_new_node=FirebaseDatabase.getInstance().getReference().child("orders_count");

                for(DataSnapshot snapshot1:snapshot.getChildren()) {

                    String email = "";
                    email += snapshot1.getKey();
                    int val = (int) snapshot1.getChildrenCount();
                    databaseReference_new_node.child(email).child("orders").setValue(""+val);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("users");
        ArrayList<Pair<String,String>> arrayList_users=new ArrayList<>();
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference databaseReference_new_node=FirebaseDatabase.getInstance().getReference().child("orders_count");
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String email="";
                    email+=snapshot1.getKey();
                    String name="";
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {
                        if(snapshot2.getKey().equals("name"))
                        {
                            name=snapshot2.getValue().toString();
                        }
                    }
                    databaseReference_new_node.child(email).child("name").setValue(name);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("orders_count");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<String> users=new ArrayList<>();
                ArrayList<Integer> orders_count=new ArrayList<>();
                Pie pie= AnyChart.pie();
                List<DataEntry> dataEntryList=new ArrayList<>();
                anyChartView=findViewById(R.id.anychartview);

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    if(snapshot1.getChildrenCount()>1) {
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            if(snapshot2.getKey().equals("name"))
                            {
                                users.add(snapshot2.getValue().toString());
                            }
                            else if(snapshot2.getKey().equals("orders"))
                            {
                                orders_count.add(Integer.parseInt(snapshot2.getValue().toString()));
                            }

                        }
                    }
                }
                for(int i=0;i<orders_count.size();i++)
                {
                    dataEntryList.add(new ValueDataEntry(users.get(i),orders_count.get(i)));
                }
                dataEntryList.add(new ValueDataEntry("Aravind",50));
                dataEntryList.add(new ValueDataEntry("Baldev",20));
                dataEntryList.add(new ValueDataEntry("lalit",5));

                pie.data(dataEntryList);
                anyChartView.setChart(pie);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void setUpPieChart() {


    }
}
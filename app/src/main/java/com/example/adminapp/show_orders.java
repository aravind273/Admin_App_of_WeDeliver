package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class show_orders extends AppCompatActivity {
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ArrayList<String> ordered_user_emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orders);
        GridView gridView = findViewById(R.id.order_gridview);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("order");
        ordered_user_emails=new ArrayList<>();
        ArrayList<String> user_item_details = new ArrayList<>();
        ArrayList<Long> user_total_payable_amount=new ArrayList<>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())//email id
                {
                    ordered_user_emails.add(snapshot1.getKey());
                    Log.d("fuck2",snapshot1.getKey());


                    String final_string="";
                    long total_payable_amount=0;
                    for(DataSnapshot snapshot2:snapshot1.getChildren())
                    {
                        String date = "";
                        String time = "";
                        String payment = "";
                        String orderDetails = "";
                        long totalamount = 0;
                        for(DataSnapshot snapshot3:snapshot2.getChildren()) {//date and time



                            if (snapshot3.getKey().equals("date")) {
                                date += snapshot3.getValue().toString();
                            } else if (snapshot3.getKey().equals("time")) {
                                time += snapshot3.getValue().toString();
                            } else if (snapshot3.getKey().equals("payment")) {
                                payment += snapshot3.getValue().toString();
                            } else
                                {
                                    String name = "";
                                    String count = "";
                                    long payableAmount = 0;

                                for (DataSnapshot snapshot4 : snapshot3.getChildren())//products
                                {


                                        if (snapshot4.getKey().equals("name")) {
                                            name += snapshot4.getValue(String.class);

                                        } else if (snapshot4.getKey().equals("count")) {
                                            count += snapshot4.getValue().toString();
                                        } else if (snapshot4.getKey().equals("selling_price")) {
                                            payableAmount += Long.parseLong(snapshot4.getValue().toString());
                                        }
                                }
                                    totalamount += payableAmount;
                                    orderDetails += name + " - " + count + "\n";
                            }
                        }
                        total_payable_amount+=totalamount;
                        final_string += "Date:- " + date + "\n" + "Time:- " + time + "\n" + "\nOrder Details:-\n" + orderDetails + "Payable Amount:- " + totalamount + "\n" + "Payment:- " + payment + "\n\n";

                    }
//                    Log.d("fuck1", final_string);
//                    Log.d("fuck2",""+total_payable_amount);

                    user_item_details.add(final_string);
                    user_total_payable_amount.add(total_payable_amount);

                }
                ArrayList<CardOrderClass> adapter_arraylist=new ArrayList<>();
                DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("users");
                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {//email
                        for(DataSnapshot snapshot1:snapshot.getChildren()) {
                            String user_email = snapshot1.getKey();
                            Log.d("fuck2", user_email);

                            for (int i = 0; i < ordered_user_emails.size(); i++) {
                                if (user_email.equals(ordered_user_emails.get(i))) {

                                    String name = "";
                                    String email = "";
                                    String phone = "";
                                    String address = "";
                                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                                        if (snapshot2.getKey().equals("name")) {
                                            name += snapshot2.getValue().toString();
                                        } else if (snapshot2.getKey().equals("email")) {
                                            email += snapshot2.getValue().toString();
                                        } else if (snapshot2.getKey().equals("phonenumber")) {
                                            phone += snapshot2.getValue().toString();
                                        } else if (snapshot2.getKey().equals("address")) {
                                            address += snapshot2.getValue().toString();
                                        }
                                    }
                                    CardOrderClass cardOrderClass = new CardOrderClass(name, email, phone, address, user_item_details.get(i), user_total_payable_amount.get(i));
                                    adapter_arraylist.add(cardOrderClass);
                                }

                            }
                            orderAdapter orderAdapter = new orderAdapter(getApplicationContext(), adapter_arraylist);
                            gridView.setAdapter(orderAdapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
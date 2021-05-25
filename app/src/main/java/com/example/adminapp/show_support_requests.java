package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_support_requests extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    TextView nothingtoshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_support_requests);
        recyclerView=findViewById(R.id.recyclerview_support_request);
        nothingtoshow=findViewById(R.id.nothingtoshow);
        firebaseAuth=FirebaseAuth.getInstance();

        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference().child("support");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ArrayList<String>> arrayList = new ArrayList<>();

                for(DataSnapshot snapshot3:snapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot3.getChildren()) {
                        boolean temp = true;
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            if (snapshot2.getKey().equals("resolved")) {
                                String val = snapshot2.getValue().toString();
                                if (val.equals("not")) {
                                    Log.d("hello", snapshot1.getKey());

                                    temp = false;
                                    break;
                                }
                            }
                        }

                        if (!temp) {

                            ArrayList<String> arrayList_data = new ArrayList<>();
                            arrayList_data.add(snapshot3.getKey());
                            for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                if (snapshot2.getKey().equals("problem")) {
                                    arrayList_data.add(snapshot2.getValue().toString());
                                } else if (snapshot2.getKey().equals("date")) {
                                    arrayList_data.add(snapshot2.getValue().toString());
                                }
                                else if (snapshot2.getKey().equals("time")) {
                                    arrayList_data.add(snapshot2.getValue().toString());
                                }
                            }
                            arrayList.add(arrayList_data);
                        }
                    }
                }
                if(arrayList.size()==0)
                {
                    nothingtoshow.setVisibility(View.VISIBLE);

                }
                RequestAdapter requestAdapter=new RequestAdapter(getApplicationContext(),arrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(requestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
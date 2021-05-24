package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class show_users extends AppCompatActivity {
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        GridView gridView = findViewById(R.id.users_gridview);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        storageReference= FirebaseStorage.getInstance().getReference().child("users");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CardUserClass> arrayList = new ArrayList<CardUserClass>();

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String name="";
                    String address="";
                    String phonenumber="";
                    String imageLink="";
                      for(DataSnapshot snapshot2:snapshot1.getChildren())
                      {
                          if(snapshot2.getKey().equals("name"))
                          {
                              name=snapshot2.getValue(String.class);

                          }
                          else if(snapshot2.getKey().equals("phonenumber"))
                          {
                              phonenumber=snapshot2.getValue(String.class);

                          }
                          else if(snapshot2.getKey().equals("ImageLink"))
                          {
                              imageLink=snapshot2.getValue(String.class);
                          }
                          else {
                              address = snapshot2.getValue(String.class);
                          }

                      }
                      CardUserClass cardUserClass=new CardUserClass(name,address,phonenumber,imageLink);
                      arrayList.add(cardUserClass);
                }
                userAdapter adapter = new userAdapter(getApplicationContext(), arrayList);
                gridView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
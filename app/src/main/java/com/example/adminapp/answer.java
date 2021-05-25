package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class answer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        EditText answer=findViewById(R.id.answer);
        Button submit=findViewById(R.id.submit_answer);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList=getIntent().getStringArrayListExtra("array");
                String date=arrayList.get(0);
                String time=arrayList.get(1);
                String mail=arrayList.get(2);
                Log.d("date",date);
                Log.d("time",time);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("support").child(mail).child(date+time);
                databaseReference.child("resolved").setValue("yes");
                databaseReference.child("answer").setValue(answer.getText().toString());
                finish();
            }

        });
    }

}
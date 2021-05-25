package com.example.adminapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.Viewholder>{
    ArrayList<ArrayList<String>> arrayList;
    Context context;
    public RequestAdapter(Context context, ArrayList<ArrayList<String>> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public RequestAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_support_request, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.Viewholder holder, int position) {
        ArrayList<String> details=arrayList.get(position);
        holder.Date.setText(details.get(1));
        holder.problem.setText(details.get(2));
        holder.Time.setText(details.get(3));
        holder.name.setText(details.get(0));
        holder.resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(context,answer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ArrayList<String> arrayList=new ArrayList<>();
                arrayList.add(details.get(1));
                arrayList.add(details.get(3));
                arrayList.add(details.get(0));
                intent.putStringArrayListExtra("array",arrayList);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder {
        TextView Date,Time,name,problem;
        Button  resolve;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Date=itemView.findViewById(R.id.date);
            Time=itemView.findViewById(R.id.time);
            name=itemView.findViewById(R.id.request_name);
            problem=itemView.findViewById(R.id.request_problem);
            resolve=itemView.findViewById(R.id.resolve);

        }
    }
}

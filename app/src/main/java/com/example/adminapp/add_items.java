package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.UUID;

public class add_items extends AppCompatActivity {
    EditText name,price,no_of_items,brand_name1,selling_price;
    String link;
    long discount;
    ImageView selected_image;
    Button select,submit;
    private static int RESULT_LOAD_IMAGE = 1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri selectedImage;
    static long count=0;
    ItemInfo itemInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        name=findViewById(R.id.item_name);
        price=findViewById(R.id.item_price);
        no_of_items=findViewById(R.id.no_of_items);
        selected_image=findViewById(R.id.item_image);
        select=findViewById(R.id.select);
        submit=findViewById(R.id.submit);
        brand_name1=findViewById(R.id.brand_name);
        selling_price=findViewById(R.id.item_selling_price);
        itemInfo=new ItemInfo();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_image!=null)
                {
                    String item_name1=name.getText().toString();
                    String price1=price.getText().toString();
                    String quantity=no_of_items.getText().toString();
                    String brand_name=brand_name1.getText().toString();
                    String sp=selling_price.getText().toString();
                    if(item_name1==null || !isNumeric(price1) || !isNumeric(quantity) || !isNumeric(sp))
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Fill all the details correctly", Snackbar.LENGTH_SHORT).show();;

                    }
                    else {
                        itemInfo.setName(item_name1);
                        itemInfo.setPrice(price1);
                        itemInfo.setNo_of_items(quantity);
                        itemInfo.setBrand_name(brand_name);
                        itemInfo.setSelling_price(sp);
                        long cp=Long.parseLong(price1);
                        long sp1=Long.parseLong(sp);
                        discount=((cp-sp1)*100/cp);
                        itemInfo.setBooked_items_count("0");
                        itemInfo.setDiscount(""+discount);
                        storageReference = FirebaseStorage.getInstance().getReference("Items").child("Item_" + itemInfo.getName()).child(itemInfo.getName() + "_" + UUID.randomUUID().toString());
                        databaseReference = FirebaseDatabase.getInstance().getReference("Items").child("Item_" + itemInfo.getName());
                        UploadDataIntoFirebase();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select Image",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadDataIntoFirebase() {

        if(selected_image!=null)
        {
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            count++;
            storageReference.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Image Uploaded successfully", Snackbar.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            databaseReference.child("link").setValue(uri.toString());

                        }
                    });

                    databaseReference.setValue(itemInfo);

                }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot takeSnapshot) {
                                double progress
                                        = (100.0
                                        * takeSnapshot.getBytesTransferred()
                                        / takeSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                        }
                    });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null) {
             selectedImage = data.getData();
            selected_image.setImageURI(selectedImage);
        }
    }


    public static boolean isNumeric(String string) {
        Long intValue;


        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }
}
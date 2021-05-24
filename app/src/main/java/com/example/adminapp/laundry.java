package com.example.adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class laundry extends AppCompatActivity {
    EditText name,service,address,price,mobilenumber;
    ImageView selected_image;
    Button select,save;
    Uri selectedImage;
    private static int RESULT_LOAD_IMAGE = 1;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    LaundryInfo laundryInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

        name=findViewById(R.id.laundry_person_name);
        service=findViewById(R.id.name_of_services);
        address=findViewById(R.id.address);
        price=findViewById(R.id.price);
        mobilenumber=findViewById(R.id.mobile_number);
        select=findViewById(R.id.select);
        selected_image=findViewById(R.id.laundry_person_image);
        save=findViewById(R.id.button_laundry);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_image!=null)
                {
                    String person_name=name.getText().toString();
                    String price1=price.getText().toString();
                    String services=service.getText().toString();
                    String address1=address.getText().toString();
                    String mobilenumber1=mobilenumber.getText().toString();
                    if(person_name==null || price1==null || services==null || address1==null || mobilenumber1==null)
                    {
                        Snackbar.make(findViewById(android.R.id.content), "Fill all the details correctly", Snackbar.LENGTH_SHORT).show();;

                    }
                    else {

                        laundryInfo=new LaundryInfo();
                        laundryInfo.setName(person_name);
                        laundryInfo.setPrice(price1);
                        laundryInfo.setMobilenumber(mobilenumber1);
                        laundryInfo.setAddress(address1);
                        laundryInfo.setServices(services);


                        storageReference = FirebaseStorage.getInstance().getReference("Laundry").child("Laundry_" + laundryInfo.getName()).child(laundryInfo.getName() + "_" + UUID.randomUUID().toString());
                        databaseReference = FirebaseDatabase.getInstance().getReference("Laundry").child("Laundry_" + laundryInfo.getName());


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
            storageReference.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content), "Image uploaded successfully", Snackbar.LENGTH_SHORT).show();
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    databaseReference.child("link").setValue(uri.toString());

                                }
                            });

                            databaseReference.setValue(laundryInfo);

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
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
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
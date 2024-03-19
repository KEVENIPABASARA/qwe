package com.example.sprinklesbakery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class addcakepage extends AppCompatActivity {
    EditText CakeName, CakePrice, CakeID;
    Button CakeOK, CakeCANCEL;
    ImageView Cakepic;

    String randomkey;
    Uri imageuri;

    FirebaseFirestore fs;
    FirebaseAuth fauth;
    FirebaseUser fuser;

    FirebaseStorage fstore;

    StorageReference storageReference;


    ActivityResultLauncher<Intent> activityluncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== RESULT_OK && result.getData() != null){
                        imageuri = result.getData().getData();
                        Cakepic.setImageURI(imageuri);
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CakeName = findViewById((R.id.cakename));
        CakePrice = findViewById((R.id.price1));
        Cakepic = findViewById(R.id.addimage);
        CakeOK =findViewById(R.id.ok);

        CakeCANCEL = findViewById((R.id.Cansel));
        CakeID = findViewById((R.id.IDNO));

        CakeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                randomkey = UUID.randomUUID().toString();

                fauth = FirebaseAuth.getInstance();
                fuser = fauth.getCurrentUser();
                fs = FirebaseFirestore.getInstance();

                String myCakeName = CakeName.getText().toString().trim();
                String myCakePrice = CakePrice.getText().toString().trim();
                String myCakeID = CakeID.getText().toString().trim();

                HashMap<Object,String> myhashmap = new HashMap<>();

                myhashmap.put("Cake Name", myCakeName);
                myhashmap.put("Cake Price", myCakePrice);

                CollectionReference mycustomer = fs.collection("Cakes");
                mycustomer.document(myCakeID).set(myhashmap);

                CakeID.setText("");
                CakeName.setText("");
                CakePrice.setText("");


                StorageReference mystore = storageReference.child("CakesPictures"+randomkey);
                mystore.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });

        Cakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                activityluncher.launch(i);
            }
        });

    }
}




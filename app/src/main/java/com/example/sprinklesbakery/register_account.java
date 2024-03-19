package com.example.sprinklesbakery;

import static android.os.Build.VERSION_CODES.O;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class register_account extends AppCompatActivity {

TextView Email,password,MNO,ADD;
Button reg,ba,can;

    FirebaseFirestore fs;
    FirebaseAuth fauth;
    FirebaseUser fuser;

ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        reg=findViewById(R.id.singup);
        ba=findViewById(R.id.back);
        can=findViewById(R.id.Cansel);
        Email=findViewById(R.id.E1);
        password=findViewById(R.id.pass1);
        MNO=findViewById(R.id.mobno);
        ADD =findViewById(R.id.address);
        progressBar=findViewById(R.id.progressBar);

            // back btn
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(register_account.this,MainActivity.class);
                        startActivity(i);
            }
        });

            //reg btn

          reg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  fauth = FirebaseAuth.getInstance();
                  fuser =fauth.getCurrentUser();
                  fs =FirebaseFirestore.getInstance();

                  String myemail =Email.getText().toString().trim();
                  String mypass = password.getText().toString().trim();
                  String mymobile = MNO.getText().toString().trim();

                  if (TextUtils.isEmpty(myemail)) {
                      Toast.makeText(register_account.this, "ENTER THE EMAIL", Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(mypass)) {
                      Toast.makeText(register_account.this, "ENTER THE PASSWORD", Toast.LENGTH_LONG).show();
                  }



                  fauth.createUserWithEmailAndPassword(myemail,mypass)
                          .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()){


                                }
                                HashMap<Object,String>myhashmap = new
                                        HashMap<>();


                                    myhashmap.put("NAME",ADD.getText().toString());
                                  myhashmap.put("Mobile No", MNO.getText().toString());
                                  myhashmap.put("Email", Email.getText().toString());
                                  myhashmap.put("Password", password.getText().toString());

                                  CollectionReference mycustomer = fs.collection("Customer");
                                  mycustomer.document(mymobile).set(myhashmap);


                                  MNO.setText("");
                                  Email.setText("");
                                  password.setText("");
                                   ADD.setText("");
                                  Intent i = new Intent(register_account.this, CUST_ACCOUNT.class);
                                  startActivity(i);
                                  finish();
                              }
                          });






              }
          });




    }





}
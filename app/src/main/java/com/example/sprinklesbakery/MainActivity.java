package com.example.sprinklesbakery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    ProgressBar progressBar1;
    Button logbtn,regbtn;

    FirebaseFirestore fs;
    FirebaseAuth fauth;
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = findViewById(R.id.username1);
        pass = findViewById(R.id.password1);
        logbtn = findViewById(R.id.loging);
        regbtn = findViewById(R.id.reg);
        progressBar1 = findViewById(R.id.progressBar1);


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MainActivity.this, register_account.class);
                startActivity(i);
                finish();


            }
        });

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String myemail = user.getText().toString().trim();
                String mypass = pass.getText().toString().trim();


                if (TextUtils.isEmpty(myemail)) {
                    Toast.makeText(MainActivity.this, "ENTER THE EMAIL", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(mypass)) {
                    Toast.makeText(MainActivity.this, "ENTER THE PASSWORD", Toast.LENGTH_LONG).show();
                }

                firebaseAuth.signInWithEmailAndPassword(myemail, mypass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    if (myemail.equals("Manager@gmail.com")&& mypass.equals("123456789")){

                                    Intent i =new Intent(MainActivity.this,Admin_account.class);
                                    startActivity(i);
                                    finish();
                                    }
                                    else {



                                    Toast.makeText(MainActivity.this, "LOGING ", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(MainActivity.this,CUST_ACCOUNT.class);
                                    startActivity(i);
                                    finish();
                                    }


                                } else {
                                    Toast.makeText(MainActivity.this, "AUTHENTION FAILED", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }

        });


    }
}





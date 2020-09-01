package com.example.CubeX;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserPick extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button consumer;
    Button admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pick);

        consumer = (Button) findViewById(R.id.Customer);
        admin = (Button) findViewById(R.id.Admin);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(UserPick.this, SignIn.class);

        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("Choice", "Consumer");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("Choice", "Admin");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }
}
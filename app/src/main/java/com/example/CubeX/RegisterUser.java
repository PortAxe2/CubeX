package com.example.CubeX;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class RegisterUser extends AppCompatActivity {


    CoordinatorLayout coordinatorLayout;

    EditText email,pass, pass_check;
    //ProgressBar progressBar;
    String Email;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText)findViewById(R.id.editTextTextEmailAddress);
        pass = (EditText)findViewById(R.id.editTextTextPassword);
        pass_check = (EditText)findViewById(R.id.editTextTextPassword3);
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    //Register button
    public void RegisterClick(final View view)
    {
        //Fetch input from user
        String emailInput = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        final String password_check = pass_check.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(emailInput))
        {
            Snackbar.make(view, "Enter Email" , Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Snackbar.make(view, "Enter password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(emailInput, password)
                .addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                            Snackbar.make(view, "Authentication Failed", Snackbar.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Intent i = new Intent(RegisterUser.this,SignIn.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("key" , "register");
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterUser.this, SignIn.class));
    }
}
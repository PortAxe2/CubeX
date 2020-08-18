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

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.content.Intent;

public class SignIn extends AppCompatActivity {


    //Declaration
    EditText email, password;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CubeX Login");
        setSupportActionBar(toolbar);

        email = (EditText)findViewById(R.id.editTextTextEmailAddress3);
        password = (EditText)findViewById(R.id.editTextTextPassword2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
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

    @Override
    public void onStart()
    {
        super.onStart();
        //If user is logged in, go to homepage
        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, Homepage.class));
            finish();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    //Sign in button click
    public void loginButtonClicked(final View view)
    {
        String Email = email.getText().toString();
        final String pass = password.getText().toString();

        //Validation
        if(TextUtils.isEmpty(Email))
        {
            Snackbar.make(view, "Enter Email", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pass))
        {
            Snackbar.make(view, "Enter password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        if(password.length() < 6)
        {
            password.setError("Password not long enough");
        }

        auth.signInWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            //Error
                            Snackbar snackbar = Snackbar
                                    .make(view, "There was an error", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.GONE);
                        }

                        else
                        {
                            Intent intent = new Intent(SignIn.this, Homepage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("key" , "signin");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }

    public void click_register(View view)
    {
        Intent intent = new Intent(SignIn.this, RegisterUser.class);
        startActivity(intent);
        finish();
    }



}
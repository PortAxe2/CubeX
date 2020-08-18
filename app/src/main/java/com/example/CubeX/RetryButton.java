package com.example.CubeX;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


public class RetryButton extends AppCompatActivity {

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry_button);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet connection")
                .setMessage("Please make sure you have an active internet connection.")
                .setCancelable(false)
                .setPositiveButton("Use NFC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        startActivity(new Intent(RetryButton.this, nfcMenu.class));
                        finish();
                    }})
                .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(RetryButton.this, Homepage.class));
                    }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
package com.example.CubeX;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;

import android.view.View;
import android.widget.TextView;


public class Scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;

    String res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Scan your QR Code");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Scanner.this, Homepage.class));
            }
        });
        toolbar.setTitleTextColor(0xFFFFFFFF);
        Button confirmButton = findViewById(R.id.button5);
        //Button retryButton = findViewById(R.id.button6);

        //retryButton.setBackgroundColor(0x808080);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        scannerView = findViewById(R.id.scannerView);

        codeScanner = new CodeScanner(this, scannerView);
        //resultData = findViewById(R.id.resultsOfQr);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //resultData.setText(result.getText());
                        res = result.getText();

                    }
                });
            }
        });

    }

    public void retry_clicked(View view)
    {
        recreate();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Scanner.this, Homepage.class));
    }

    public void confirm_clicked(View view)
    {
        if(res != null)
        {
            Intent intent = new Intent(this, deviceInfo.class);

            Bundle bundle = new Bundle();
            bundle.putString("deviceID", res);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        codeScanner.startPreview();
    }

}
package com.example.CubeX;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import androidx.appcompat.widget.Toolbar;


import android.widget.Button;

import android.view.View;
import android.widget.TextView;


public class Scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;

    String res;

    String username;
    Boolean warningClicked = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    private static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        username = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        documentReference = db.collection("users").document(username);


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

        scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannerView);

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

    public void retry_clicked(View view) {
        recreate();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Scanner.this, Homepage.class));
    }

    public void confirm_clicked(View view) {
        if (res != null) {

            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                        }
                    }
                }
            });

            Intent intent = new Intent(this, deviceInfo.class);
            Bundle bundle = new Bundle();
            bundle.putString("deviceID", res);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

}
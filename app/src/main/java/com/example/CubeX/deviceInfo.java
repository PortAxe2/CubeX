package com.example.CubeX;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import android.view.View;

public class deviceInfo extends AppCompatActivity {


    private static final String TAG = "Hehe";
    String Location;
    String niname;
    String ID;
    int Battery;
    Double distanceSensor1;
    Double distanceSensor2;
    Double distanceSensor3;

    AlertDialog.Builder builder;

    EditText nickname;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Device Info");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(deviceInfo.this, Scanner.class));
            }
        });
        toolbar.setTitleTextColor(0xFFFFFFFF);

        nickname = (EditText) findViewById(R.id.nickname);
        builder = new AlertDialog.Builder(deviceInfo.this);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String key = bundle.getString("deviceID");

        final TextView location = findViewById(R.id.location);
        final TextView battery = findViewById(R.id.battery);
        final TextView sensor1 = findViewById(R.id.sensor1);
        final TextView sensor2 = findViewById(R.id.sensor2);
        final TextView sensor3 = findViewById(R.id.sensor3);


        final DocumentReference[] docRef = {db.collection("devices").document(key)};
        ID = key;


        final String TAG = "deviceInfo";

        //final TextView results = findViewById(R.id.textView45);

        docRef[0].get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //Declare the maps to store the values
                        Map<String, Double> sens1 = new HashMap<String, Double>();
                        Map<String, Double> sens2 = new HashMap<String, Double>();
                        Map<String, Double> sens3 = new HashMap<String, Double>();

                        //Store data from Firestore to appropriate textViews
                        sens1 = (Map<String, Double>) document.getData().get("sensor1");
                        sens2 = (Map<String, Double>) document.getData().get("sensor2");
                        sens3 = (Map<String, Double>) document.getData().get("sensor3");


                        //Get the values to display
                        Location = "" + document.getData().get("location");
                        Battery = document.getLong("batteryLevel").intValue();
                        distanceSensor1 = sens1.get("distance");
                        distanceSensor2 = sens2.get("distance");
                        distanceSensor3 = sens3.get("distance");


                        //Output values on the screen
                        location.setText(Location);
                        battery.setText(Battery + "%");
                        sensor1.setText("Sensor 1: " + distanceSensor1 + " cm");
                        sensor2.setText("Sensor 2: " + distanceSensor2 + " cm");
                        sensor3.setText("Sensor 3: " + distanceSensor3 + " cm");

                    } else {
                        //Log.d(Tag, "No such document");
                        //results.setText("No such document");
                        builder.setTitle("Nonexistent device")
                                .setMessage("Scan QR Code again")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        startActivity(new Intent(deviceInfo.this, Scanner.class));
                                        finish();
                                    }
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } else {
                    //Log.d(Tag, "get failed with ", task.getException());
                    //results.setText("get failed with " + task.getException());
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(deviceInfo.this, Scanner.class));
    }

    public void confirmClick(View view) {

        niname = nickname.getText().toString();

        DocumentReference userRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        Map<String, String> values = new HashMap<String, String>();
        values.put("nickname",niname);
        values.put("device_id", ID);

        userRef.update("devices_registered", FieldValue.arrayUnion(values));

        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);

    }

}
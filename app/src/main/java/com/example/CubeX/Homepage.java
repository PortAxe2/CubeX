package com.example.CubeX;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Homepage extends AppCompatActivity implements DevicesAdapter.SelectedDevice {

    //FireStore Setup
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef_users = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    CollectionReference collectionReferenceRef_devices = db.collection("devices");


    private static final String TAG = "Homepage";

    //Device Class Variables
    String nickname , fillStatus, identifier;
    int battery, fillLevel, deviceImage, batteryIcon, fillIcon;

    //Layout
    RecyclerView recyclerView;
    Toolbar toolbar;

    //Recycler View
    List<String> devs;
    List<Device> deviceList = new ArrayList<>();
    DevicesAdapter devicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        recyclerView = findViewById(R.id.recyclerview);

        //Populate Lists
        //---------------------------------------------------------------------------

        docRef_users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    final DocumentSnapshot document = task.getResult();
                    if(document.exists())
                    {

                        //Get the list of devices the user has registered
                        devs = (List<String>) document.getData().get("devices_registered");

                        //Loop over each device in the devices collection
                        for(int i =  0 ; i < devs.size() ; i++)
                        {

                            DocumentReference documentReference_devices = collectionReferenceRef_devices.document(devs.get(i));

                            documentReference_devices.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        DocumentSnapshot document_devices = task.getResult();
                                        if(document_devices.exists())
                                        {
                                            //nickname = (String) document_devices.getData().get("nickname");
                                            nickname = "hahah";

                                            battery =  document_devices.getLong("batteryLevel").intValue();
                                            if (battery < 30) {batteryIcon = R.drawable.ic_warning_24px;}
                                            else batteryIcon = R.drawable.ic_check_circle_24px;

                                            fillLevel = document_devices.getLong("fillLevel").intValue();
                                            if (fillLevel < 30)
                                            {
                                                fillIcon = R.drawable.ic_warning_24px;
                                                fillStatus="Needs attention";
                                            }
                                            else
                                                {
                                                fillIcon = R.drawable.ic_check_circle_24px;
                                                fillStatus = "Good";
                                            }

                                            identifier="ZW3hJH3hzd2ooF0wqM2o";
                                            deviceImage=R.drawable.common_google_signin_btn_icon_dark;

                                            //Add to RecyclerView List
                                            Device device = new Device(nickname,deviceImage,batteryIcon,fillIcon,battery,fillStatus,identifier);
                                            deviceList.add(device);

                                        }

                                        devicesAdapter = new DevicesAdapter(deviceList, Homepage.this);
                                        recyclerView.setAdapter(devicesAdapter);

                                    }
                                }
                            });
                        }
                    }

                    else
                    {
                        //results.setText("No such document");
                    }
                }

                else
                {
                    //results.setText("get failed with " + task.getException());
                }
            }
        });

        //---------------------------------------------------------------------------


        //Toolbar Setup
        //---------------------------------------------------------------------------
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Your Devices List");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Homepage.this, SignIn.class));
                finish();
            }
        });
        toolbar.setTitleTextColor(0xFFFFFFFF);
        //---------------------------------------------------------------------------



        //RecyclerView Setup
        //---------------------------------------------------------------------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



        //---------------------------------------------------------------------------

        DocumentReference docRef = db.collection("devices").document("ZW3hJH3hzd2ooF0wqM2o");

        Snackbar snackbar = Snackbar
                .make(this.getWindow().getDecorView().findViewById(android.R.id.content), "Welcome", Snackbar.LENGTH_LONG);
        snackbar.show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addDevice);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                startActivity(new Intent(Homepage.this, Scanner.class));
                finish();
            }

        });
    }


    public void logoutButton(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }


    @Override
    public void selectedDevice(Device device) {

        startActivity((new Intent(Homepage.this, DeviceExpanded.class).putExtra("data", device)));


    }
}




































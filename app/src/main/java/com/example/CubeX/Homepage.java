package com.example.CubeX;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
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

import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Homepage extends AppCompatActivity {

    //private ArrayList<Device> deviceProperties = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("devices").document("ZW3hJH3hzd2ooF0wqM2o");


    RecyclerView recyclerView;
    Toolbar toolbar;

    List<Device> deviceList = new ArrayList<>();
    String nickname[]={"X's Device","Y's Device","Z's Device","A's Device","B's Device"};
    String fillStatus[]={"Needs Attention","Good","Needs Attention","Good","Needs Attention"};
    int battery[]={54,67,98,24,65};
    int deviceImage[]={R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,
            R.drawable.common_google_signin_btn_icon_dark,};
    int batteryIcon[]={R.drawable.ic_warning_24px,
            R.drawable.ic_check_circle_24px,
            R.drawable.ic_warning_24px,
            R.drawable.ic_check_circle_24px,
            R.drawable.ic_warning_24px};
    int fillIcon[]={R.drawable.ic_warning_24px,
            R.drawable.ic_check_circle_24px,
            R.drawable.ic_warning_24px,
            R.drawable.ic_check_circle_24px,
            R.drawable.ic_check_circle_24px};
    String identifier[]={"ZW3hJH3hzd2ooF0wqM2o","fefesw","jowhduwa","ejhfgeuh","jihfegf"};


    DevicesAdapter devicesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recyclerview);


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

        for(int i = 0 ; i < 5 ; i++)
        {
            Device device = new Device(nickname[i],deviceImage[i],batteryIcon[i],fillIcon[i],battery[i],fillStatus[i],identifier[i]);
            deviceList.add(device);
        }

        devicesAdapter = new DevicesAdapter(deviceList);

        recyclerView.setAdapter(devicesAdapter);


        //---------------------------------------------------------------------------

        DocumentReference docRef = db.collection("devices").document("ZW3hJH3hzd2ooF0wqM2o");

        Snackbar snackbar = Snackbar
                .make(this.getWindow().getDecorView().findViewById(android.R.id.content), "Welcome", Snackbar.LENGTH_LONG);
        snackbar.show();







        //-----------------------------------------------------------------------------------
        //listView = findViewById(R.id.listView);
        //-----------------------------------------------------------------------------------





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

    public void turnOff(View view)
    {
        docRef.update("Locked",false);
    }

    public void turnOn(View view)
    {
        docRef.update("Locked", true);
    }


    //-----------------------------------------------------------------------------------








    //-----------------------------------------------------------------------------------



    public void logoutButton(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }



}




































package com.example.CubeX;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Homepage extends AppCompatActivity implements DevicesAdapter.SelectedDevice {

    //FireStore Setup
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef_users = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    CollectionReference collectionReferenceRef_devices = db.collection("devices");


    private static final String TAG = "Homepage";

    //Device Class Variables
    String nickname, fillStatus;
    String identifier = "PlaceHolder";
    int battery, fillLevel, deviceImage, batteryIcon, fillIcon;

    AlertDialog.Builder builder;
    ProgressBar progressBar;

    //Layout
    RecyclerView recyclerView;
    Toolbar toolbar;

    //Recycler View
    List<Map<String, String>> registered_devices;
    List<Device> deviceList = new ArrayList<>();
    DevicesAdapter devicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(!(cm.getActiveNetwork() != null && cm.getActiveNetworkInfo().isConnected()))
        {
            startActivity(new Intent(this, RetryButton.class));
            finish();
        }


        recyclerView = findViewById(R.id.recyclerview);
        progressBar = (ProgressBar)findViewById(R.id.load_progress);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh_layout);

        progressBar.setVisibility(View.VISIBLE);

        //Populate Lists
        //---------------------------------------------------------------------------
        docRef_users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    final DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //Get the list of devices the user has registered
                        registered_devices = (List<Map<String, String>>) document.getData().get("devices_registered");

                        //Loop over each device in the devices collection
                        for (int i = 0; i < registered_devices.size(); i++) {

                            DocumentReference documentReference_devices = collectionReferenceRef_devices.document(registered_devices.get(i).get("device_id"));

                            final int iterator = i;
                            documentReference_devices.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document_devices = task.getResult();
                                        if (document_devices.exists()) {
                                            //nickname = (String) document_devices.getData().get("nickname");
                                            nickname = registered_devices.get(iterator).get("nickname");

                                            battery = document_devices.getLong("batteryLevel").intValue();
                                            if (battery < 30) {
                                                batteryIcon = R.drawable.ic_warning_24px;
                                            } else batteryIcon = R.drawable.ic_check_circle_24px;

                                            fillLevel = document_devices.getLong("fillLevel").intValue();
                                            if (fillLevel < 30) {
                                                fillIcon = R.drawable.ic_warning_24px;
                                                fillStatus = "Needs attention";
                                            } else {
                                                fillIcon = R.drawable.ic_check_circle_24px;
                                                fillStatus = "Good";
                                            }

                                            deviceImage = R.drawable.common_google_signin_btn_icon_dark;

                                            //Add to RecyclerView List
                                            identifier = registered_devices.get(iterator).get("device_id");
                                            Device device = new Device(nickname, deviceImage, batteryIcon, fillIcon, battery, fillStatus, identifier);
                                            deviceList.add(device);

                                        }

                                        devicesAdapter = new DevicesAdapter(deviceList, Homepage.this);
                                        recyclerView.setAdapter(devicesAdapter);

                                    }
                                }
                            });
                        }
                    } else {
                        //results.setText("No such document");
                    }
                } else {
                    //results.setText("get failed with " + task.getException());
                }
            }
        });
        //---------------------------------------------------------------------------

        progressBar.setVisibility(View.GONE);


        //Refresh Response
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
            }
        });


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

        int resId = R.anim.layout_animation_fall_down;
        Context context = recyclerView.getContext();
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
        recyclerView.setLayoutAnimation(animation);



        //---------------------------------------------------------------------------

        DocumentReference docRef = db.collection("devices").document(identifier);

        Snackbar snackbar = Snackbar
                .make(this.getWindow().getDecorView().findViewById(android.R.id.content), "Welcome", Snackbar.LENGTH_LONG);
        snackbar.show();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addDevice);
        FloatingActionButton nfcAction = (FloatingActionButton) findViewById(R.id.useNFC);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Homepage.this, Scanner.class));
                finish();
            }

        });

        nfcAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Homepage.this, nfcMenu.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void logoutButton(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignIn.class));
        finish();
    }

    @Override
    public void selectedDevice(Device device) {
        startActivity((new Intent(Homepage.this, DeviceExpanded.class).putExtra("data", device)));
    }
}




































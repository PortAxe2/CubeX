package com.example.CubeX;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.CubeX.ui.main.SectionsAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


public class DeviceExpanded extends AppCompatActivity {


    private static final String TAG = "ID";
    Toolbar toolbar;
    String nickname;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_expanded);
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        SectionsAdapter sectionsAdapter = new SectionsAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        toolbar = findViewById(R.id.toolbar_exp);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeviceExpanded.this, Homepage.class));
            }
        });


        Intent intent = getIntent();

        if(intent.getExtras() != null)
        {
            Device device = (Device) intent.getSerializableExtra("data");
            toolbar.setTitle(device.getNickname());
            sectionsAdapter.setID(device.getIdentifier());
            Log.d(TAG, device.getIdentifier());
            nickname = device.getNickname();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Homepage.class));
    }
}
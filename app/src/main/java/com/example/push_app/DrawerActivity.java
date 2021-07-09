package com.example.push_app;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingerpush.android.FingerPushManager;
import com.fingerpush.android.NetworkUtility;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.push_app.databinding.ActivityDrawerBinding;


import org.json.JSONObject;

import java.security.AllPermission;

public class DrawerActivity extends AppCompatActivity {

    private ActivityDrawerBinding binding;
    NavigationView navigationView;
    DrawerLayout drawLayout;
    ImageView logo;
    TextView appname;
    BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new MyReceiver();

        String appKey ="BDPA1Q3EYA96";
        String secretKey = "dQ78sXDHgrUfJ2Q2S2uxOCuZUXN2GXtE";
        FingerPushManager.setAppKey(appKey);
        FingerPushManager.setAppSecret(secretKey);
        FingerPushManager.getInstance(this).setDevice(new NetworkUtility.ObjectListener() {
            @Override
            public void onComplete(String code, String message, JSONObject jsonObject) {
                // code 200, 201 이 반환된 경우 정상적으로 디바이스가 등록된 것입니다.
            }

            @Override
            public void onError(String code, String message) {
                // 코드 504 가 반환된 경우 이미 디바이스가 등록된 상태입니다.
            }
        });

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView menu = findViewById(R.id.menu);
        logo = findViewById(R.id.iv_logo);
        appname = findViewById(R.id.tv_appname);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.maincontainer, new MainFragment());
        fragmentTransaction.commit();

        drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.maincontainer, new MainFragment());
                fragmentTransaction.commit();
                appname.setText(R.string.app_name);
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                DrawerActivity.this,
                drawLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawLayout.closeDrawers();

                int id = menuItem.getItemId();

                if(id == R.id.alert){
                    sendMyBroadcast();
                    appname.setText("알림");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new AlertFragment());
                    fragmentTransaction.commit();

                }
                else if(id==R.id.tag){
                    appname.setText("태그");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new TagFragment());
                    fragmentTransaction.commit();
                }
                else if(id==R.id.targeting){
                    appname.setText("타겟팅");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new TargetingFragment());
                    fragmentTransaction.commit();
                }
                else if(id==R.id.appinfo){
                    appname.setText("앱정보");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new AppInfoFragment());
                    fragmentTransaction.commit();
                }
                else if(id==R.id.guide){
                    appname.setText("이용가이드");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new GuideFragment());
                    fragmentTransaction.commit();
                }
                else if(id==R.id.setting){
                    appname.setText("알림설정");
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.maincontainer, new SettingFragment());
                    fragmentTransaction.commit();
                }

                return true;
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLayout.openDrawer(GravityCompat.END);
            }
        });

    }

    BroadcastReceiver br = new MyReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(MyReceiver.MY_ACTION);
        this.registerReceiver(br, filter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    public void sendMyBroadcast(){
        Intent intent = new Intent(MyReceiver.MY_ACTION);
        sendBroadcast(intent);
    }


}
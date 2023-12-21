package com.avinash.rider_gps_sos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity  {
    private Toolbar myToolbar;
    private MenuItem mcn;
    private TextView txt1,txt2,txt3,txt4;
    public boolean RideMode;
    private SwitchCompat RideSwitch;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Paper.init(this);

        txt1=(TextView)findViewById(R.id.txt1);
        txt2=(TextView)findViewById(R.id.txt2);
        txt3=(TextView)findViewById(R.id.txt3);
        txt4=(TextView)findViewById(R.id.txt4);

        RideSwitch = (SwitchCompat) findViewById(R.id.ModeSwitch) ;
        SharedPreferences sp=getSharedPreferences("save",MODE_PRIVATE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        101);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        102);
            }
        }



        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsoleActivity.class);
                startActivity(intent);

            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SosActivity.class);
                startActivity(intent);

            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeveloperInfoActivity.class);
                startActivity(intent);


            }
        });

        RideSwitch.setChecked(sp.getBoolean("value",false));

        RideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    startService(RideSwitch);


                } else {
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    stopService(RideSwitch);
                }
            }
        });




    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


            if(item.getItemId()== R.id.menu_mcn) {
                openSetting();
                return true;
            }

            else if(item.getItemId()==R.id.menu_feedback ) {
                OpenFeedBack();
                return true;
            }
            else if(item.getItemId()==R.id.menu_sign ) {
                ChangeLogType();
                return true;
            }
            return super.onOptionsItemSelected(item);

    }


    private void ChangeLogType() {
        Paper.book().destroy();

        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();





    }

    private void OpenFeedBack() {
        Intent intent = new Intent(MainActivity.this, feedbackActivity.class);
        startActivity(intent);
    }

    private void openSetting() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }


    public void startService(View v) {

            Intent SosServiceIntent=new Intent(this,myServiceImpl.class);

            startService( SosServiceIntent);
        }


    public void stopService(View v ){

            Intent SosServiceIntent=new Intent(this,myServiceImpl.class);

            stopService(SosServiceIntent);




    }
}
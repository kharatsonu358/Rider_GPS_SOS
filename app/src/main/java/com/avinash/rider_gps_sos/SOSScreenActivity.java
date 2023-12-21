package com.avinash.rider_gps_sos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.paperdb.Paper;

public class SOSScreenActivity extends AppCompatActivity  {

    public TextView ClockTxt;
    public SeekBar sb;
    String msg, no,Spot,no1,no2,loc;
    long MinTime=30;
    private FusedLocationProviderClient fusedLocationProviderClient;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_s_screen);
        Paper.init(this);
        MinTime=Long.parseLong(Paper.book().read(Prevalent.MinTime));
        Log.v("myTag", "val = "+String.valueOf(MinTime) );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        mediaPlayer=MediaPlayer.create(this,R.raw.ambulance_alert);
        mediaPlayer.start();


        Toast.makeText(getApplicationContext(),""+MinTime,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),""+Paper.book().read(Prevalent.MinTime),Toast.LENGTH_SHORT).show();
        sb = findViewById(R.id.myseek);
        ClockTxt = findViewById(R.id.ClockDownTimeText);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SOSScreenActivity.this);
        no = Paper.book().read(Prevalent.ContactOneKey);
        no1 =  Paper.book().read(Prevalent.ContactTwoKey);
        no2 =  Paper.book().read(Prevalent.ContactThreeKey);

        Log.d("ashwin", "this is cont 1 : "+no);
        Log.d("ashwin", "thisis cont 2 : "+no1);

        Log.d("ashwin", "thisis cont 3 : "+no2);


        msg="HELP! \n BIKE   On "+currentDateandTime+"\n";




        CountDownTimer CDT=new CountDownTimer(MinTime*1000, 1000) {

            public void onTick(long MinTime) {
                ClockTxt.setText("seconds remaining: " + MinTime / 1000);
            }

            public void onFinish() {
                ClockTxt.setText("done!");
                getCurrentLocation();




            }
        };
        CDT.start();



        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 95) {
                    CDT.cancel();
                    mediaPlayer.stop();


                    Intent i=new Intent(SOSScreenActivity.this,MainActivity.class);
                    startActivity(i);




                } else {
                    seekBar.setThumb(getDrawable(R.drawable.ic_baseline_thumb_up_24));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if(progress>95){
                    seekBar.setThumb(getDrawable(R.drawable.ic_baseline_thumb_up_24));


                }

            }
        });



    }
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {

                        loc = "(" + location.getLatitude() + "," + location.getLongitude() + ")";
                        Spot=" https://www.google.com/maps/place/"+loc;

                        SendMessage(Spot);
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), String.format("Exception in Client%s", e),Toast.LENGTH_SHORT).show();
        }
    }

    private void SendMessage(String spot) {

        msg+=spot;

        SmsManager sms=SmsManager.getDefault();

        sms.sendTextMessage(no, null, msg, null,null);
        sms.sendTextMessage(no1, null, msg, null,null);
        sms.sendTextMessage(no2, null, msg, null, null);




        Toast.makeText(getApplicationContext(), "Message Sent successfully! to "+no ,
                Toast.LENGTH_LONG).show();

    }


}
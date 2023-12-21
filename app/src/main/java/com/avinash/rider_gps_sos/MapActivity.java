package com.avinash.rider_gps_sos;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity {
    public EditText destEdt;
    public Button btnNavi;
    String sSource;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageButton TBImgMap;
        destEdt = findViewById(R.id.input_Source);
        btnNavi=findViewById(R.id.BtnNavigate);


        TBImgMap = findViewById(R.id.imgBtnMap);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapActivity.this);

         sSource = "";
        TBImgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnNavi.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                getCurrentLocation();


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

                        sSource = "(" + location.getLatitude() + "," + location.getLongitude() + ")";

                        OpenMaps(sSource);
                    }
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), String.format("Exception in Client%s", e),Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenMaps(String Soc){
        try {
            String SocLoc = Soc;

            String loc = destEdt.getText().toString().trim();

            if ( loc.length()<=5 ) {
                Toast.makeText(this, "Please Enter Detail Destination", Toast.LENGTH_SHORT).show();

            }
            else {
                Uri addressUrl = Uri.parse("https://www.google.com/maps/dir/" + SocLoc + "/" + loc);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUrl);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }



        }catch (ActivityNotFoundException e)
        {
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps?");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


        }
        }












}
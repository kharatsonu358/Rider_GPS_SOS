package com.avinash.rider_gps_sos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;

import java.util.Formatter;
import java.util.Locale;

import io.paperdb.Paper;

public class ConsoleActivity extends AppCompatActivity implements LocationListener {
    private ImageButton TBImg;
    private TextView SpeedIndicator,TripAVal,TripBVal,TopSpeedVal,AvgSpeedVal,AccSpeedVal;
    private Button btnSAcc,btnSAvg;
    private Button btnRstAcc ,rstTopSpeed;
    private float TopSpeed;
    private float AvgSpeed=0.0f;
    private float n=1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);


        TBImg = (ImageButton) findViewById(R.id.imgBtnCon);
        SpeedIndicator = (TextView) findViewById(R.id.speedText);
        TripAVal=(TextView)findViewById(R.id.txtTripAVal);
        TripBVal=(TextView)findViewById(R.id.txtTripBVal);
        TopSpeedVal=(TextView)findViewById(R.id.TopSpeedVal);
        AccSpeedVal=(TextView)findViewById(R.id.AccSpeedVal);
        AvgSpeedVal=(TextView) findViewById(R.id.AvgSpeedVal);
        rstTopSpeed = (Button) findViewById(R.id.btnTopSpeedReset);
        btnSAcc=(Button) findViewById(R.id.btnAccSpeedStart);
        btnRstAcc=(Button) findViewById(R.id.btnAccSpeedReset);
        btnSAvg=(Button) findViewById(R.id.btnAvgSpeedStart);




        TBImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rstTopSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopSpeedVal.setText("0.0");
                Paper.book().write(Prevalent.TopSpeed, "0.0");

            }
        });
        btnSAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnRstAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccSpeedVal.setText("0.0");
                Paper.book().write(Prevalent.Acceleration, "0.0");


            }
        });
        btnSAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            //start the program if the permission is granted
            doStuff();
        }






    }

    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this,"Waiting GPS Connection!", Toast.LENGTH_SHORT).show();

    }




    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            CollectLocation myLocation = new CollectLocation(location);

            updateSpeed(myLocation);
            updateDistance(myLocation);


            /*

            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<LatLng> latLngs = new ArrayList<>();
            locations.add(location);
            for (Location loc :  locations){
                latLngs.add(new LatLng(loc.getLocation().getLat(), loc.getLocation().getLng()));
            }
            Double dist = SphericalUtil.computeLength(latLngs);
            Double dist1 = 10.0;


            TripAVal.setText(dist1.toString());
*/
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {


    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void updateSpeed(CollectLocation location) {
        float nCurrentSpeed ;


        if (location != null) {
            nCurrentSpeed = location.getSpeed();

            try {
                TopSpeed = Float.parseFloat(TopSpeedVal.getText().toString());
                if (nCurrentSpeed >= TopSpeed) {
                    TopSpeed = 1.f * nCurrentSpeed;
                    Formatter fmt1 = new Formatter(new StringBuilder());
                    fmt1.format(Locale.US, "%5.1f", TopSpeed);
                    String strTopSpeed = fmt1.toString();
                    strTopSpeed = strTopSpeed.replace(" ", "0");

                    TopSpeedVal.setText(strTopSpeed);


                }
            } catch (Exception e) {
                Toast.makeText(this, "exception", Toast.LENGTH_SHORT).show();
            }


            Formatter fmt = new Formatter(new StringBuilder());
            fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
            String strCurrentSpeed = fmt.toString();
            strCurrentSpeed = strCurrentSpeed.replace(" ", "0");

            SpeedIndicator.setText(strCurrentSpeed);
        }



    }

    private void updateLocation(CollectLocation location) {


    }

    private void updateDistance(CollectLocation location) {

        double distance;
        double templon;
        double templat;

       /* if ( (templon). || (templat == null) ){
            templon =  location.getLongitude();
            templat =  location.getLatitude();
            };



        double templon =  location.getLongitude();
        double templat =  location.getLatitude();



        location.distanceBetween (double startLatitude,
        double startLongitude,
        double endLatitude,
        double endLongitude,
        float[] results)
*/

    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.doStuff();
            } else {
                finish();
            }
        }


    }

}
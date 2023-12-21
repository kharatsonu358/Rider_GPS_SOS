package com.avinash.rider_gps_sos;

import static com.avinash.rider_gps_sos.App.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;

import io.paperdb.Paper;



public class myServiceImpl extends Service implements SensorEventListener {

    // TAG to identify notification
    private static final int NOTIFICATION = 007;

    // IBinder object to allow Activity to connect
    private final IBinder mBinder = new LocalBinder();

    // Sensor Objects
    private Sensor accelerometer;
    private SensorManager mSensorManager;
    private boolean MNotIsChecked;

    private double accelerationX, accelerationY, accelerationZ;

    private int threshold = 15;
    private String buff;

    // Notification Manager
    private NotificationManager mNotificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        public myServiceImpl getService() {
            return myServiceImpl.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buff= Paper.book().read(Prevalent.MuteNotKey);
        MNotIsChecked=Boolean.getBoolean(buff);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this);                            // Unregister sensor when not in use

        mNotificationManager.cancel(NOTIFICATION);
        stopSelf();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        accelerationX = (Math.round(sensorEvent.values[0]*1000)/1000.0);
        accelerationY = (Math.round(sensorEvent.values[1]*1000)/1000.0);
        accelerationZ = (Math.round(sensorEvent.values[2]*1000)/1000.0);

        if (accelerationX > threshold || accelerationY > threshold || accelerationZ > threshold) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, SOSScreenActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mSensorManager.unregisterListener(this);
            mNotificationManager.cancel(NOTIFICATION);
            stopSelf();
            startActivity(mIntent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void showNotification() {
        Log.d("SERVICE DEBUG", "Notification Shown");
        CharSequence text = "Started Data Collection";

        // PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        // PendingIntent deleteIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Intent notificationIntent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Sos Service")
                .setContentText("Rider Mode On")
                .setSmallIcon(R.drawable.ic_baseline_directions_bike_24)
                .setContentIntent(pendingIntent)
                .build();

        mNotificationManager.notify(NOTIFICATION, notification);

    }
}

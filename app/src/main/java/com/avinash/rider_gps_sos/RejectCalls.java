package com.avinash.rider_gps_sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;
import java.lang.reflect.Method;

import io.paperdb.Paper;


public class RejectCalls extends BroadcastReceiver {
    String incomingNumber="";
    AudioManager audioManager;
    TelephonyManager telephonyManager;

    public void onReceive(Context context, Intent intent) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (intent.getAction().equals("android.intent.action.PHONE_STATE"))  {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))  {
                incomingNumber =  intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            }
        }

        if(!incomingNumber.equals("")){
            boolean RejectBool=Boolean.getBoolean(Paper.book().read(Prevalent.RCWMKey));
            boolean RideMode=Boolean.getBoolean(Paper.book().read(Prevalent.RiderModeKey));
            if(RejectBool&&RideMode) {


                audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                rejectCall();
                SendMessage(incomingNumber);

            }

        }
    }

    private void SendMessage(String incomingNumber) {
        SmsManager sms=SmsManager.getDefault();
        String msg= Paper.book().read(Prevalent.RejectMsgKey);
        try {
            sms.sendTextMessage(incomingNumber, null, msg, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void rejectCall(){


        try {


            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method method = classTelephony.getDeclaredMethod("getITelephony");

            method.setAccessible(true);

            Object telephonyInterface = method.invoke(telephonyManager);

            Class<?> telephonyInterfaceClass =Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            methodEndCall.invoke(telephonyInterface);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
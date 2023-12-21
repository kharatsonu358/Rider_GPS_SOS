package com.avinash.rider_gps_sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;

import io.paperdb.Paper;

public class SettingActivity extends AppCompatActivity {
    private SwitchCompat RCWN,MNot;
    EditText EtMessage;
    Button Save;
    public boolean RCWNIsChecked=false;
    public boolean MNotIsChecked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        RCWN = findViewById(R.id.SetSwi);
        MNot = findViewById(R.id.SetSwi2);
        Save=findViewById(R.id.btnMessSave);
        EtMessage=findViewById(R.id.etMessage);
        SharedPreferences spSet1=getSharedPreferences("saveRCWN",MODE_PRIVATE);
        SharedPreferences spSet2=getSharedPreferences("saveMNot",MODE_PRIVATE);
        ImageButton IBBack;
        Paper.init(this);

        IBBack= findViewById(R.id.imgBtnSetting);
        IBBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        RCWN.setChecked(spSet1.getBoolean("value",false));
        MNot.setChecked(spSet2.getBoolean("value",false));


        RCWN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Calls Will Be Rejected With Msg Below ", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("saveRCWN",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    String example="false";

                    Paper.book().write("Boolean", example);
                    RCWNIsChecked=true;
                    EtMessage.setFocusable(false);

                } else {
                    Toast.makeText(getApplicationContext(), "Reject call Service OFF", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("saveRCWN",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    RCWNIsChecked=false;
                    EtMessage.setFocusable(true);


                }
            }
        });


        MNot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Notifications Will Be Muted After Rider Mode is Set ON", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("saveMNot",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    Paper.book().write(Prevalent.MuteNotKey, "true");
                    MNotIsChecked=true;


                } else {
                    Toast.makeText(getApplicationContext(), "Notifications May " +
                            "Disturb you while Riding  ", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor =getSharedPreferences("saveMNot",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    MNotIsChecked=false;
                    Paper.book().write(Prevalent.MuteNotKey, "false");



                }
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=EtMessage.getText().toString();
                if(msg.equals(""))
                {
                    Toast.makeText(SettingActivity.this, "Please Edit The Message To Replay on Rejected Calls", Toast.LENGTH_SHORT).show();
                }
                else{
                Paper.book().write(Prevalent.RejectMsgKey,msg);
                    Toast.makeText(SettingActivity.this, "Messsage Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
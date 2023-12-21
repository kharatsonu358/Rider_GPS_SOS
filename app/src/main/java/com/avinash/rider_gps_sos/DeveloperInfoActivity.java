package com.avinash.rider_gps_sos;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DeveloperInfoActivity extends AppCompatActivity {
    ImageButton TBImgDEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);
        TBImgDEv=findViewById(R.id.imgBtnDev);
        TBImgDEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeveloperInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
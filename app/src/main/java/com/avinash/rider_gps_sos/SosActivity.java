package com.avinash.rider_gps_sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class SosActivity extends AppCompatActivity {
    Spinner dropdown;
    ImageButton TBImgsos,sosimg1,sosimg2,sosimg3;
    EditText ConPick1,ConPick2,ConPick3;
    Button btnConPick1,btnConPick2,btnConPick3;
    String MinTime= Paper.book().read(Prevalent.MinTime);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        Paper.init(this);



        dropdown = (Spinner) findViewById(R.id.spinner2);
        String[] items = new String[]{"15", "25", "35","45", "55", "65","75", "85", "95"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=items[position];
                Paper.book().write(Prevalent.MinTime,s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TBImgsos =(ImageButton)findViewById(R.id.imgBtnSos);
        sosimg1 =(ImageButton)findViewById(R.id.Sosimg1);
        sosimg2 =(ImageButton)findViewById(R.id.Sosimg2);
        sosimg3 =(ImageButton)findViewById(R.id.Sosimg3);
        ConPick1=findViewById(R.id.ContactPicker);
        ConPick2=findViewById(R.id.ContactPicker2);
        ConPick3=findViewById(R.id.ContactPicker3);
        btnConPick1=findViewById(R.id.btnConPick1);
        btnConPick2=findViewById(R.id.btnConPick2);
        btnConPick3=findViewById(R.id.btnConPick3);
        MinTime=dropdown.getSelectedItem().toString();
        String regexPhone = "^([6-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])$";
        Pattern ph = Pattern.compile(regexPhone);
        ConPick1.setHint(Paper.book().read(Prevalent.ContactOneKey));
        ConPick2.setHint(Paper.book().read(Prevalent.ContactTwoKey));
        ConPick3.setHint(Paper.book().read(Prevalent.ContactThreeKey));

        TBImgsos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        sosimg1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConPick1.setVisibility(View.VISIBLE);
                ConPick2.setVisibility(View.INVISIBLE);
                ConPick3.setVisibility(View.INVISIBLE);
                btnConPick1.setVisibility(View.VISIBLE);
                btnConPick2.setVisibility(View.INVISIBLE);
                btnConPick3.setVisibility(View.INVISIBLE);




            }
        });
        sosimg2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConPick1.setVisibility(View.INVISIBLE);
                ConPick2.setVisibility(View.VISIBLE);
                ConPick3.setVisibility(View.INVISIBLE);
                btnConPick1.setVisibility(View.INVISIBLE);
                btnConPick2.setVisibility(View.VISIBLE);
                btnConPick3.setVisibility(View.INVISIBLE);


            }
        });
        sosimg3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ConPick1.setVisibility(View.INVISIBLE);
                ConPick2.setVisibility(View.INVISIBLE);
                ConPick3.setVisibility(View.VISIBLE);
                btnConPick1.setVisibility(View.INVISIBLE);
                btnConPick2.setVisibility(View.INVISIBLE);
                btnConPick3.setVisibility(View.VISIBLE);


            }
        });
        btnConPick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con1=ConPick1.getText().toString();
                Matcher ma = ph.matcher(con1);
                if(!ma.matches())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    Paper.book().write(Prevalent.ContactOneKey,con1);
                    ConPick1.setHint(con1);
                    Toast.makeText(getApplicationContext(),"contact Saved"+con1,Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnConPick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con3=ConPick3.getText().toString();
                Matcher ma = ph.matcher(con3);
                if(!ma.matches())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    Paper.book().write(Prevalent.ContactThreeKey,con3);
                    ConPick3.setHint(con3);
                    Toast.makeText(getApplicationContext(),"contact Saved"+con3,Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnConPick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con2=ConPick2.getText().toString();
                Matcher ma = ph.matcher(con2);
                if(!ma.matches())
                {
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    Paper.book().write(Prevalent.ContactTwoKey,con2);
                    ConPick2.setHint(con2);
                    Toast.makeText(getApplicationContext(),"contact Saved"+con2,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
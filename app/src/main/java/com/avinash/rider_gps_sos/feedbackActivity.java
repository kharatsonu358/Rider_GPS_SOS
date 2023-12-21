package com.avinash.rider_gps_sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.avinash.rider_gps_sos.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class feedbackActivity extends AppCompatActivity {
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageButton IBBackfeed;
        RadioButton Rb1,Rb2,Rb3;
        Button FSubmit;
        EditText edtFeedBack;

        IBBackfeed=(ImageButton)findViewById(R.id.imgBtnFeedback);
        Rb1=(RadioButton)findViewById(R.id.RBFeed1) ;
        Rb2=(RadioButton)findViewById(R.id.RBFeed2) ;
        Rb3=(RadioButton)findViewById(R.id.RBFeed3) ;
        edtFeedBack=(EditText)findViewById(R.id.etFeedBack) ;
        FSubmit=(Button)findViewById(R.id.btnFeed);
        loadingBar = new ProgressDialog(this);

        IBBackfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedbackActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        Rb1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton Rb1=(RadioButton)findViewById(R.id.RBFeed1);
                Rb2.setChecked(false);
                Rb3.setChecked(false);
            }
        });



        Rb2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton Rb2=(RadioButton)findViewById(R.id.RBFeed2);
                Rb3.setChecked(false);
                Rb1.setChecked(false);
            }
        });
        Rb3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton Rb3=(RadioButton)findViewById(R.id.RBFeed3);
                Rb2.setChecked(false);
                Rb1.setChecked(false);
            }
        });
        FSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Rb1.isChecked()||Rb2.isChecked()||Rb3.isChecked()) {
                    String feedType="Default";
                    if(Rb1.isChecked())feedType="Concern";
                    else if(Rb2.isChecked())feedType="Request";
                    else if(Rb3.isChecked())feedType="Appreciation";
                    String feedMsg = edtFeedBack.getText().toString();
                    String phone= Paper.book().read(Prevalent.UserPhoneKey);
                    loadingBar.setTitle("Sending FeedBack");
                    loadingBar.setMessage("Please wait, while we are Sending Your Response");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    SubmitFeedback(feedType,feedMsg,phone);

                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please Select type of Feedback",
                            Toast.LENGTH_SHORT).show();
                }

            }


        });
    }

    private void SubmitFeedback(String feedType, String feedMsg,String phone) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Feedback").child(phone).exists())
                {

                    HashMap<String, Object> UserFeedBack = new HashMap<>();
                    UserFeedBack.put("phone", phone);
                    UserFeedBack.put("feedType", feedType);
                    UserFeedBack.put("feedMsg", feedMsg);

                    RootRef.child("Feedback").child(phone).updateChildren(UserFeedBack)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override

                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "your FeedBack  Has been Submitted ", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();



                                        }
                                        else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(getApplicationContext(), "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                else
                {
                    Toast.makeText(getApplicationContext(), "Only One FeedBack is Allowed ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getApplicationContext(), "U Can Send Another When You Received Response From Admin ", Toast.LENGTH_SHORT).show();


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
package com.avinash.rider_gps_sos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.avinash.rider_gps_sos.Model.FeedBackViewHolder;
import com.avinash.rider_gps_sos.Model.FeedBacks;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class AdminDash extends AppCompatActivity {
    private DatabaseReference FeedBackRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.TBAdminDash);
        toolbar.setTitle("Admin Panel");
        setSupportActionBar(toolbar);
        FeedBackRef = FirebaseDatabase.getInstance().getReference().child("Feedback");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<FeedBacks> options =
                new FirebaseRecyclerOptions.Builder<FeedBacks>()
                        .setQuery(FeedBackRef, FeedBacks.class)
                        .build();


        FirebaseRecyclerAdapter<FeedBacks, FeedBackViewHolder> adapter =
                new FirebaseRecyclerAdapter<FeedBacks, FeedBackViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FeedBackViewHolder holder, int position, @NonNull FeedBacks model)
                    {
                        holder.txtFeedBackMsg.setText(model.getFeedBackMsg());
                        holder.TxtPhoneNumber.setText(model.getUserPhone());
                        holder.txtFeedBackMsgType.setText("Type Of FeedBack" + model.getFeedType() + "$");

                    }

                    @NonNull
                    @Override
                    public FeedBackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_list, parent, false);
                        FeedBackViewHolder holder = new FeedBackViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
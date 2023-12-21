package com.avinash.rider_gps_sos.Model;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avinash.rider_gps_sos.R;


public class FeedBackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView TxtPhoneNumber, txtFeedBackMsg, txtFeedBackMsgType;

    public ItemClickListener listner;

    public FeedBackViewHolder(@NonNull View itemView) {
        super(itemView);

        TxtPhoneNumber = (TextView) itemView.findViewById(R.id.UserPhoneNumber);
        txtFeedBackMsg = (TextView) itemView.findViewById(R.id.FeedBackMsg);
        txtFeedBackMsgType = (TextView) itemView.findViewById(R.id.FeedBackMsgType);
    }
    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}

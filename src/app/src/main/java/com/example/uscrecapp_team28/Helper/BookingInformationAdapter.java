package com.example.uscrecapp_team28.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.example.uscrecapp_team28.Activity.BookingInformationActivity;
import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.R;

import java.util.ArrayList;

public class BookingInformationAdapter<MyActivity> extends RecyclerView.Adapter {
    public ArrayList<BookingItem> getmBookingList() {
        return mBookingList;
    }

    private ArrayList<BookingItem> mBookingList;
    Context context;
    private Agent mAgent;
    public static class BookingInformationViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public AppCompatButton cancelButton;
        public RelativeLayout backImage;

        public BookingInformationViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            cancelButton = itemView.findViewById(R.id.CancelButton);
            backImage = (RelativeLayout)itemView.findViewById(R.id.imageBackGround);
        }
    }
    public BookingInformationAdapter(ArrayList<BookingItem> BookingList,Agent agent) {
        mBookingList= BookingList;
        mAgent = agent;
    }
    @Override
    public BookingInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        context = parent.getContext();
        BookingInformationViewHolder evh = new BookingInformationViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookingItem currentItem = mBookingList.get(position);
        ((BookingInformationViewHolder) holder).mTextView1.setText(currentItem.getText1());
        ((BookingInformationViewHolder) holder).mTextView2.setText(currentItem.getText2());
//        System.out.println(currentItem.getmText1());
//        String PACKAGE_NAME = context.getApplicationContext().getPackageName();
        if (currentItem.getText1().equals("Lyon Center")) {
            ((BookingInformationViewHolder) holder).backImage.setBackground(ContextCompat.getDrawable(context, R.drawable.lyon));
        } else {
            ((BookingInformationViewHolder) holder).backImage.setBackground(ContextCompat.getDrawable(context, R.drawable.village));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());
        System.out.print("Current Item:");
        if(currentItem.getText2().substring(0,10).equals(today)){
            ((BookingInformationViewHolder) holder).mTextView2.setTextColor(Color.RED);
        }

        ((BookingInformationViewHolder) holder).cancelButton.setTag(currentItem.getmReservationId());
        ((BookingInformationViewHolder) holder).cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reservation_id = (String)view.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                String msg = "You are going to cancel the reservation.";
                builder.setMessage(msg);
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(context, BookingInformationActivity.class));
                    }
                });
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAgent.cancel_reservation(reservation_id);
                        context.startActivity(new Intent(context,BookingInformationActivity.class));
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }
}

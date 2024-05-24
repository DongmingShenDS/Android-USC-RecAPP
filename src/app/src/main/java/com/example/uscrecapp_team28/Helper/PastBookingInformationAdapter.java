package com.example.uscrecapp_team28.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.R;

import java.util.ArrayList;

public class PastBookingInformationAdapter extends RecyclerView.Adapter{
    public ArrayList<BookingItem> getmBookingList() {
        return mBookingList;
    }

    private ArrayList<BookingItem> mBookingList;
    Context context;
    public static class PastBookingInformationViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public RelativeLayout backImage;

        public PastBookingInformationViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            backImage = (RelativeLayout)itemView.findViewById(R.id.imageBackGroundHistory);
        }
    }
    public PastBookingInformationAdapter(ArrayList<BookingItem> BookingList) {
        mBookingList= BookingList;
    }
    @Override
    public PastBookingInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_booking_item, parent, false);
        context = parent.getContext();
        PastBookingInformationViewHolder evh = new PastBookingInformationViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookingItem currentItem = mBookingList.get(position);
        ((PastBookingInformationViewHolder) holder).mTextView1.setText(currentItem.getText1());
        ((PastBookingInformationViewHolder) holder).mTextView2.setText(currentItem.getText2());
        if (currentItem.getText1().equals("Lyon Center")) {
            ((PastBookingInformationViewHolder) holder).backImage.setBackground(ContextCompat.getDrawable(context, R.drawable.lyon));
        } else {
            ((PastBookingInformationViewHolder) holder).backImage.setBackground(ContextCompat.getDrawable(context, R.drawable.village));
        }
    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }
}

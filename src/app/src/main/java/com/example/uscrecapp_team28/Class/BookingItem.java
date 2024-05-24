package com.example.uscrecapp_team28.Class;

import android.graphics.drawable.Drawable;

public class BookingItem {
    public String getmReservation_id() {
        return mReservation_id;
    }

    private String mReservation_id;
    private String mText1;
    private String mText2;
    private String imgURL;

    public BookingItem(String reservation_id, String text1, String text2) {
        mReservation_id = reservation_id;
        mText1 = text1;
        mText2 = text2;
    }

    public String getmReservationId(){
        return mReservation_id;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setmReservationID(String mReservation_id) {
        this.mReservation_id = mReservation_id;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}

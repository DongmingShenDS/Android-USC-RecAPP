package com.example.uscrecapp_team28.Class;

import java.util.ArrayList;
import java.util.HashMap;

public interface ReservationInterface {
    public HashMap<String,ArrayList<BookingItem>> display_all_reservation_info();
    public void cancel_reservation(String reservation_id);
}

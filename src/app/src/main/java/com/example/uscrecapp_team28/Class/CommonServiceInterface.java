package com.example.uscrecapp_team28.Class;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CommonServiceInterface implements ActivitySwitchingHelperInterface {
    public boolean to_login_page() {
        return false;
    }

    public boolean to_main_page() {
        return false;
    }

    public boolean to_profile_page() {
        return false;
    }

    public boolean to_booking_page() {
        return false;
    }

    public boolean to_summary_page() {
        return false;
    }

    abstract public boolean user_login();
    abstract public boolean check_loggedin();
    abstract public ArrayList<String> view_profile();
    abstract public ArrayList<TimeslotItem> view_all_timeslots(String center_id, String thisdate);
    abstract public Integer make_reservation(String user_id, String timeslot_id, String date_id, int maxcap);
    abstract public boolean cancel_reservation(String reservation_id);
    abstract public Integer join_waitlist(String user_id, String timeslot_id);
    abstract public HashMap<String, ArrayList<BookingItem>> view_all_reservations();
    abstract public void init_info();
    abstract public void logout();
}

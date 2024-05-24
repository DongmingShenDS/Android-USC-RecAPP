package com.example.uscrecapp_team28.Class;

import java.util.ArrayList;

public interface TimeSlotInterface {
    public Integer add_user(String unique_user_id);
    public ArrayList<TimeslotItem> display_all_timeslot_info(String center_id, String thisdate);
    public boolean check_availability(String user_id, String date_id);
    public boolean check_full(String timeslot_id, int max_capacity);
    public Integer join_waitlist(String time_id, String user_id);

}

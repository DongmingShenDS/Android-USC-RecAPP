package com.example.uscrecapp_team28.Class;

import java.util.ArrayList;
import java.util.HashMap;

public class Agent extends CommonServiceInterface {

    private String device_id;
    private String unique_userid;
    private String username;  // get from user
    private String password;  // get from user
    private String real_username;
    private String real_password;
    private String uscid;
    private String photourl;
    private String name;
    private String email;
    private String unique_center_id;  // change when click button
    private String unique_timeslot_id;  // change when click button
    private Profile profile = null;
    private boolean notification_on;
    private Integer notification_time;

    public Agent() {}

    // init everything in user, should be called when user open map page
    @Override
    public void init_info() {
        profile = new Profile(this.device_id);
        ArrayList<String> result = profile.display_profile();
        // System.out.println("RESULT: " + result);
        setUnique_userid(result.get(0));
        setUscid(result.get(1));
        setReal_username(result.get(2));
        setReal_password(result.get(3));
        setPhotourl(result.get(4));
        setName(result.get(5));
        setEmail(result.get(6));
        System.out.println("INIT INFO CALLED !!!!!!!!");
        setNotification_on(profile.getPNotification_on());
        setNotification_time(profile.getPNotification_time());
        // System.out.println("AGENT: SET UNIQUE USERID: " + unique_userid);
    }

    @Override
    public void logout() {
        LogoutInterface a = new Logout(this.device_id);
        if (a.user_logout()) {
            setUnique_userid("");
            setUscid("");
            setReal_username("");
            setReal_password("");
            setPhotourl("");
            setName("");
            setEmail("");
            return;
        }
        logout();
        setUnique_userid("");
        setUscid("");
        setReal_username("");
        setReal_password("");
        setPhotourl("");
        setName("");
        setEmail("");
        // System.out.println("DELETED EVERYTHING ALREADY");
    }

    public String getUnique_userid() {
        return unique_userid;
    }

    public int getNotification_time() {
        return this.notification_time;
    }

    public void setNotification_time(int t) {
        this.notification_time = t;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUnique_center_id() {
        return unique_center_id;
    }

    public String getUnique_timeslot_id() {
        return unique_timeslot_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setUnique_userid(String unique_userid) {
        this.unique_userid = unique_userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUnique_center_id(String unique_center_id) {
        this.unique_center_id = unique_center_id;
    }

    public void setUnique_timeslot_id(String unique_timeslot_id) {
        this.unique_timeslot_id = unique_timeslot_id;
    }

    public void setNotification_on(boolean b) {
        System.out.println("setNotification_on called!!!, value is: " + b);
        this.notification_on = b;
    }

    public boolean getNotification_on() {
        return this.notification_on;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }


    public String getReal_username() {
        return real_username;
    }

    public void setReal_username(String real_username) {
        this.real_username = real_username;
    }

    public String getReal_password() {
        return real_password;
    }

    public void setReal_password(String real_password) {
        this.real_password = real_password;
    }

    public String getUscid() {
        return uscid;
    }

    public void setUscid(String uscid) {
        this.uscid = uscid;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean user_login() {
        LoginCheckerInterface l = new LoginChecker(this.username, this.password, this.device_id);
        boolean correct = l.check_login();
        return correct;
    }

    @Override
    public boolean check_loggedin() {
        // System.out.println("LOGGED IN");
        AuthenticationInterface a = new Authentication(this.device_id);
        boolean already_login = a.if_already_login();
        while (already_login && this.profile == null) {
            init_info();
        }
        return already_login;
    }

    @Override
    public ArrayList<String> view_profile() {
        // make sure the profile is not empty
        if (this.profile == null) {
            init_info();
        }
        return profile.display_profile();
    }

    @Override
    public ArrayList<TimeslotItem> view_all_timeslots(String center_id, String thisdate) {
        TimeSlotInterface t = new TimeSlot();
        return t.display_all_timeslot_info(center_id, thisdate);

    }

    @Override
    public Integer make_reservation(String user_id, String timeslot_id, String date_id, int maxcap) {
        TimeSlot ts = new TimeSlot();
        ts.setUnique_user_id(user_id);
        ts.setUnique_timeslot_id(timeslot_id);
        ts.setDate_id(date_id);
        ts.setMax_capacity(maxcap);
        ts.add_user(user_id);
        return ts.getReserve_result();
    }

    @Override
    public boolean cancel_reservation(String reservation_id) {
        // System.out.println("AGENT: User Unique ID: " + unique_userid);
        ReservationInterface r = new Reservation(unique_userid);
        r.cancel_reservation(reservation_id);
        return true;
    }

    public boolean cancel_reservation_test(String reservation_id,String user_id) {
        // System.out.println("AGENT: User Unique ID: " + unique_userid);
        Reservation r = new Reservation();
        r.setUnique_userid(user_id);
        r.cancel_reservation(reservation_id);
        return true;
    }
    @Override
    public Integer join_waitlist(String user_id, String timeslot_id) {
        TimeSlot ts1 = new TimeSlot();
        ts1.setUnique_user_id(user_id);
        ts1.setUnique_timeslot_id(timeslot_id);
        ts1.join_waitlist(timeslot_id, user_id);
        return ts1.getWait_result();

    }

    @Override
    public HashMap<String, ArrayList<BookingItem>> view_all_reservations() {
        // .out.println("AGENT: User Unique ID: " + unique_userid);
        ReservationInterface r = new Reservation(unique_userid);
        return r.display_all_reservation_info();
    }

    public HashMap<String, ArrayList<BookingItem>> view_all_reservations_test(String user_id) {
        // .out.println("AGENT: User Unique ID: " + unique_userid);
        Reservation r = new Reservation();
        r.setUnique_userid(user_id);
        return r.display_all_reservation_info();
    }
}

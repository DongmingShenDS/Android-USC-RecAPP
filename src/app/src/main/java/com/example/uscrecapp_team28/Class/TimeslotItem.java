package com.example.uscrecapp_team28.Class;

public class TimeslotItem {
    private int timeslot_id;
    private String date;
    private int current_users;
    private int max_cap;
    private String start;
    private String finish;
    private String center_name;
    private String user_id;
    private String center_id;
    private String date_id;

    public TimeslotItem(int timeslot_id, String date, int current_users, int max_cap, String start, String finish, String center_name, String user_id, String center_id, String date_id) {
        this.timeslot_id = timeslot_id;
        this.date = date;
        this.current_users = current_users;
        this.max_cap = max_cap;
        this.start = start;
        this.finish = finish;
        this.center_name = center_name;
        this.user_id = user_id;
        this.center_id = center_id;
        this.date_id = date_id;
    }

    public int getTimeslot_id() {
        return timeslot_id;
    }

    public String getDate() {
        return date;
    }

    public int getCurrent_users() {
        return current_users;
    }

    public int getMax_cap() {
        return max_cap;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
        return finish;
    }

    public String getCenter_name() {
        return center_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setTimeslot_id(int timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCurrent_users(int current_users) {
        this.current_users = current_users;
    }

    public void setMax_cap(int max_cap) {
        this.max_cap = max_cap;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getDate_id() {
        return date_id;
    }

    public void setDate_id(String date_id) {
        this.date_id = date_id;
    }
}

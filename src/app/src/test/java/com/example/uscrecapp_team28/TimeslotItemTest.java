package com.example.uscrecapp_team28;

import static org.junit.Assert.*;

import com.example.uscrecapp_team28.Class.TimeslotItem;

import org.junit.Test;

public class TimeslotItemTest {

    @Test
    public void getTimeslot_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 1;
        assertEquals(i, tsi.getTimeslot_id());

    }

    @Test
    public void getDate() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("2022-04-07", tsi.getDate());
    }

    @Test
    public void getCurrent_users() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 20;
        assertEquals(i, tsi.getCurrent_users());
    }

    @Test
    public void getMax_cap() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 100;
        assertEquals(i, tsi.getMax_cap());
    }

    @Test
    public void getStart() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("08:00", tsi.getStart());
    }

    @Test
    public void getFinish() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("10:00", tsi.getFinish());
    }

    @Test
    public void getCenter_name() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("Lyon Center", tsi.getCenter_name());
    }

    @Test
    public void getUser_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("1", tsi.getUser_id());
    }

    @Test
    public void setTimeslot_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 1;
        assertEquals(i, tsi.getTimeslot_id());
        tsi.setTimeslot_id(2);
        i = 2;
        assertEquals(i, tsi.getTimeslot_id());
    }

    @Test
    public void setDate() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("2022-04-07", tsi.getDate());
        tsi.setDate("2022-04-08");
        assertEquals("2022-04-08", tsi.getDate());

    }

    @Test
    public void setCurrent_users() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 20;
        assertEquals(i, tsi.getCurrent_users());
        tsi.setCurrent_users(21);
        assertEquals(21, tsi.getCurrent_users());
    }

    @Test
    public void setMax_cap() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        int i = 100;
        assertEquals(i, tsi.getMax_cap());
        tsi.setMax_cap(101);
        assertEquals(101, tsi.getMax_cap());
    }

    @Test
    public void setStart() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("08:00", tsi.getStart());
        tsi.setStart("10:00");
        assertEquals("10:00", tsi.getStart());
    }

    @Test
    public void setFinish() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("10:00", tsi.getFinish());
        tsi.setFinish("12:00");
        assertEquals("12:00", tsi.getFinish());
    }

    @Test
    public void setCenter_name() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("Lyon Center", tsi.getCenter_name());
        tsi.setCenter_name("Lyon Center 1");
        assertEquals("Lyon Center 1", tsi.getCenter_name());
    }

    @Test
    public void setUser_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("1", tsi.getUser_id());
        tsi.setUser_id("2");
        assertEquals("2", tsi.getUser_id());
    }

    @Test
    public void getCenter_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("2", tsi.getCenter_id());
    }

    @Test
    public void setCenter_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("2", tsi.getCenter_id());
        tsi.setCenter_id("1");
        assertEquals("1", tsi.getCenter_id());
    }

    @Test
    public void getDate_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("20", tsi.getDate_id());
    }

    @Test
    public void setDate_id() {
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        assertEquals("20", tsi.getDate_id());
        tsi.setDate_id("21");
        assertEquals("21", tsi.getDate_id());
    }
}
package com.example.uscrecapp_team28;

import static org.junit.Assert.*;

import com.example.uscrecapp_team28.Class.TimeSlot;
import com.example.uscrecapp_team28.Class.TimeslotItem;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class TimeSlotTest {



    @Test
    public void add_user() {
        //Available timeslot, make reservation here
        TimeSlot ts = new TimeSlot();
        ts.setUnique_user_id("5");
        ts.setUnique_timeslot_id("4381");
        ts.setMax_capacity(2);
        ts.setDate_id("303");
        //Full timeslot
        TimeSlot ts1 = new TimeSlot();
        ts1.setUnique_user_id("5");
        ts1.setUnique_timeslot_id("4382");
        ts1.setMax_capacity(0);
        ts1.setDate_id("303");
        //Available timeslot, but the user is not available
        TimeSlot ts2 = new TimeSlot();
        ts2.setUnique_user_id("5");
        ts2.setUnique_timeslot_id("4383");
        ts2.setMax_capacity(1);
        ts2.setDate_id("303");
        //Case that the timeslot is full
        int r1 = ts1.add_user("5");
        assertEquals(1, r1);
        //Also not available now
        int r2 = ts.add_user("5");
        assertEquals(0, r2);
        //The user is not available
        int r3 = ts2.add_user("5");
        assertEquals(2, r3);
        try {
            //new MakeBooking(unique_timeslot_id, unique_user_id,date_id, max_capacity).execute().get();


            Thread thread_del = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        System.out.println("enter background");
                        //connect to sql database
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        System.out.println("after connection");
                        String query4 = String.format(
                                "DELETE FROM reservation WHERE user_id = %s AND timeslot_id = %s", "5", "4381");
                        String query5 = String.format(
                                "DELETE FROM availability WHERE user_id = %s AND date_id = %s", "5", "303");
                        int result = s.executeUpdate(query5);
                        int result2 = s.executeUpdate(query4);
                        int del = 0;
                        if(result==1 && result2==1){
                            del =1;
                            assertEquals(1, del);
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_del.start();
            thread_del.join();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @Test
    public void display_all_timeslot_info() {
        TimeSlot ts = new TimeSlot();
        ts.setUnique_user_id("5");
        ArrayList<TimeslotItem> tsi = ts.display_all_timeslot_info("1", "2022-04-06");
        assertEquals(6, tsi.size());
        assertEquals("08:00", tsi.get(0).getStart());
        assertEquals(2, tsi.get(1).getMax_cap());
        TimeSlot ts1 = new TimeSlot();
        ArrayList<TimeslotItem> tsi1 = ts1.display_all_timeslot_info("1", "2023-04-06");
        assertEquals(0, tsi1.size());
        TimeSlot ts2 = new TimeSlot();
        ArrayList<TimeslotItem> tsi2 = ts2.display_all_timeslot_info("3", "2022-04-06");
        assertEquals(0, tsi2.size());


    }

    @Test
    public void check_availability() {
        TimeSlot ts = new TimeSlot();
        assertEquals(true, ts.check_availability("1", "80"));
        assertEquals(false, ts.check_availability("1", "74"));
    }

    @Test
    public void check_full() {
        TimeSlot ts = new TimeSlot();
        assertEquals(true, ts.check_full("1000", 0));
        assertEquals(false, ts.check_full("1000",1 ));
    }

    @Test
    public void join_waitlist() {
        //Already have a reservation in this timeslot
        TimeSlot ts = new TimeSlot();
        ts.setUnique_user_id("5");
        ts.setUnique_timeslot_id("4384");
        ts.setMax_capacity(1);
        ts.setDate_id("304");
        //Already in the waitlist
        TimeSlot ts1 = new TimeSlot();
        ts1.setUnique_user_id("5");
        ts1.setUnique_timeslot_id("4385");
        ts1.setMax_capacity(0);
        ts1.setDate_id("304");
        //Can join the waitlist
        TimeSlot ts2 = new TimeSlot();
        ts2.setUnique_user_id("5");
        ts2.setUnique_timeslot_id("4386");
        ts2.setMax_capacity(0);
        ts2.setDate_id("304");
        //Case that already have a reservation
        int r1 = ts.join_waitlist("4384", "5");
        assertEquals(1, r1);
        //ALready in waitlist
        int r2 = ts1.join_waitlist("4385", "5");
        assertEquals(2, r2);
        //The user joins the waitlist
        int r3 = ts2.join_waitlist("4386", "5");
        assertEquals(0, r3);
        try {
            Thread thread_del_join = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        System.out.println("enter background");
                        //connect to sql database
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        System.out.println("after connection");
                        String query4 = String.format(
                                "DELETE FROM waitlist WHERE user_id = %s AND timeslot_id = %s", "5", "4386");
                        int result2 = s.executeUpdate(query4);
                        int del = 0;
                        if(result2==1){
                            del =1;
                            assertEquals(1, del);
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_del_join.start();
            thread_del_join.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUnique_timeslot_id() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("10",ts.getUnique_timeslot_id());
    }

    @Test
    public void setUnique_timeslot_id() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getUnique_timeslot_id());
        ts.setUnique_timeslot_id("10");
        assertEquals("10", ts.getUnique_timeslot_id());
    }

    @Test
    public void getTimeslot_start_time() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("8:00",ts.getTimeslot_start_time());
    }

    @Test
    public void setTimeslot_start_time() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getTimeslot_start_time());
        ts.setTimeslot_start_time("8:00");
        assertEquals("8:00", ts.getTimeslot_start_time());
    }

    @Test
    public void getTimeslot_finish_time() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("10:00", ts.getTimeslot_finish_time());
    }

    @Test
    public void setTimeslot_finish_time() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getTimeslot_finish_time());
        ts.setTimeslot_finish_time("10:00");
        assertEquals("10:00", ts.getTimeslot_finish_time());
    }

    @Test
    public void getUnique_waitlist_id() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("15", ts.getUnique_waitlist_id());
    }

    @Test
    public void setUnique_waitlist_id() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getUnique_waitlist_id());
        ts.setUnique_waitlist_id("28");
        assertEquals("28", ts.getUnique_waitlist_id());
    }

    @Test
    public void getMax_capacity() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        Integer i = 100;
        assertEquals(i,  ts.getMax_capacity());
    }

    @Test
    public void setMax_capacity() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getMax_capacity());
        ts.setMax_capacity(100);
        Integer i = 100;
        assertEquals(i, ts.getMax_capacity());
    }

    @Test
    public void getCenter_id() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("1", ts.getCenter_id());
    }

    @Test
    public void setCenter_id() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getCenter_id());
        ts.setCenter_id("1");
        assertEquals("1", ts.getCenter_id());

    }

    @Test
    public void getCurr_user() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        Integer i = 20;
        assertEquals(i,  ts.getCurr_user());
    }

    @Test
    public void setCurr_user() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getCurr_user());
        ts.setCurr_user(20);
        Integer i = 20;
        assertEquals(i, ts.getCurr_user());
    }

    @Test
    public void getSlotList() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals(0, ts.getSlotList().size());
    }

    @Test
    public void setSlotList() {
        ArrayList<TimeslotItem> sl = new ArrayList<TimeslotItem>();
        TimeSlot ts = new TimeSlot();
        assertEquals(0, ts.getSlotList().size());
        TimeslotItem tsi =  new TimeslotItem(1, "2022-04-07", 20, 100, "08:00", "10:00", "Lyon Center", "1", "2", "20");
        sl.add(tsi);
        ts.setSlotList(sl);
        assertEquals(1, ts.getSlotList().size());
        assertEquals(1, ts.getSlotList().get(0).getTimeslot_id());

    }

    @Test
    public void getUnique_user_id() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("1", ts.getUnique_user_id());
    }

    @Test
    public void setUnique_user_id() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getUnique_user_id());
        ts.setUnique_user_id("2");
        assertEquals("2", ts.getUnique_user_id());
    }

    @Test
    public void getDate_id() {
        TimeSlot ts = new TimeSlot("10", "8:00", "10:00", "15", 100, "1", 20, "1", "20");
        assertEquals("20", ts.getDate_id());
    }

    @Test
    public void setDate_id() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getDate_id());
        ts.setDate_id("20");
        assertEquals("20", ts.getDate_id());
    }

    @Test
    public void getIsavail() {
        TimeSlot ts = new TimeSlot();
        assertEquals(true, ts.getIsavail());
    }

    @Test
    public void setIsavail() {
        TimeSlot ts = new TimeSlot();
        assertEquals(true, ts.getIsavail());
        ts.setIsavail(false);
        assertEquals(false, ts.getIsavail());
    }

    @Test
    public void getIsfull() {
        TimeSlot ts = new TimeSlot();
        assertEquals(false, ts.getIsfull());
    }

    @Test
    public void setIsfull() {
        TimeSlot ts = new TimeSlot();
        assertEquals(false, ts.getIsfull());
        ts.setIsfull(true);
        assertEquals(true, ts.getIsfull());
    }

    @Test
    public void getReserve_result() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getReserve_result());
    }

    @Test
    public void setReserve_result() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getReserve_result());
        ts.setReserve_result(1);
        Integer i = 1;
        assertEquals(i, ts.getReserve_result());
    }

    @Test
    public void getWait_result() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getWait_result());
    }

    @Test
    public void setWait_result() {
        TimeSlot ts = new TimeSlot();
        assertEquals(null, ts.getWait_result());
        ts.setWait_result(1);
        Integer i = 1;
        assertEquals(i, ts.getWait_result());


    }
}
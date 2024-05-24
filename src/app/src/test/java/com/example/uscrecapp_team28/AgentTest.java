package com.example.uscrecapp_team28;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.Reservation;
import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.Class.TimeslotItem;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.HashMap;

public class AgentTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testInit_info() {
        // a trivial version
        Agent e = new Agent();
        e.init_info();
        assertEquals(null, e.getUnique_userid());
        assertEquals(null, e.getUscid());
        assertEquals(null, e.getReal_username());
        assertEquals(null, e.getReal_password());
        assertEquals(null, e.getPhotourl());
        assertEquals(null, e.getName());
        assertEquals(null, e.getEmail());
        // a real version
        Agent a = new Agent();
        a.setDevice_id("deviceid");
        // before init_info()
        assertEquals(null, a.getUnique_userid());
        assertEquals(null, a.getUscid());
        assertEquals(null, a.getReal_username());
        assertEquals(null, a.getReal_password());
        assertEquals(null, a.getPhotourl());
        assertEquals(null, a.getName());
        assertEquals(null, a.getEmail());
        // after init_info()
        a.init_info();
        assertEquals("5", a.getUnique_userid());
        assertEquals("uscid", a.getUscid());
        assertEquals("test", a.getReal_username());
        assertEquals("test", a.getReal_password());
        assertEquals("https://media-exp1.licdn.com/dms/image/C5603AQFxpC5CYYULNA/profile-displayphoto-shrink_800_800/0/1624353277071?e=1652918400&v=beta&t=84ukoO8jlXa6Di4JE2VbtMd-klLCCcQK6aoQT1G5GwY", a.getPhotourl());
        assertEquals("test_name", a.getName());
        assertEquals("test_email", a.getEmail());
    }

    public void testLogout() {
        Agent a = new Agent();
        a.setUsername("test");
        a.setPassword("test");
        a.setDevice_id("deviceid");
        a.user_login();
        a.logout();
        assertEquals("deviceid", a.getDevice_id());
        // assign diviceid back to the user to not modify the database
        a.setUsername("test");
        a.setPassword("test");
        a.setDevice_id("deviceid");
        a.user_login();
    }

    public void testGetUnique_userid() {
        Agent a = new Agent();
        a.setUnique_userid("id");
        assertEquals("id", a.getUnique_userid());
    }

    public void testGetUsername() {
        Agent a = new Agent();
        a.setUsername("un");
        assertEquals("un", a.getUsername());
    }

    public void testGetPassword() {
        Agent a = new Agent();
        a.setPassword("pw");
        assertEquals("pw", a.getPassword());
    }

    public void testGetUnique_center_id() {
        Agent a = new Agent();
        a.setUnique_center_id("cid");
        assertEquals("cid", a.getUnique_center_id());
    }

    public void testGetUnique_timeslot_id() {
        Agent a = new Agent();
        a.setUnique_timeslot_id("tid");
        assertEquals("tid", a.getUnique_timeslot_id());
    }

    public void testGetDevice_id() {
        Agent a = new Agent();
        a.setDevice_id("did");
        assertEquals("did", a.getDevice_id());
    }

    public void testSetUnique_userid() {
        Agent a = new Agent();
        a.setUnique_userid("id");
        assertEquals("id", a.getUnique_userid());
    }

    public void testSetUsername() {
        Agent a = new Agent();
        a.setUsername("un");
        assertEquals("un", a.getUsername());
    }

    public void testSetPassword() {
        Agent a = new Agent();
        a.setPassword("pw");
        assertEquals("pw", a.getPassword());
    }

    public void testSetUnique_center_id() {
        Agent a = new Agent();
        a.setUnique_center_id("cid");
        assertEquals("cid", a.getUnique_center_id());
    }

    public void testSetUnique_timeslot_id() {
        Agent a = new Agent();
        a.setUnique_timeslot_id("tid");
        assertEquals("tid", a.getUnique_timeslot_id());
    }

    public void testSetDevice_id() {
        Agent a = new Agent();
        a.setDevice_id("did");
        assertEquals("did", a.getDevice_id());
    }

    public void testGetReal_username() {
        Agent a = new Agent();
        a.setReal_username("run");
        assertEquals("run", a.getReal_username());
    }

    public void testSetReal_username() {
        Agent a = new Agent();
        a.setReal_username("run");
        assertEquals("run", a.getReal_username());
    }

    public void testGetReal_password() {
        Agent a = new Agent();
        a.setReal_password("rpw");
        assertEquals("rpw", a.getReal_password());
    }

    public void testSetReal_password() {
        Agent a = new Agent();
        a.setReal_password("rpw");
        assertEquals("rpw", a.getReal_password());
    }

    public void testGetUscid() {
        Agent a = new Agent();
        a.setUscid("uscid");
        assertEquals("uscid", a.getUscid());
    }

    public void testSetUscid() {
        Agent a = new Agent();
        a.setUscid("uscid");
        assertEquals("uscid", a.getUscid());
    }

    public void testGetPhotourl() {
        Agent a = new Agent();
        a.setPhotourl("url");
        assertEquals("url", a.getPhotourl());
    }

    public void testSetPhotourl() {
        Agent a = new Agent();
        a.setPhotourl("url");
        assertEquals("url", a.getPhotourl());
    }

    public void testGetName() {
        Agent a = new Agent();
        a.setName("name");
        assertEquals("name", a.getName());
    }

    public void testSetName() {
        Agent a = new Agent();
        a.setName("name");
        assertEquals("name", a.getName());
    }

    public void testGetEmail() {
        Agent a = new Agent();
        a.setEmail("email");
        assertEquals("email", a.getEmail());
    }

    public void testSetEmail() {
        Agent a = new Agent();
        a.setEmail("email");
        assertEquals("email", a.getEmail());
    }

    public void testUser_login() {
        Agent a = new Agent();
        a.setUsername("test");
        a.setPassword("test");
        a.setDevice_id("deviceid");
        assertEquals(true, a.user_login());
        Agent b = new Agent();
        assertEquals(false, b.user_login());
    }

    public void testCheck_loggedin() {
        Agent a = new Agent();
        assertEquals(false, a.check_loggedin());
        Agent b = new Agent();
        b.setDevice_id("deviceid");
        assertEquals(true, b.check_loggedin());
        b.setDevice_id("another_not_exist");
        assertEquals(false, b.check_loggedin());
    }

    public void testView_profile() {
        // a trivial version
        Agent e = new Agent();
        ArrayList<String> e_arr = new ArrayList<String>();
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        assertEquals(e_arr, e.view_profile());
        // a trivial version
        Agent t = new Agent();
        t.setDevice_id("DNE");
        assertEquals(e_arr, e.view_profile());
        // a real version
        Agent a = new Agent();
        a.setDevice_id("deviceid");
        ArrayList<String> display = new ArrayList<String>();
        display.add("5");
        display.add("uscid");
        display.add("test");
        display.add("test");
        display.add("https://media-exp1.licdn.com/dms/image/C5603AQFxpC5CYYULNA/profile-displayphoto-shrink_800_800/0/1624353277071?e=1652918400&v=beta&t=84ukoO8jlXa6Di4JE2VbtMd-klLCCcQK6aoQT1G5GwY");
        display.add("test_name");
        display.add("test_email");
        display.add("deviceid");
        ArrayList<String> real = a.view_profile();
        assertEquals(display, real);
    }

    public void testView_all_timeslots() {
        Agent ag = new Agent();
        ag.setDevice_id("deviceid");
        ag.init_info();
        ArrayList<TimeslotItem> tsi = new ArrayList<TimeslotItem>();
        tsi = ag.view_all_timeslots("1", "2022-12-12");
        assertEquals(6, tsi.size());
        assertEquals("10:00", tsi.get(1).getStart());
    }

    public void testMake_reservation() {
        Agent ag = new Agent();
        ag.setDevice_id("deviceid");
        ag.init_info();

        int r1 = ag.make_reservation("5","4382", "303", 0);
        int r0 = ag.make_reservation("5","4381", "303", 2);
        int r2 = ag.make_reservation("5","4383", "303", 1);
        assertEquals(1, r1);
        assertEquals(0, r0);
        assertEquals(2, r2);
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

    public void testJoin_waitlist() {
        Agent ag = new Agent();
        ag.setDevice_id("deviceid");
        ag.init_info();
        int r1 = ag.join_waitlist("5", "4384");
        int r2 = ag.join_waitlist("5", "4385");
        int r0 = ag.join_waitlist("5", "4386");
        assertEquals(1, r1);
        assertEquals(2, r2);
        assertEquals(0, r0);
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

    public void testCancel_reservation() {
        Agent agent = new Agent();
        String user_id = "6";
        String timeslot_id = "104";
        String date_id = "104";
        agent.make_reservation(user_id,timeslot_id,date_id,2);
        //cancel the reservation and find if the reservation exists
        Reservation reservation = new Reservation();
        reservation.setUnique_userid(user_id);
        assertEquals(1,reservation.get_reservationId_by_timeslot_and_user(timeslot_id).size());
        String reservation_id = reservation.get_reservationId_by_timeslot_and_user(timeslot_id).get(0);
        agent.cancel_reservation_test(reservation_id,"6");
        //test if it contains the reservation id
        assertEquals(0,reservation.get_reservationId_by_timeslot_and_user(timeslot_id).size());
        //test if it contains the reservation id in the availability table
        assertEquals(0,reservation.check_availability_table_for_reservation().size());

    }

    public void testView_all_reservations() {
        Agent a = new Agent();
        HashMap<String, ArrayList<BookingItem>> m = a.view_all_reservations_test("6");
        System.out.println(m);
        assertEquals(2,m.get("future").size());
        assertEquals(1,m.get("history").size());
        assertEquals("128",m.get("history").get(0).getmReservationId());
        //contain reservation id 104 and 105
        boolean contain129 = false;
        boolean contain130 = false;
        for(BookingItem i:m.get("future")){
            if(i.getmReservationId().equals("129")){
                contain129 = true;
            }else if(i.getmReservationId().equals("130")){
                contain130 = true;
            }
        }
        assertTrue(contain129);
        assertTrue(contain130);
        //test for nonexist user
        HashMap<String, ArrayList<BookingItem>>  nonexistM = a.view_all_reservations_test("-1");
        System.out.println( nonexistM);
        assertEquals(0, nonexistM.get("future").size());
        assertEquals(0, nonexistM.get("history").size());
    }

}
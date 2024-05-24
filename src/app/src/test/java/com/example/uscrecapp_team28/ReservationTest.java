package com.example.uscrecapp_team28;

import static org.junit.Assert.*;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.Reservation;
import com.example.uscrecapp_team28.Class.BookingItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ReservationTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUnique_userid() {
        Reservation r = new Reservation();
        r.setUnique_userid("uid1");
        assertEquals("uid1", r.getUnique_userid());
    }

    @Test
    public void setUnique_userid() {
        Reservation r = new Reservation();
        r.setUnique_userid("uid2");
        assertEquals("uid2", r.getUnique_userid());
    }

    @Test
    public void setReservation_id() {
        Reservation r = new Reservation();
        r.setReservation_id("rid1");
        assertEquals("rid1", r.getReservation_id());
    }

    @Test
    public void getReservation_id() {
        Reservation r = new Reservation();
        r.setReservation_id("rid2");
        assertEquals("rid2", r.getReservation_id());
    }

    //display all reservation information of a user. make sure the past and future information are correct
    @Test
    public void display_all_reservation_info() {
        //test all reservation information for a user
        Reservation reservation = new Reservation();
        String user_id = "6";
        reservation.setUnique_userid(user_id);
        HashMap<String, ArrayList<BookingItem>>  m = reservation.display_all_reservation_info();
        System.out.println(m);
        assertEquals(1,m.get("future").size());
        assertEquals(2,m.get("history").size());
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
        assertFalse(contain129);
        assertTrue(contain130);
    }

    @Test
    public void display_all_reservation_info_for_nonexist_user(){
        Reservation reservation = new Reservation();
        reservation.setUnique_userid("-1");
        HashMap<String, ArrayList<BookingItem>>  m = reservation.display_all_reservation_info();
        System.out.println(m);
        assertEquals(0,m.get("future").size());
        assertEquals(0,m.get("history").size());
    }

    //add a reservation and test to cancel the reservation
    @Test
    public void cancel_reservation_reservation_table() {
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
        reservation.cancel_reservation(reservation_id);
        //test if it contains the reservation id
        assertEquals(0,reservation.get_reservationId_by_timeslot_and_user(timeslot_id).size());
    }

    @Test
    public void cancel_reservation_availability_table(){
        Agent agent = new Agent();
        String user_id = "6";
        String timeslot_id = "104";
        String date_id = "104";
        agent.make_reservation(user_id,timeslot_id,date_id,2);
        //cancel the reservation and find if the reservation exists
        Reservation reservation = new Reservation();
        reservation.setUnique_userid(user_id);
        assertEquals(1,reservation.get_reservationId_by_timeslot_and_user(timeslot_id).size());
        assertEquals(1,reservation.check_availability_table_for_reservation().size());
        String reservation_id = reservation.get_reservationId_by_timeslot_and_user(timeslot_id).get(0);
        reservation.cancel_reservation(reservation_id);
        //test if it contains the reservation id in the availability table
        assertEquals(0,reservation.check_availability_table_for_reservation().size());
    }
}
package com.example.uscrecapp_team28.Class;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Reservation implements ReservationInterface{

    String unique_userid;
    FirebaseFirestore db;
    String reservation_id;

    public Reservation(){
    };

    public Reservation(String uid) {
        this.unique_userid = uid;
        db = FirebaseFirestore.getInstance();
    }

    public String getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getUnique_userid() {
        return unique_userid;
    }
    public ArrayList<String> get_reservationId_by_timeslot_and_user(String timeslot_id){
        final ArrayList<String> res = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //connect to sql database
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                    Statement s = connection.createStatement();
                    String query = String.format("SELECT reservation.reservation_id from reservation where user_id=%s and timeslot_id=%s;",getUnique_userid(),timeslot_id);
                    ResultSet result = s.executeQuery(query);
                    while(result.next()){
                        res.add(result.getString("reservation_id"));
                    }

                } catch (Exception e) { }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<String> check_availability_table_for_reservation(){
        final ArrayList<String> res = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //connect to sql database
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                    Statement s = connection.createStatement();
                    String query = String.format("SELECT user_id from availability where user_id=%s;",getUnique_userid());
                    ResultSet result = s.executeQuery(query);
                    while(result.next()){
                        res.add(result.getString("user_id"));
                    }

                } catch (Exception e) {

                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    public void setUnique_userid(String unique_userid) {
        this.unique_userid = unique_userid;
    }
    private final HashMap<String, ArrayList<BookingItem>> listMap = new HashMap<String,ArrayList<BookingItem>>(){{
        put("future",new ArrayList<>());
        put("history", new ArrayList<>());
    }};
    @Override
    // return all necessary information to display all the booking information in app
    public HashMap display_all_reservation_info() {
        Thread ThreadGetBookingInformation = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //connect to sql database
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                    Statement s = connection.createStatement();
//              String userId = sp1.getString("user_id",null);
                    // String userId = "0";
                    String userId = getUnique_userid();
                    // System.out.println("User Unique ID: " + userId);
                    //query the database for all user's reservation
                    String query = String.format("SELECT \n" +
                            "\treservation.reservation_id,datelist.date,center.name AS center_name,timeslot.start_time,timeslot.finish_time \n" +
                            "FROM reservation \n" +
                            "JOIN user \n" +
                            "JOIN timeslot \n" +
                            "JOIN center\n" +
                            "JOIN datelist\n" +
                            "ON user.user_id=%s \n" +
                            "\tAND reservation.user_id=user.user_id \n" +
                            "    AND reservation.timeslot_id=timeslot.timeslot_id\n" +
                            "\tAND center.center_id=timeslot.center_id\n" +
                            "    AND datelist.date_id=timeslot.date_id ORDER BY datelist.date_id;", userId);
                    ResultSet result = s.executeQuery(query);
                    Date cur_time = new Date();
                    while (result.next()){
                        //compare with current date and time
                        Date timeslot_time =  new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                                .parse(result.getString("date")+" "+result.getString("finish_time"));
                        //if the endtime is after the current time, future booking
                        if(timeslot_time.compareTo(cur_time)>0){
                            listMap.get("future").add(new BookingItem(result.getString("reservation_id"),result.getString("center_name"),result.getString("date")+" "+result.getString("start_time")));
                        }else{
                            listMap.get("history").add(new BookingItem(result.getString("reservation_id"),result.getString("center_name"),result.getString("date")+" "+result.getString("start_time")));
                        }
                    }
                    connection.close();
                } catch (Exception e) {
                    // System.out.println("Exception");
                }
            }
        });
        ThreadGetBookingInformation.start();
        try {
            ThreadGetBookingInformation.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listMap;
    }

    @Override
    public void cancel_reservation(String reservation_id) {
        //cancel the reservation from sql database
        this.reservation_id = reservation_id;
        final Date[] date = new Date[1];
        final String[] start_time = new String[1];
        Thread ThreadCancelBooking = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    // System.out.println("enter background");
                    //connect to sql database
                    Class.forName("com.mysql.jdbc.Driver");
                    String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                    Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                    Statement s = connection.createStatement();
                    // System.out.println("after connection");
                    //get the timeid
                    String getTimeQuery = String.format("SELECT timeslot_id FROM reservation\n" +
                            "\tWHERE reservation.reservation_id=%s;",reservation_id);
                    ResultSet timeResult = s.executeQuery(getTimeQuery);
                    int time_id=-1;
                    while(timeResult.next()){
                        time_id = timeResult.getInt("timeslot_id");
                    }
                    //get the date
                    String getDateQuery = String.format("SELECT date_id, start_time FROM timeslot \n" +
                            "\tWHERE timeslot_id=%s;",time_id);
                    ResultSet dateResult = s.executeQuery(getDateQuery);
                    int date_id=-1;
                    while(dateResult.next()){
                        date_id = dateResult.getInt("date_id");
                        start_time[0] = dateResult.getString("start_time");
                    }
                    String getDateStringQuery = String.format("SELECT date FROM datelist WHERE date_id=%s",date_id);
                    dateResult = s.executeQuery(getDateStringQuery);
                    while(dateResult.next()){
                        date[0] = dateResult.getDate("date");
                    }
                    //update the database by delete the reservation
                    String query = String.format(
                            "DELETE from reservation\n" +
                                    "\tWHERE reservation.reservation_id=%s;", reservation_id);

                    s.executeUpdate(query);
                    // System.out.println(String.format("after execution of query delete %s from database",reservation_id));
                    String deleteAvaiQuery = String.format("DELETE FROM availability\n" +
                            "\tWHERE user_id=%s AND date_id=%s;",getUnique_userid(),date_id);
                    s.executeUpdate(deleteAvaiQuery);
                    String lastcheck = String.format(
                            "SELECT COUNT(reservation.timeslot_id) AS count FROM reservation WHERE reservation.timeslot_id = %s;",  time_id);
                    ResultSet result2 = s.executeQuery(lastcheck);
                    int count = 0;
                    while(result2.next()){
                        count = result2.getInt("count");
                    }
                    //compare the count from recreational_center copacity
                    int capacity = 0;
                    String capacityQuery = String.format("SELECT max_capacity FROM timeslot\n" +
                            "\tWHERE timeslot_id=%s;",time_id);
                    ResultSet capacityResult = s.executeQuery(capacityQuery);
                    while(capacityResult.next()){
                        capacity = capacityResult.getInt("max_capacity");
                    }
                    if(count<capacity){
                        //notify all other users in the waitlist
                        String waitingUserQuery = String.format("SELECT user_id FROM waitlist\n" +
                                "\tWHERE timeslot_id=%s;",time_id);
                        ResultSet waitingUserResult = s.executeQuery(waitingUserQuery);
                        while(waitingUserResult.next()){
                            String user_id = waitingUserResult.getString("user_id");
                            //add the user_id to the firedb document

                            db.collection("User").document("User").update(user_id,date[0].toString()+" " + start_time[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });
                        }
                    }
                    connection.close();
                } catch (Exception e){
                    // System.out.println("Exception");
                    e.printStackTrace();
                }
            }
        });
        ThreadCancelBooking.start();
        try {
            ThreadCancelBooking.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //else do not need to notify

    }
}

package com.example.uscrecapp_team28.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TimeSlot implements TimeSlotInterface{
    String unique_timeslot_id;
    String timeslot_start_time;
    String timeslot_finish_time;
    String unique_waitlist_id;
    String unique_user_id;
    String date_id;
    String center_id;
    Integer max_capacity;
    Integer curr_user;
    Boolean isavail = true;
    Boolean isfull = false;
    Integer reserve_result;
    Integer wait_result;

    private ArrayList<TimeslotItem> slotList = new ArrayList<TimeslotItem>();


    public Integer getWait_result() {
        return wait_result;
    }

    public void setWait_result(Integer wait_result) {
        this.wait_result = wait_result;
    }

    public TimeSlot(){};

    public TimeSlot(String unique_timeslot_id, String timeslot_start_time, String timeslot_finish_time, String unique_waitlist_id, int max_capacity, String center_id, Integer curr_user, String unique_user_id, String date_id) {
        this.unique_timeslot_id = unique_timeslot_id;
        this.timeslot_start_time = timeslot_start_time;
        this.timeslot_finish_time = timeslot_finish_time;
        this.unique_waitlist_id = unique_waitlist_id;
        this.max_capacity = max_capacity;
        this.center_id = center_id;
        this.curr_user = curr_user;
        this.date_id = date_id;
        this.unique_user_id = unique_user_id;
    }

    /**
     * Add the user to this timeslot
     * @param unique_user_id
     * @return an integer indicating the three possible results of making reservation
     */
    public Integer add_user(String unique_user_id){
        if(!check_full(unique_timeslot_id, max_capacity) && check_availability(unique_user_id, date_id)){
            try {
                //new MakeBooking(unique_timeslot_id, unique_user_id,date_id, max_capacity).execute().get();

                Thread thread_make = new Thread(new Runnable() {
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
                            //Add the user to the reservation list of the timeslot, and make the user unavailable today.
                            String query = String.format(
                                    "INSERT INTO reservation(user_id, timeslot_id) VALUES (%d, %s);", Integer.parseInt(unique_user_id), unique_timeslot_id);

                            String query2 = String.format(
                                    "INSERT INTO availability(user_id, date_id) VALUES (%d, %s);", Integer.parseInt(unique_user_id), date_id);
                            String query3 = String.format(
                                    "SELECT * FROM waitlist WHERE user_id = %s AND timeslot_id = %s", Integer.parseInt(unique_user_id), unique_timeslot_id);
                            System.out.println(query);
                            int result = s.executeUpdate(query);
                            int result3 = s.executeUpdate(query2);
                            ResultSet result4 = s.executeQuery(query3);
                            if(result == 1 && result3 ==1){
                                while(result4.next()){
                                    //Check if the user is in waitlist. If so, after the user makes the reservation, delete it from waitlist
                                    String query4 = String.format(
                                            "DELETE FROM waitlist WHERE user_id = %s AND timeslot_id = %s", Integer.parseInt(unique_user_id), unique_timeslot_id);
                                    int result5 = s.executeUpdate(query4);
                                    if(result5==1){
                                        System.out.println("Reservation Insertion Succeeds");
                                        break;
                                    }
                                }
                            }
                            connection.close();

                        } catch (Exception e){
                            System.out.println("Exception");
                            e.printStackTrace();
                        }
                    }
                });
                thread_make.start();
                thread_make.join();

                //Success Reservation
                reserve_result = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if(check_full(unique_timeslot_id, max_capacity)){
                //The time slot is full just now (page not refresh yet)
                reserve_result = 1;
            }
            else if(!check_availability(unique_user_id, date_id)){
                //The user is not available (already reserved)
                reserve_result = 2;
            }
        }
        return reserve_result;
    }



    /**
     * Return all the timeslots' information given the date and the center_id
     * @param center_id
     * @param thisdate
     * @return A list of timeslotItem storing information of the timeslot
     */
    @Override
    public ArrayList<TimeslotItem> display_all_timeslot_info(String center_id, String thisdate) {
        setCenter_id(center_id);
        try {
            //new GetTimeslotInformation("1", thisdate).execute().get();
            Thread thread_display = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        String centerId = center_id;
                        System.out.println(centerId);
                        System.out.println(thisdate);
                        //Display the information of a timeslot
                        String query = String.format("SELECT \n" +
                                "\ttimeslot.timeslot_id AS timeslot_id,timeslot.max_capacity AS maxcap,timeslot.date_id, center.name AS center_name,timeslot.start_time,timeslot.finish_time, " +
                                "COUNT(reservation.timeslot_id) AS currentusers\n" +
                                "FROM timeslot \n" +
                                "JOIN center\n" +
                                "ON center.center_id = timeslot.center_id \n" +
                                "LEFT JOIN reservation \n" +
                                "\tON reservation.timeslot_id=timeslot.timeslot_id \n" +
                                "JOIN datelist\n" +
                                " ON datelist.date_id=timeslot.date_id WHERE datelist.date = '%s' AND center.center_id=%s " +
                                "GROUP BY(timeslot.timeslot_id) ;", thisdate, centerId);
                        System.out.println(query);
                        ResultSet result = s.executeQuery(query);
                        while (result.next()){
                            slotList.add(new TimeslotItem(Integer.parseInt(result.getString("timeslot_id")),thisdate,Integer.parseInt(result.getString("currentusers")), Integer.parseInt(result.getString("maxcap")),
                                    result.getString("start_time"), result.getString("finish_time"), result.getString("center_name"), unique_user_id, centerId, result.getString("date_id")));
                        }
                        System.out.println("Get all timeslots");
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_display.start();
            thread_display.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slotList;
    }

    /**
     * Call the query to check availability of the user on a specific date
     * @param user_id
     * @param date_id
     * @return whether the user is available or not
     */
    @Override
    public boolean check_availability(String user_id, String date_id) {
        setUnique_user_id(user_id);
        setDate_id(date_id);
        try {
            Thread thread_avail = new Thread(new Runnable() {
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
                        //query the database for all user's reservation
                        String judge = String.format(
                                "SELECT * FROM availability WHERE user_id = %s AND date_id = %s ;", user_id, date_id);
                        ResultSet result1 = s.executeQuery(judge);
                        Boolean flag1 = true;
                        while(result1.next()){
                            flag1 = false;
                            break;
                        }
                        if(flag1 == true){
                            isavail = true;

                        }
                        else{
                            isavail = false;
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_avail.start();
            thread_avail.join();
            //new CheckAvailability(user_id, date_id).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isavail;
    }

    /**
     * Check if the timeslot is full
     * @param timeslot_id
     * @param max_capacity
     * @return
     */
    public boolean check_full(String timeslot_id, int max_capacity){
        setUnique_timeslot_id(timeslot_id);
        setMax_capacity(max_capacity);
        try {
            Thread thread_full = new Thread(new Runnable() {
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
                        //query the database for the number of reservations in this timeslot
                        String judge = String.format(
                                "SELECT COUNT(reservation.timeslot_id) AS count FROM reservation WHERE reservation.timeslot_id = %s;",  timeslot_id);
                        ResultSet result2 = s.executeQuery(judge);
                        int count = 0;
                        while(result2.next()){
                            count = result2.getInt("count");
                        }
                        int remaining = max_capacity - count;
                        //No spots remaining
                        if(remaining <= 0){
                            isfull = true;
                            System.out.println("No spots left!!");
                        }
                        else{
                            isfull = false;
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_full.start();
            thread_full.join();
            //new CheckAvailability(user_id, date_id).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isfull;
    }



    /**
     * Join the user to the timeslot's waitlist
     * @param time_id
     * @param user_id
     * @return
     */
    public Integer join_waitlist(String time_id, String user_id){
        try {
            //new WaitBooking(time_id, user_id).execute().get();
            Thread thread_wait = new Thread(new Runnable() {
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
                        //Check if the user already has a reservation for this timeslot
                        String checkquery = String.format(
                                "SELECT * FROM reservation WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
                        ResultSet result1 = s.executeQuery(checkquery);
                        Boolean b1 = true;
                        Boolean b2 = true;
                        while(result1.next()){
                            b1 = false;
                            //Already made a reservation
                            wait_result = 1;
                            //Show a messagebox
                        }
                        //Check if the user is already in waitlist
                        String checkquery2 = String.format(
                                "SELECT * FROM waitlist WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
                        ResultSet result2 = s.executeQuery(checkquery2);
                        while(result2.next()){
                            b2 = false;
                            //The user is already in waitlist
                            wait_result = 2;
                            //Show a message box
                        }
                        if(b1 && b2){
                            String query = String.format(
                                    "INSERT INTO waitlist(user_id, timeslot_id) VALUES (%s, %s);", user_id, time_id);
                            int result = s.executeUpdate(query);
                            if(result == 1){
                                //Join waitlist successfully
                                wait_result = 0;
                                System.out.println("Waitlist Insertion Succeeds");
                            }
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_wait.start();
            thread_wait.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wait_result;

    }

    /**
     * A class to execute the query to add user to the waitlist
     */
//    class WaitBooking extends AsyncTask<Void, Void, Void> {
//        String time_id;
//        String user_id;
//        //set parameter for async function
//        public WaitBooking(String time_id, String user_id){
//            this.time_id = time_id;
//            this.user_id = user_id;
//        }
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                System.out.println("enter background");
//                //connect to sql database
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                System.out.println("after connection");
//                //Check if the user already has a reservation for this timeslot
//                String checkquery = String.format(
//                        "SELECT * FROM reservation WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
//                ResultSet result1 = s.executeQuery(checkquery);
//                Boolean b1 = true;
//                Boolean b2 = true;
//                while(result1.next()){
//                    b1 = false;
//                    //Already made a reservation
//                    wait_result = 1;
//                    //Show a messagebox
//                }
//                //Check if the user is already in waitlist
//                String checkquery2 = String.format(
//                        "SELECT * FROM waitlist WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
//                ResultSet result2 = s.executeQuery(checkquery2);
//                while(result2.next()){
//                    b2 = false;
//                    //The user is already in waitlist
//                    wait_result = 2;
//                    //Show a message box
//                }
//                if(b1 && b2){
//                    String query = String.format(
//                            "INSERT INTO waitlist(user_id, timeslot_id) VALUES (%s, %s);", user_id, time_id);
//                    int result = s.executeUpdate(query);
//                    if(result == 1){
//                        //Join waitlist successfully
//                        wait_result = 0;
//                        System.out.println("Waitlist Insertion Succeeds");
//                    }
//                }
//
//            } catch (Exception e){
//                System.out.println("Exception");
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//        }
//    }

    /**
     * Class to check if a timeslot is full
     */
//    class CheckFull extends AsyncTask<Void, Void, Void> {
//        String timeslot_id;
//        int maxcap;
//        //set parameter for async function
//        public CheckFull(String timeslot_id, int maxcap){
//            this.timeslot_id = timeslot_id;
//            this.maxcap = maxcap;
//        }
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                System.out.println("enter background");
//                //connect to sql database
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                System.out.println("after connection");
//                //query the database for the number of reservations in this timeslot
//                String judge = String.format(
//                        "SELECT COUNT(reservation.timeslot_id) AS count FROM reservation WHERE reservation.timeslot_id = %s;",  timeslot_id);
//                ResultSet result2 = s.executeQuery(judge);
//                int count = 0;
//                while(result2.next()){
//                    count = result2.getInt("count");
//                }
//                int remaining = maxcap - count;
//                //No spots remaining
//                if(remaining <= 0){
//                    isfull = true;
//                    System.out.println("No spots left!!");
//                }
//                else{
//                    isfull = false;
//                }
//
//            } catch (Exception e){
//                System.out.println("Exception");
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//        }
//    }

    /**
     * Class to execute query to check if the user already made a reservation today
     */
//    class CheckAvailability extends AsyncTask<Void, Void, Void> {
//        String user_id;
//        String date_id;
//        Boolean isAvailable;
//        //set parameter for async function
//        public CheckAvailability(String user_id, String date_id){
//            this.user_id = user_id;
//            this.date_id = date_id;
//        }
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                System.out.println("enter background");
//                //connect to sql database
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                System.out.println("after connection");
//                //query the database for all user's reservation
//                String judge = String.format(
//                        "SELECT * FROM availability WHERE user_id = %s AND date_id = %s ;", user_id, date_id);
//                ResultSet result1 = s.executeQuery(judge);
//                Boolean flag1 = true;
//                while(result1.next()){
//                    flag1 = false;
//                    break;
//                }
//                if(flag1 == true){
//                    isavail = true;
//
//                }
//                else{
//                    isavail = false;
//                }
//
//            } catch (Exception e){
//                System.out.println("Exception");
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//        }
//    }

    /**
     * A class to execute the query of making reservation
     */
//    class MakeBooking extends AsyncTask<Void, Void, Void> {
//        String time_id;
//        String user_id;
//        String date_id;
//        int capacity;
//        //set parameter for async function
//        public MakeBooking(String time_id, String user_id, String date_id, int capacity){
//            this.time_id = time_id;
//            this.user_id = user_id;
//            this.date_id = date_id;
//            this.capacity = capacity;
//        }
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                System.out.println("enter background");
//                //connect to sql database
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                System.out.println("after connection");
//                //Add the user to the reservation list of the timeslot, and make the user unavailable today.
//                String query = String.format(
//                        "INSERT INTO reservation(user_id, timeslot_id) VALUES (%d, %s);", Integer.parseInt(user_id), time_id);
//
//                String query2 = String.format(
//                        "INSERT INTO availability(user_id, date_id) VALUES (%d, %s);", Integer.parseInt(user_id), date_id);
//                String query3 = String.format(
//                        "SELECT * FROM waitlist WHERE user_id = %s AND timeslot_id = %s", Integer.parseInt(user_id), time_id);
//                System.out.println(query);
//                int result = s.executeUpdate(query);
//                int result3 = s.executeUpdate(query2);
//                ResultSet result4 = s.executeQuery(query3);
//                if(result == 1 && result3 ==1){
//                    while(result4.next()){
//                        //Check if the user is in waitlist. If so, after the user makes the reservation, delete it from waitlist
//                        String query4 = String.format(
//                                "DELETE FROM waitlist WHERE user_id = %s AND timeslot_id = %s", Integer.parseInt(user_id), time_id);
//                        int result5 = s.executeUpdate(query4);
//                        if(result5==1){
//                            System.out.println("Reservation Insertion Succeeds");
//                            break;
//                        }
//                    }
//                }
//            } catch (Exception e){
//                System.out.println("Exception");
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//        }
//    }

//    class GetTimeslotInformation extends AsyncTask<Void, Void, Void> {
//        String usid;
//        String datenow;
//
//        public GetTimeslotInformation(String usid, String thisdate) {
//            this.usid = usid;
//            this.datenow = thisdate;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                //connect to sql database
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                String centerId = center_id;
//                System.out.println(centerId);
//                System.out.println(datenow);
//                //Display the information of a timeslot
//                String query = String.format("SELECT \n" +
//                        "\ttimeslot.timeslot_id AS timeslot_id,timeslot.max_capacity AS maxcap,timeslot.date_id, center.name AS center_name,timeslot.start_time,timeslot.finish_time, " +
//                        "COUNT(reservation.timeslot_id) AS currentusers\n" +
//                        "FROM timeslot \n" +
//                        "JOIN center\n" +
//                        "ON center.center_id = timeslot.center_id \n" +
//                        "LEFT JOIN reservation \n" +
//                        "\tON reservation.timeslot_id=timeslot.timeslot_id \n" +
//                        "JOIN datelist\n" +
//                        " ON datelist.date_id=timeslot.date_id WHERE datelist.date = '%s' AND center.center_id=%s " +
//                        "GROUP BY(timeslot.timeslot_id) ;", datenow, centerId);
//                System.out.println(query);
//                ResultSet result = s.executeQuery(query);
//                while (result.next()){
//                    slotList.add(new TimeslotItem(Integer.parseInt(result.getString("timeslot_id")),datenow,Integer.parseInt(result.getString("currentusers")), Integer.parseInt(result.getString("maxcap")),
//                            result.getString("start_time"), result.getString("finish_time"), result.getString("center_name"), usid, centerId, result.getString("date_id")));
//                }
//                System.out.println("Get all timeslots");
//            } catch (Exception e){
//                System.out.println("Exception");
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void aVoid){
//            super.onPostExecute(aVoid);
//        }
//    }

    public String getUnique_timeslot_id() {
        return unique_timeslot_id;
    }

    public void setUnique_timeslot_id(String unique_timeslot_id) {
        this.unique_timeslot_id = unique_timeslot_id;
    }

    public String getTimeslot_start_time() {
        return timeslot_start_time;
    }

    public void setTimeslot_start_time(String timeslot_start_time) {
        this.timeslot_start_time = timeslot_start_time;
    }

    public String getTimeslot_finish_time() {
        return timeslot_finish_time;
    }

    public void setTimeslot_finish_time(String timeslot_finish_time) {
        this.timeslot_finish_time = timeslot_finish_time;
    }

    public String getUnique_waitlist_id() {
        return unique_waitlist_id;
    }

    public void setUnique_waitlist_id(String unique_waitlist_id) {
        this.unique_waitlist_id = unique_waitlist_id;
    }

    public Integer getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(Integer max_capacity) {
        this.max_capacity = max_capacity;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public Integer getCurr_user() {
        return curr_user;
    }

    public void setCurr_user(Integer curr_user) {
        this.curr_user = curr_user;
    }

    public ArrayList<TimeslotItem> getSlotList() {
        return slotList;
    }

    public void setSlotList(ArrayList<TimeslotItem> slotList) {
        this.slotList = slotList;
    }

    public String getUnique_user_id() {
        return unique_user_id;
    }

    public void setUnique_user_id(String unique_user_id) {
        this.unique_user_id = unique_user_id;
    }

    public String getDate_id() {
        return date_id;
    }

    public void setDate_id(String date_id) {
        this.date_id = date_id;
    }

    public Boolean getIsavail() {
        return isavail;
    }

    public void setIsavail(Boolean isavail) {
        this.isavail = isavail;
    }

    public Boolean getIsfull() {
        return isfull;
    }

    public void setIsfull(Boolean isfull) {
        this.isfull = isfull;
    }

    public Integer getReserve_result() {
        return reserve_result;
    }

    public void setReserve_result(Integer reserve_result) {
        this.reserve_result = reserve_result;
    }
}

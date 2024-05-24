package com.example.uscrecapp_team28.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uscrecapp_team28.Activity.BookingActivity;
import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.TimeslotItem;
import com.example.uscrecapp_team28.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class BookingAdapter extends RecyclerView.Adapter{
    public ArrayList<TimeslotItem> gettSlotList() {
        return tSlotList;
    }

    public ArrayList<Button> getBookButtonList() {
        return bookButtonList;
    }

    public ArrayList<Button> getWaitButtonList() {
        return waitButtonList;
    }

    public static ArrayList<Button> bookButtonList = new ArrayList<Button>();

    private  ArrayList<Button> waitButtonList = new ArrayList<Button>();

    private ArrayList<TimeslotItem> tSlotList;
    private Agent tAgent;

    Context context;
    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public AppCompatButton bookButton;
        public AppCompatButton waitButton;

        public BookingViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            bookButton = itemView.findViewById(R.id.BookButton);
            waitButton = itemView.findViewById(R.id.WaitButton);
            BookingAdapter.bookButtonList.add(bookButton);

        }
    }



    public void settSlotList(ArrayList<TimeslotItem> tSlotList) {
        this.tSlotList = tSlotList;
    }

    public Agent gettAgent() {
        return tAgent;
    }

    public void settAgent(Agent tAgent) {
        this.tAgent = tAgent;
    }

    public BookingAdapter(ArrayList<TimeslotItem> slotList, Agent agent) throws ParseException {
        // System.out.println("Constructor for Booking Adapter");
        tSlotList = new ArrayList<TimeslotItem>();
        Date cur_time = new Date();
        for(int i = 0; i < slotList.size();i++){
            Date timeslot_time =  new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
                    .parse(slotList.get(i).getDate()+" "+slotList.get(i).getFinish());
            if(timeslot_time.compareTo(cur_time)>0){
                tSlotList.add(slotList.get(i));
            }
        }
        tAgent = agent;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeslot_item, parent, false);
        context = parent.getContext();
        BookingViewHolder evh = new BookingViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // System.out.println("On Bind View Holder");
        TimeslotItem currentItem = tSlotList.get(position);
        String times = currentItem.getDate() + "  " + currentItem.getStart() + " - " + currentItem.getFinish();
        ((BookingViewHolder) holder).mTextView1.setText(times);
        int remain_spot;
        if(currentItem.getMax_cap() - currentItem.getCurrent_users() > 0){
            remain_spot = currentItem.getMax_cap() - currentItem.getCurrent_users();
        }
        else{
            remain_spot = 0;
        }

        ((BookingViewHolder) holder).mTextView2.setText(Integer.toString(remain_spot));
        if(remain_spot > 0){
            ((BookingViewHolder) holder).waitButton.setVisibility(View.INVISIBLE);
            ((BookingViewHolder) holder).bookButton.setTag(currentItem.getTimeslot_id());
            ((BookingViewHolder) holder).bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_id = currentItem.getUser_id();
                    String time_id = Integer.toString(currentItem.getTimeslot_id());
                    // System.out.println(user_id);
                    // System.out.println(time_id);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);

                    builder.setTitle("Confirmation");
                    String msg = "You are going to make a reservation on " + times + " at " + currentItem.getCenter_name();
                    builder.setMessage(msg);
                        builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent i1 = new Intent(context, BookingActivity.class);
                                i1.putExtra("gym", currentItem.getCenter_id());
                                i1.putExtra("datechoice", currentItem.getDate());
                                context.startActivity(i1);
                            }
                        });
                        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Integer re_result = tAgent.make_reservation(user_id, time_id, currentItem.getDate_id(), currentItem.getMax_cap());
                                if(re_result == 0){
                                    //Successfully added a reservation

                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle("Confirmation");
                                    String msg1 = "You have successfully made a reservation on " + times + " at " + currentItem.getCenter_name();
                                    alertDialog.setMessage(msg1);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(context, BookingActivity.class);
                                                    i.putExtra("gym", currentItem.getCenter_id());
                                                    i.putExtra("datechoice", currentItem.getDate());
                                                    context.startActivity(i);
                                                }
                                            });
                                    alertDialog.show();

                                }
                                else if(re_result == 1){
                                    //TimeSlot is full just now
                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle("");
                                    String msg2 = "The timeslot is unavailable now. Please Check again.";
                                    alertDialog.setMessage(msg2);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(context, BookingActivity.class);
                                                    i.putExtra("gym", currentItem.getCenter_id());
                                                    i.putExtra("datechoice", currentItem.getDate());
                                                    context.startActivity(i);
                                                }
                                            });
                                    alertDialog.show();

                                }
                                else if (re_result == 2){
                                    //The user is not available
                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle("");
                                    String msg3 = "You already have a reservation today!";
                                    alertDialog.setMessage(msg3);
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(context, BookingActivity.class);
                                                    i.putExtra("gym", currentItem.getCenter_id());
                                                    i.putExtra("datechoice", currentItem.getDate());
                                                    context.startActivity(i);
                                                }
                                            });
                                    alertDialog.show();
                                }
                            }
                        });


                    builder.show();
                }
            });
        }
        else{
            ((BookingViewHolder) holder).bookButton.setVisibility(View.INVISIBLE);
            ((BookingViewHolder) holder).waitButton.setTag(currentItem.getTimeslot_id());
            ((BookingViewHolder) holder).waitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_id = currentItem.getUser_id();
                    String time_id = Integer.toString(currentItem.getTimeslot_id());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Confirmation");
                    String msg = "You are going to join the waitlist on " + times + " at " + currentItem.getCenter_name();
                    builder.setMessage(msg);
                    builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent i1 = new Intent(context, BookingActivity.class);
                            i1.putExtra("gym", currentItem.getCenter_id());
                            i1.putExtra("datechoice", currentItem.getDate());
                            context.startActivity(i1);
                        }
                    });
                    builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Integer wa_result = tAgent.join_waitlist(user_id, time_id);
                            if(wa_result == 0){
                                //Successfully Waitlist
                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("");
                                String msg3 = "You successfully join the waitlist on " + times + " at " + currentItem.getCenter_name();
                                alertDialog.setMessage(msg3);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(context, BookingActivity.class);
                                                i.putExtra("gym", currentItem.getCenter_id());
                                                i.putExtra("datechoice", currentItem.getDate());
                                                context.startActivity(i);
                                            }
                                        });
                                alertDialog.show();
                            }
                            else if(wa_result == 1){
                                //Already have a reservation
                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("");
                                String msg3 = "You already have a reservation on this timeslot!";
                                alertDialog.setMessage(msg3);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(context, BookingActivity.class);
                                                i.putExtra("gym", currentItem.getCenter_id());
                                                i.putExtra("datechoice", currentItem.getDate());
                                                context.startActivity(i);
                                            }
                                        });
                                alertDialog.show();

                            }
                            else if (wa_result == 2){
                                //Already in waitlist
                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("");
                                String msg3 = "You are already in the waitlist";
                                alertDialog.setMessage(msg3);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(context, BookingActivity.class);
                                                i.putExtra("gym", currentItem.getCenter_id());
                                                i.putExtra("datechoice", currentItem.getDate());
                                                context.startActivity(i);
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tSlotList.size();
    }

    //Join Waitlist
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
//                String checkquery = String.format(
//                        "SELECT * FROM reservation WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
//                ResultSet result1 = s.executeQuery(checkquery);
//                Boolean b1 = true;
//                Boolean b2 = true;
//                while(result1.next()){
//                    b1 = false;
//                    //Show a messagebox
//                }
//                String checkquery2 = String.format(
//                        "SELECT * FROM waitlist WHERE user_id = %s AND timeslot_id = %s;", user_id, time_id);
//                ResultSet result2 = s.executeQuery(checkquery2);
//                while(result2.next()){
//                    b2 = false;
//                    //Show a message box
//                }
//                if(b1 && b2){
//                    String query = String.format(
//                            "INSERT INTO waitlist(user_id, timeslot_id) VALUES (%s, %s);", user_id, time_id);
//                    int result = s.executeUpdate(query);
//                    if(result == 1){
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
//
//    //Make the booking
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
//                    String lastcheck = String.format(
//                            "SELECT COUNT(reservation.timeslot_id) AS count FROM reservation WHERE reservation.timeslot_id = %s;",  time_id);
//                    ResultSet result2 = s.executeQuery(lastcheck);
//                    int count = 0;
//                    while(result2.next()){
//                        count = result2.getInt("count");
//                    }
//                    int remaining = capacity - count;
//                    //replace with a message box later
//                    if(remaining <= 0){
//                        System.out.println("No spots left!!");
//                    }
//                    else{
//                        String query = String.format(
//                                "INSERT INTO reservation(user_id, timeslot_id) VALUES (%d, %s);", Integer.parseInt(user_id), time_id);
//                        String query2 = String.format(
//                                "INSERT INTO availability(user_id, date_id) VALUES (%d, %s);", Integer.parseInt(user_id), date_id);
//                        System.out.println(query);
//                        int result = s.executeUpdate(query);
//                        int result3 = s.executeUpdate(query2);
//                        if(result == 1 && result3 ==1){
//                            System.out.println("Reservation Insertion Succeeds");
//                        }
//                    }
//                }
//                else{
//                    //Replace with a messagebox later.
//                    System.out.println("You already have a reservation today.");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
//                    builder.setCancelable(true);
//                    builder.setTitle("Message");
//                    builder.setMessage("You already made a reservation today!");
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.cancel();
//                        }
//                    });
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    builder.create();
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
}

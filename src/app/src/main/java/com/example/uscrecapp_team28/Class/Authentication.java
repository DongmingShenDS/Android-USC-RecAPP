package com.example.uscrecapp_team28.Class;

import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Authentication implements AuthenticationInterface{

    private String device_id;
    private String unique_userid = "";

    public Authentication(String did) {
        this.device_id = did;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String unique_userid) {
        this.device_id = unique_userid;
    }

    public void setUnique_userid(String unique_userid) {
        this.unique_userid = unique_userid;
    }

    public String getUnique_userid() {
        return unique_userid;
    }

    @Override
    public boolean if_already_login() {
        // check database for if unique_userid's login column is TRUE
        try {
            //new AuthenticationTask().execute().get();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        //Your code goes here
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        String query = String.format("SELECT * FROM user WHERE device_id='%s';", device_id);
                        ResultSet result = s.executeQuery(query);
                        // System.out.println("Query Complete");
                        while (result.next()){
                            String temp = result.getString("user_id") + "\n";
                            setUnique_userid(temp);
                        }
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.start();
            thread1.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.unique_userid.isEmpty()) {
            // System.out.println("NOT YET LOGIN");
            return false;
        }
        else {
            // System.out.println("ALREADY LOGIN");
            return true;
        }
    }
}

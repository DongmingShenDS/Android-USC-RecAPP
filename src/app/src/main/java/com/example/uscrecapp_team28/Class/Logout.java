package com.example.uscrecapp_team28.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Logout implements LogoutInterface{

    String device_id;

    public Logout(String did) {
        this.device_id = did;
    }

    @Override
    public boolean user_logout() {
        try {
            // new DeleteDeviceTask().execute().get();
            // new AuthenticationTask().execute().get();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        String update = String.format("UPDATE user SET device_id='' WHERE device_id='%s';", getDevice_id());
                        int i = s.executeUpdate(update);
                        // System.out.println("Update Complete");
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
            return false;
        }
        return true;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

//    class DeleteDeviceTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids){
//            try{
//                Class.forName("com.mysql.jdbc.Driver");
//                String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
//                Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
//                Statement s = connection.createStatement();
//                String update = String.format("UPDATE user SET device_id='' WHERE device_id='%s';", getDevice_id());
//                int i = s.executeUpdate(update);
//                // System.out.println("Update Complete");
//                connection.close();
//            } catch (Exception e){
//                e.printStackTrace();
//                // System.out.println("Exception");
//            }
//            return null;
//        }
//    }
}

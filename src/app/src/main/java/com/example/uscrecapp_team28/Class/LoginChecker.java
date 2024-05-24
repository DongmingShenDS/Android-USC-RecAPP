package com.example.uscrecapp_team28.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginChecker implements LoginCheckerInterface{

    String username;
    String password;
    String device_id;
    Boolean loginFlag;

    public LoginChecker(String un, String pw, String di) {
        this.username = un;
        this.password = pw;
        this.device_id = di;
        this.loginFlag = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(boolean b) {
        this.loginFlag = b;
    }

    @Override
    public boolean check_login() {
        // check database for if username & password combination is correct
        try {
            //new LoginTask().execute().get();
            Thread thread_login = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        Class.forName("com.mysql.jdbc.Driver");
                        String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                        Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                        Statement s = connection.createStatement();
                        String query = String.format("SELECT * FROM user WHERE username='%s' AND password='%s';", getUsername(), getPassword());
                        ResultSet result = s.executeQuery(query);
//                        System.out.println("Query Complete");
//                        System.out.println(result);
                        while (result.next()) {
                            setLoginFlag(true);
                        }
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread_login.start();
            thread_login.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(this.loginFlag);
        if (this.loginFlag) {
            try {
                Thread thread_device = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            Class.forName("com.mysql.jdbc.Driver");
                            String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3479112?characterEncoding=latin1";
                            Connection connection = DriverManager.getConnection(connectionUrl,"sql3479112","k1Q9Fq3375");
                            Statement s = connection.createStatement();
                            String update = String.format("UPDATE user SET device_id='%s' WHERE username='%s' AND password='%s';", device_id, username, password);
                            int i = s.executeUpdate(update);
                            // System.out.println("Update Complete");
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread_device.start();
                thread_device.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return false;
        }
    }
}

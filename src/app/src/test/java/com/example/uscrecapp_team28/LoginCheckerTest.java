package com.example.uscrecapp_team28;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import com.example.uscrecapp_team28.Class.LoginChecker;

import junit.framework.TestCase;

public class LoginCheckerTest extends TestCase {

    public void setup() {
    }

    public void cleanup() throws Exception {
    }

    // DS
    public void testGetUsername() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        assertEquals("alvinshe", a.getUsername());
        a.setUsername("tigojian");
        assertEquals("tigojian", a.getUsername());
    }

    // DS
    public void testSetUsername() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        a.setUsername("tigojianX");
        assertEquals("tigojianX", a.getUsername());
        a.setUsername("tigojianY");
        assertEquals("tigojianY", a.getUsername());
        a.setUsername("tigojianZ");
        assertEquals("tigojianZ", a.getUsername());

    }

    // DS
    public void testGetPassword() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        assertEquals("12345678", a.getPassword());
        a.setPassword("tigojian");
        assertEquals("tigojian", a.getPassword());
        a.setPassword("xxxxx");
        assertEquals("xxxxx", a.getPassword());
        a.setPassword("12345");
        assertEquals("12345", a.getPassword());
    }

    // DS
    public void testSetPassword() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        a.setPassword("tigojianX");
        assertEquals("tigojianX", a.getPassword());
    }

    // DS
    public void testGetLoginFlag() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        assertEquals(false, a.getLoginFlag());
    }

    // DS
    public void testSetLoginFlag() {
        LoginChecker a = new LoginChecker("alvinshe","12345678","xxx");
        a.setLoginFlag(true);
        assertEquals(true, a.getLoginFlag());
    }

    // DS
    public void testCheck_login() {
        LoginChecker loginChecker1 = new LoginChecker("test","test","deviceid");
        loginChecker1.check_login();
        assertEquals(true, loginChecker1.getLoginFlag());
        LoginChecker loginChecker2 = new LoginChecker("alvinshe","x","deviceid");
        loginChecker2.check_login();
        assertEquals(false, loginChecker2.getLoginFlag());
        LoginChecker loginChecker3 = new LoginChecker("tigojian","x","deviceid");
        loginChecker3.check_login();
        assertEquals(false, loginChecker3.getLoginFlag());
    }
}
package com.example.uscrecapp_team28;

import com.example.uscrecapp_team28.Class.Logout;

import junit.framework.TestCase;

import org.junit.Assert;

// DS
public class LogoutTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testUser_logout() {
        Logout l1 = new Logout("did");
        Logout l2 = new Logout("user");
        try {
            l1.user_logout();
            l2.user_logout();
        } catch (Exception e) {
            Assert.fail("Exception");
        }

    }

    public void testGetDevice_id() {
        Logout l1 = new Logout("did");
        assertEquals("did", l1.getDevice_id());
        Logout l2 = new Logout("x");
        assertEquals("x", l2.getDevice_id());
        Logout l3 = new Logout("xsv23i429uwwbej2ief4i2e");
        assertEquals("xsv23i429uwwbej2ief4i2e", l3.getDevice_id());
    }

    public void testSetDevice_id() {
        Logout l1 = new Logout("init");
        assertEquals("init", l1.getDevice_id());
        l1.setDevice_id("did");
        assertEquals("did", l1.getDevice_id());
        l1.setDevice_id("x");
        assertEquals("x", l1.getDevice_id());
        l1.setDevice_id("xsv23i429uwwbej2ief4i2e");
        assertEquals("xsv23i429uwwbej2ief4i2e", l1.getDevice_id());
    }
}
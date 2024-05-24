package com.example.uscrecapp_team28;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import com.example.uscrecapp_team28.Class.Authentication;

public class AuthenticationTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    // DS
    public void testGetDevice_id() {
        Authentication a = new Authentication("dida");
        assertEquals("dida", a.getDevice_id());
        Authentication b = new Authentication("didb");
        assertEquals("didb", b.getDevice_id());
    }

    // DS
    public void testSetDevice_id() {
        Authentication a = new Authentication("did");
        a.setDevice_id("did'");
        assertEquals("did'", a.getDevice_id());
        a.setDevice_id("did'2");
        assertEquals("did'2", a.getDevice_id());
        a.setDevice_id("");
        assertEquals("", a.getDevice_id());
    }

    // DS
    public void testSetUnique_userid() {
        Authentication a = new Authentication("did");
        a.setUnique_userid("abc");
        assertEquals("abc", a.getUnique_userid());
    }

    // DS
    public void testGetUnique_userid() {
        Authentication a = new Authentication("did");
        a.setUnique_userid("abc2");
        assertEquals("abc2", a.getUnique_userid());
    }

    // DS
    public void testIf_already_login() {
        Authentication a = new Authentication("did");
        assertEquals(false, a.if_already_login());
        a.setUnique_userid("abc");
        assertEquals(true, a.if_already_login());
        Authentication i = new Authentication("interface");
        assertEquals(false, i.if_already_login());
        a.setDevice_id("deviceid");
        assertEquals(true, a.if_already_login());
    }
}
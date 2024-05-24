package com.example.uscrecapp_team28;

import com.example.uscrecapp_team28.Class.Agent;
import com.example.uscrecapp_team28.Class.Profile;

import junit.framework.TestCase;

import org.junit.Assert;

import java.sql.ResultSet;
import java.util.ArrayList;

// DS
public class ProfileTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetResult() {
        Profile p = new Profile("did");
        try {
            ResultSet r = p.getResult();
        } catch (Exception e) {
            Assert.fail("Exception");
        }
    }

    public void testSetResult() {
        Profile p = new Profile("did");
        p.setResult(null);
        assertEquals(null, p.getResult());
        ResultSet r = p.getResult();
        p.setResult(r);
        assertEquals(r, p.getResult());
    }

    public void testGetUnique_userid() {
        Profile p = new Profile("did");
        assertEquals(null, p.getUnique_userid());
        Profile real = new Profile("deviceid");
        assertEquals("5", real.getUnique_userid());
    }

    public void testSetUnique_userid() {
        Profile p = new Profile("did");
        p.setUnique_userid("1");
        assertEquals("1", p.getUnique_userid());
        p.setUnique_userid("x");
        assertEquals("x", p.getUnique_userid());
    }

    public void testGetUsername() {
        Profile p = new Profile("did");
        assertEquals(null, p.getUsername());
        Profile real = new Profile("deviceid");
        assertEquals("test", real.getUsername());
    }

    public void testSetUsername() {
        Profile p = new Profile("did");
        p.setUsername("alvin");
        assertEquals("alvin", p.getUsername());
        Profile real = new Profile("deviceid");
        assertEquals("test", real.getUsername());
        real.setUsername("real_test");
        assertEquals("real_test", real.getUsername());
    }

    public void testGetPassword() {
        Profile p = new Profile("did");
        assertEquals(null, p.getPassword());
        Profile real = new Profile("deviceid");
        assertEquals("test", real.getPassword());
    }

    public void testSetPassword() {
        Profile p = new Profile("did");
        p.setPassword("alvin");
        assertEquals("alvin", p.getPassword());
        Profile real = new Profile("deviceid");
        assertEquals("test", real.getPassword());
        real.setPassword("real_test");
        assertEquals("real_test", real.getPassword());
    }

    public void testGetName() {
        Profile p = new Profile("did");
        assertEquals(null, p.getName());
        Profile real = new Profile("deviceid");
        assertEquals("test_name", real.getName());
    }

    public void testSetName() {
        Profile p = new Profile("did");
        p.setName("alvin");
        assertEquals("alvin", p.getName());
        Profile real = new Profile("deviceid");
        assertEquals("test_name", real.getName());
        real.setName("real_testname");
        assertEquals("real_testname", real.getName());
    }

    public void testGetEmail() {
        Profile p = new Profile("did");
        assertEquals(null, p.getEmail());
        Profile real = new Profile("deviceid");
        assertEquals("test_email", real.getEmail());
    }

    public void testSetEmail() {
        Profile p = new Profile("did");
        p.setEmail("xm");
        assertEquals("xm", p.getEmail());
        Profile real = new Profile("deviceid");
        assertEquals("test_email", real.getEmail());
        real.setEmail("email");
        assertEquals("email", real.getEmail());
    }

    public void testGetUscid() {
        Profile p = new Profile("did");
        assertEquals(null, p.getUscid());
        Profile real = new Profile("deviceid");
        assertEquals("uscid", real.getUscid());
    }

    public void testSetUscid() {
        Profile p = new Profile("did");
        p.setUscid("xm");
        assertEquals("xm", p.getUscid());
        Profile real = new Profile("deviceid");
        assertEquals("uscid", real.getUscid());
        real.setUscid("realid");
        assertEquals("realid", real.getUscid());
    }

    public void testGetPhotourl() {
        Profile p = new Profile("did");
        assertEquals(null, p.getPhotourl());
        Profile real = new Profile("deviceid");
        assertEquals("https://media-exp1.licdn.com/dms/image/C5603AQFxpC5CYYULNA/profile-displayphoto-shrink_800_800/0/1624353277071?e=1652918400&v=beta&t=84ukoO8jlXa6Di4JE2VbtMd-klLCCcQK6aoQT1G5GwY", real.getPhotourl());
    }

    public void testSetPhotourl() {
        Profile p = new Profile("did");
        p.setPhotourl("xm");
        assertEquals("xm", p.getPhotourl());
        Profile real = new Profile("deviceid");
        real.setPhotourl("url");
        assertEquals("url", real.getPhotourl());
    }

    public void testGetDevice_id() {
        Profile p = new Profile("did");
        assertEquals("did", p.getDevice_id());
        Profile real = new Profile("deviceid");
        assertEquals("deviceid", real.getDevice_id());
    }

    public void testSetDevice_id() {
        Profile p = new Profile("did");
        p.setDevice_id(null);
        assertEquals(null, p.getDevice_id());
        p.setDevice_id("newid");
        assertEquals("newid", p.getDevice_id());
    }

    public void testDisplay_profile() {
        ArrayList<String> display = new ArrayList<String>();
        display.add("5");
        display.add("uscid");
        display.add("test");
        display.add("test");
        display.add("https://media-exp1.licdn.com/dms/image/C5603AQFxpC5CYYULNA/profile-displayphoto-shrink_800_800/0/1624353277071?e=1652918400&v=beta&t=84ukoO8jlXa6Di4JE2VbtMd-klLCCcQK6aoQT1G5GwY");
        display.add("test_name");
        display.add("test_email");
        display.add("deviceid");
        Profile real = new Profile("deviceid");
        assertEquals(display, real.display_profile());
        ArrayList<String> e_arr = new ArrayList<String>();
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add(null);
        e_arr.add("fakeid");
        Profile fake = new Profile("fakeid");
        assertEquals(e_arr, fake.display_profile());
    }
}
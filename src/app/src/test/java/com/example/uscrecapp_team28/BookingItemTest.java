package com.example.uscrecapp_team28;

import com.example.uscrecapp_team28.Class.BookingItem;

import junit.framework.TestCase;

// DS
public class BookingItemTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetmReservationId() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        assertEquals("1", b.getmReservationId());
    }

    public void testGetText1() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        assertEquals("text1", b.getText1());
    }

    public void testGetText2() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        assertEquals("text2", b.getText2());
    }

    public void testGetImgURL() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        b.setImgURL("url");
        assertEquals("url", b.getImgURL());
    }

    public void testSetmReservationID() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        b.setmReservationID("newid");
        assertEquals("newid", b.getmReservationId());
    }

    public void testSetmText1() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        b.setmText1("new_text1");
        assertEquals("new_text1", b.getText1());
    }

    public void testSetmText2() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        b.setmText2("new_text2");
        assertEquals("new_text2", b.getText2());
    }

    public void testSetImgURL() {
        BookingItem b = new BookingItem("1", "text1", "text2");
        b.setImgURL("url_set");
        assertEquals("url_set", b.getImgURL());
    }
}
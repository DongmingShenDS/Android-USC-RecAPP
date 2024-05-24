package com.example.uscrecapp_team28;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.uscrecapp_team28.Activity.MainActivity;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookingActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);
    private static Matcher<View> getElementFromMatchAtPosition(final Matcher<View> matcher, final int position) {
        return new BaseMatcher<View>() {
            int counter = 0;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == position) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }
            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at hierarchy position "+position);
            }
        };
    }

    // Make a reservation, and then try to make another reservation at the same day
    @Test
    public void makeReservationTest() {
        // login
        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // go to booking page (lyon)
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.tomorrowbutton)).perform(click());
        // make reservation
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.BookButton)), 0), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        //If the user wants to make another reservation today, it will fail
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.BookButton)), 1), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // back to map page
        onView(withId(R.id.booking_back)).perform(click());
        // go to summary page
        onView(withId(R.id.summarybtn)).perform(click());
        // cancel reservation
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.CancelButton)), 0), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // back to map page
        onView(withId(R.id.returnButton)).perform(click());
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    };
    //Make the reservation, and then try to join the waitlist of same timeslot
    @Test
    public void joinAfterMakeTest() {
        // login
        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // go to booking page (lyon)
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.tomorrowbutton)).perform(click());
        // make reservation in a timeslot that has 1 spot remaining
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.BookButton)), 4), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        //If the user wants to join waitlist in the timeslot, it will fail
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.WaitButton)), 4), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // back to map page
        onView(withId(R.id.booking_back)).perform(click());
        // go to summary page
        onView(withId(R.id.summarybtn)).perform(click());
        // cancel reservation
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.CancelButton)), 0), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // back to map page
        onView(withId(R.id.returnButton)).perform(click());
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }
    //Join Waitlist and then try to join again
    @Test
    public void joinWaitlistTest() {
        // login
        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // go to booking page (village)
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.thirdbutton)).perform(click());
        // Join waitlist in a timeslot that has no spot left
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.WaitButton)), 5), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        //If the user wants to join the waitlist again, it will fail
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.WaitButton)), 5), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        // back to map page
        onView(withId(R.id.booking_back)).perform(click());
        // delete from waitlist
        try {
            Thread thread_del_join = new Thread(new Runnable() {
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
                        String query4 = String.format(
                                "DELETE FROM waitlist WHERE user_id = %s", "8");
                        int result2 = s.executeUpdate(query4);
                        int del = 0;
                        if(result2==1){
                            del =1;
                            assertEquals(1, del);
                        }
                        connection.close();

                    } catch (Exception e){
                        System.out.println("Exception");
                        e.printStackTrace();
                    }
                }
            });
            thread_del_join.start();
            thread_del_join.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }

    @Test
    public void onClickRefresh() {
        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        onView(withId(R.id.wrong)).check(doesNotExist());
//        intended(hasComponent("com.example.uscrecapp_team28.Activity.MapActivity"));
        onView(withId(R.id.button1)).perform(click());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String tomorrow =  sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String third =  sdf.format(cal.getTime());
        onView(withId(R.id.todaybutton))
                .check(matches(withText(today)));
        onView(withId(R.id.tomorrowbutton))
                .check(matches(withText(tomorrow)));
        onView(withId(R.id.thirdbutton))
                .check(matches(withText(third)));
        onView(withId(R.id.refreshbtn)).perform(click());
        onView(withId(R.id.booking_back)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
    }

//    @Test
//    public void onClickRefresh() {
//        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signinbtn)).perform(click());
//        onView(withId(R.id.wrong)).check(doesNotExist());
////        intended(hasComponent("com.example.uscrecapp_team28.MapActivity"));
//        onView(withId(R.id.button1)).perform(click());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        String today = sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String tomorrow =  sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String third =  sdf.format(cal.getTime());
//        onView(withId(R.id.todaybutton))
//                .check(matches(withText(today)));
//        onView(withId(R.id.tomorrowbutton))
//                .check(matches(withText(tomorrow)));
//        onView(withId(R.id.thirdbutton))
//                .check(matches(withText(third)));
//        onView(withId(R.id.refreshbtn)).perform(click());
//        onView(withId(R.id.booking_back)).perform(click());
//        onView(withId(R.id.profileButton)).perform(click());
//        onView(withId(R.id.profile_logout)).perform(click());
//    }

//
//    @Test
//    public void onClickMap() {
//        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signinbtn)).perform(click());
//        onView(withId(R.id.wrong)).check(doesNotExist());
////        intended(hasComponent("com.example.uscrecapp_team28.Activity.MapActivity"));
//        onView(withId(R.id.button1)).perform(click());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        String today = sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String tomorrow =  sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String third =  sdf.format(cal.getTime());
//        onView(withId(R.id.todaybutton))
//                .check(matches(withText(today)));
//        onView(withId(R.id.tomorrowbutton))
//                .check(matches(withText(tomorrow)));
//        onView(withId(R.id.thirdbutton))
//                .check(matches(withText(third)));
//        onView(withId(R.id.booking_back)).perform(click());
//        onView(withId(R.id.profileButton)).perform(click());
//        onView(withId(R.id.profile_logout)).perform(click());
//
//    }
////
//    @Test
//    public void onClickToday() {
//        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signinbtn)).perform(click());
//        onView(withId(R.id.wrong)).check(doesNotExist());
////        intended(hasComponent("com.example.uscrecapp_team28.Activity.MapActivity"));
//        onView(withId(R.id.button1)).perform(click());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        String today = sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String tomorrow =  sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String third =  sdf.format(cal.getTime());
//        onView(withId(R.id.todaybutton))
//                .check(matches(withText(today)));
//        onView(withId(R.id.tomorrowbutton))
//                .check(matches(withText(tomorrow)));
//        onView(withId(R.id.thirdbutton))
//                .check(matches(withText(third)));
//        onView(withId(R.id.todaybutton)).perform(click());
//        onView(withId(R.id.booking_back)).perform(click());
//        onView(withId(R.id.profileButton)).perform(click());
//        onView(withId(R.id.profile_logout)).perform(click());
//
//        //onView(withId(R.id.TrecyclerView)).perform(click());
//
//
//    }
//
//    @Test
//    public void onClickSecond() {
//        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signinbtn)).perform(click());
//        onView(withId(R.id.wrong)).check(doesNotExist());
////        intended(hasComponent("com.example.uscrecapp_team28.Activity.MapActivity"));
//        onView(withId(R.id.button1)).perform(click());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        String today = sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String tomorrow =  sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String third =  sdf.format(cal.getTime());
//        onView(withId(R.id.todaybutton))
//                .check(matches(withText(today)));
//        onView(withId(R.id.tomorrowbutton))
//                .check(matches(withText(tomorrow)));
//        onView(withId(R.id.thirdbutton))
//                .check(matches(withText(third)));
//        onView(withId(R.id.tomorrowbutton)).perform(click());
//        RecyclerView rv = getCurrentActivity().findViewById(R.id.TrecyclerView);
//        BookingAdapter ba = (BookingAdapter) rv.getAdapter() ;
//        ArrayList<TimeslotItem> tlist = ba.gettSlotList();
//        assertEquals(6, tlist.size());
//        assertEquals(tomorrow, tlist.get(0).getDate());
//        assertEquals("08:00", tlist.get(0).getStart());
//        onView(withId(R.id.booking_back)).perform(click());
//        onView(withId(R.id.profileButton)).perform(click());
//        onView(withId(R.id.profile_logout)).perform(click());
//
//
//    }
//
//    @Test
//    public void onClickThird() {
//        onView(withId(R.id.username)).perform(typeText("bookingtest"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.signinbtn)).perform(click());
//        onView(withId(R.id.wrong)).check(doesNotExist());
////        intended(hasComponent("com.example.uscrecapp_team28.Activity.MapActivity"));
//        onView(withId(R.id.button1)).perform(click());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        String today = sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String tomorrow =  sdf.format(cal.getTime());
//        cal.add(Calendar.DATE, 1);
//        String third =  sdf.format(cal.getTime());
//        onView(withId(R.id.todaybutton))
//                .check(matches(withText(today)));
//        onView(withId(R.id.tomorrowbutton))
//                .check(matches(withText(tomorrow)));
//        onView(withId(R.id.thirdbutton))
//                .check(matches(withText(third)));
//        onView(withId(R.id.thirdbutton)).perform(click());
//        RecyclerView rv = getCurrentActivity().findViewById(R.id.TrecyclerView);
//        BookingAdapter ba = (BookingAdapter) rv.getAdapter() ;
//        ArrayList<TimeslotItem> tlist = ba.gettSlotList();
//        assertEquals(6, tlist.size());
//        assertEquals(third, tlist.get(0).getDate());
//        assertEquals("08:00", tlist.get(0).getStart());
//        onView(withId(R.id.booking_back)).perform(click());
//        onView(withId(R.id.profileButton)).perform(click());
//        onView(withId(R.id.profile_logout)).perform(click());
//    }
}
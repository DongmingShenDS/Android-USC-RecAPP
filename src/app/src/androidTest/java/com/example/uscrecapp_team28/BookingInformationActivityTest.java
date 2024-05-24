package com.example.uscrecapp_team28;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

import android.app.Activity;
import android.view.View;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.example.uscrecapp_team28.Activity.BookingActivity;
import com.example.uscrecapp_team28.Activity.BookingInformationActivity;
import com.example.uscrecapp_team28.Activity.MainActivity;
import com.example.uscrecapp_team28.Activity.MapActivity;
import com.example.uscrecapp_team28.Helper.BookingAdapter;
import com.example.uscrecapp_team28.Helper.BookingInformationAdapter;
import com.example.uscrecapp_team28.Class.BookingItem;
import com.example.uscrecapp_team28.Helper.PastBookingInformationAdapter;
import com.example.uscrecapp_team28.Helper.RecyclerMapAdapter;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookingInformationActivityTest {
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
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);
    public Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Collection<Activity> allActivities = ActivityLifecycleMonitorRegistry.getInstance()
                        .getActivitiesInStage(Stage.RESUMED);
                if (!allActivities.isEmpty()) {
                    currentActivity[0] = allActivities.iterator().next();
                }
            }
        });
        return currentActivity[0];
    }
    @Test
    public void DisplayReservation(){
        onView(withId(R.id.username)).perform(typeText("reservation_test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        ArrayList<BookingItem> mapFutureList = ((RecyclerMapAdapter)((MapActivity)getCurrentActivity()).getmAdapter()).getmBookingList();
        int future_correct_count = 0;
        for(BookingItem b:mapFutureList){
            if(b.getmReservation_id().equals("129")||b.getmReservation_id().equals("130")){
                future_correct_count++;
            }
        }
        assertEquals(future_correct_count,2);
        onView(withId(R.id.summarybtn)).perform(click());
        ArrayList<BookingItem> historyList = ((PastBookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmHistoryAdapter()).getmBookingList();
        assertEquals(1,historyList.size());
        assertEquals("128",historyList.get(0).getmReservation_id());
        ArrayList<BookingItem> futureList = ((BookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmAdapter()).getmBookingList();
        assertEquals(2,futureList.size());
        int correct_count = 0;
        for(BookingItem b:futureList){
            if(b.getmReservation_id().equals("129")||b.getmReservation_id().equals("130")){
                correct_count++;
            }
        }
        assertEquals(2,correct_count);
        onView(withId(R.id.returnButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
    }
    @Test
    public void CancelReservation(){
        onView(withId(R.id.username)).perform(typeText("reservation_test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), ViewActions.closeSoftKeyboard());
//        Intents.init();
        onView(withId(R.id.signinbtn)).perform(click());
//        intended(hasComponent(MapActivity.class.getName()));
//        Intents.release();
//        Intents.init();
        onView(withId(R.id.summarybtn)).perform(click());
        ArrayList<BookingItem> historyList = ((PastBookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmHistoryAdapter()).getmBookingList();
        assertEquals(1,historyList.size());
        assertEquals("128",historyList.get(0).getmReservation_id());
//        intended(hasComponent(BookingInformationActivity.class.getName()));
        ArrayList<BookingItem> futureList = ((BookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmAdapter()).getmBookingList();
        assertEquals(2,futureList.size());
        int correct_count = 0;
        for(BookingItem b:futureList){
            if(b.getmReservation_id().equals("129")||b.getmReservation_id().equals("130")){
                correct_count++;
            }
        }
        assertEquals(2,correct_count);
        onView(withId(R.id.returnButton)).perform(click());
        onView(withId(R.id.button1)).perform(click());
        //make reservation
        onView(withId(R.id.tomorrowbutton)).perform(click());
        BookingAdapter bookingActivity = (BookingAdapter) ((BookingActivity) getCurrentActivity()).gettAdapter();
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.BookButton)), 0), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("OK")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.booking_back)).perform(click());
        // go to summary page
        onView(withId(R.id.summarybtn)).perform(click());
        // cancel reservation
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.CancelButton)), 0), isDisplayed())).perform(click());
        onView(withText("CONFIRM")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        //check the display that the element is the same as before
        ArrayList<BookingItem> cancelHistoryList = ((PastBookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmHistoryAdapter()).getmBookingList();
        assertEquals(1,cancelHistoryList.size());
        assertEquals("128",cancelHistoryList.get(0).getmReservation_id());
        ArrayList<BookingItem> cancelFutureList = ((BookingInformationAdapter)((BookingInformationActivity)getCurrentActivity()).getmAdapter()).getmBookingList();
        assertEquals(2,cancelFutureList.size());
        int future_correct_count = 0;
        for(BookingItem b:cancelFutureList){
            if(b.getmReservation_id().equals("129")||b.getmReservation_id().equals("130")){
                future_correct_count++;
            }
        }
        assertEquals(2,future_correct_count);
        // back to map page
        onView(withId(R.id.returnButton)).perform(click());
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
    }
}
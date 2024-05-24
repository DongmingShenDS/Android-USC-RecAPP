package com.example.uscrecapp_team28;

// 4 test cases

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.uscrecapp_team28.Activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mapToLyon() {
        // login to a the test account
        onView(withId(R.id.username)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // test if currently in the map page, if not => fail
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click button 1 to go to reservation page of lyon
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.center_name)).check(matches(withText("@ Lyon Recreation Center")));
        // check refresh button
        onView(withId(R.id.refreshbtn)).perform(click());
        onView(withId(R.id.center_name)).check(matches(withText("@ Lyon Recreation Center")));
        // check if successfully back to the map page when click back
        onView(withId(R.id.booking_back)).perform(click());
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }

    @Test
    public void mapToVillage() {
        // login to a the test account
        onView(withId(R.id.username)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // test if currently in the map page, if not => fail
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click button 2 to go to reservation page of village
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.center_name)).check(matches(withText("@ USC Village Fitness Center")));
        // check refresh button
        onView(withId(R.id.refreshbtn)).perform(click());
        onView(withId(R.id.center_name)).check(matches(withText("@ USC Village Fitness Center")));
        // check if successfully back to the map page when click back
        onView(withId(R.id.booking_back)).perform(click());
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }

    @Test
    public void mapToProfile() {
        // login to a the test account
        onView(withId(R.id.username)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // test if currently in the map page, if not => fail
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click into profile (icon)
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText("TESTNAME")));
        onView(withId(R.id.profile_username)).check(matches(withText("Username: mainmap")));
        onView(withId(R.id.profile_email)).check(matches(withText("Email: testEMAIL")));
        onView(withId(R.id.profile_uscid)).check(matches(withText("USCid: testUSCID")));
        onView(withId(R.id.profile_back)).perform(click());
        // click into profile (text)
        onView(withId(R.id.profileText)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText("TESTNAME")));
        onView(withId(R.id.profile_username)).check(matches(withText("Username: mainmap")));
        onView(withId(R.id.profile_email)).check(matches(withText("Email: testEMAIL")));
        onView(withId(R.id.profile_uscid)).check(matches(withText("USCid: testUSCID")));
        onView(withId(R.id.profile_back)).perform(click());
        // check if successfully back to the map page
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }

    @Test
    public void mapToSummary() {
        // login to a the test account
        onView(withId(R.id.username)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("mainmap"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // test if currently in the map page, if not => fail
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click into summary
        onView(withId(R.id.summarybtn)).perform(click());
        onView(withId(R.id.bookinginfotitle)).check(matches(withText("Booking Information")));
        onView(withId(R.id.returnButton)).perform(click());
        // check if successfully back to the map page
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // logout
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }
}
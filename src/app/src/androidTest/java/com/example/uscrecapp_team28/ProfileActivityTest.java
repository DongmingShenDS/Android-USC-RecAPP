package com.example.uscrecapp_team28;

// 1 test case

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;

//import android.support.test.espresso.contrib.RecyclerViewActions;

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
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void profileContentTest() {
        onView(withId(R.id.username)).perform(typeText("profile"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("profile"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // already jump to map page, so the wrong field will not exist
        onView(withId(R.id.wrong)).check(doesNotExist());
        // check if in map page already
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click into profile to check all fields are expected
        onView(withId(R.id.profileText)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText("TESTNAME")));
        onView(withId(R.id.profile_username)).check(matches(withText("Username: profile")));
        onView(withId(R.id.profile_email)).check(matches(withText("Email: testEMAIL")));
        onView(withId(R.id.profile_uscid)).check(matches(withText("USCid: testUSCID")));
        onView(withId(R.id.profile_total)).check(matches(withText("0")));
        onView(withId(R.id.profile_history)).check(matches(withText("0")));
        onView(withId(R.id.profile_upcoming)).check(matches(withText("0")));
        // logout
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }
}
package com.example.uscrecapp_team28;

// 1 test case

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.uscrecapp_team28.Activity.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // run A (setup) then B (test)
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AlreadyLoginTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void AtestAlreadyLoginSetup() {
        // first, login but not logout -> just to setup... => will run first because of A
        onView(withId(R.id.username)).perform(typeText("already"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("already"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
    }

    @Test
    public void BtestAlreadyLoginTest() {
        // the user should be already logged in (because using the same device) => will run then because of B
        // check if in map page already
        onView(withId(R.id.wrong)).check(doesNotExist());
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click into profile to further check if the identity is a match (icon)
        onView(withId(R.id.profileButton)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText("TESTNAME")));
        onView(withId(R.id.profile_username)).check(matches(withText("Username: already")));
        onView(withId(R.id.profile_email)).check(matches(withText("Email: testEMAIL")));
        onView(withId(R.id.profile_uscid)).check(matches(withText("USCid: testUSCID")));
        // logout
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }

}
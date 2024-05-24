package com.example.uscrecapp_team28;

// 2 test cases

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testLoginSuccessAndLogout() {
        onView(withId(R.id.username)).perform(typeText("test_login"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test_login"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // already jump to map page, so the wrong field will not exist
        onView(withId(R.id.wrong)).check(doesNotExist());
        // check if in map page already
        onView(withId(R.id.profileText)).check(matches(withText("VIEW PROFILE")));
        onView(withId(R.id.summarybtn)).check(matches(withText("UPCOMING BOOKINGS")));
        // click into profile to further check if the identity is a match (text)
        onView(withId(R.id.profileText)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText("TESTNAME")));
        onView(withId(R.id.profile_username)).check(matches(withText("Username: test_login")));
        onView(withId(R.id.profile_email)).check(matches(withText("Email: testEMAIL")));
        onView(withId(R.id.profile_uscid)).check(matches(withText("USCid: testUSCID")));
        // logout
        onView(withId(R.id.profile_logout)).perform(click());
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
    }


    @Test
    public void testLoginFail() {
        onView(withId(R.id.username)).perform(typeText("test_login"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test_login_wrong"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.signinbtn)).perform(click());
        // a wrong message prompt & still in login page
        onView(withId(R.id.wrong)).check(matches(withText("Wrong username/password. Try again.")));
        onView(withId(R.id.signin)).check(matches(withText("Sign in")));
        // views in map should not exist
        onView(withId(R.id.profileText)).check(doesNotExist());
        onView(withId(R.id.profileButton)).check(doesNotExist());
        onView(withId(R.id.summarybtn)).check(doesNotExist());
        onView(withId(R.id.button1)).check(doesNotExist());
        onView(withId(R.id.button2)).check(doesNotExist());
    }
}
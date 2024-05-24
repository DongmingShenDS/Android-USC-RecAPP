# USCRecApp_Team28
This is USCRecApp for Team28 README doc

To Run the App: 
    Switch the configuration to "app" (the one with a green Android icon), then click the green arrow "run 'app'" or press ^R
    (Run directly, do not need to build first)
    Or can also run from the "MainActivity" directly

About the Emulator: (minSdk 21, targetSdk 32)
    All emulators with the accepted SDK should work.
    But since we used Pixel 4 API 30 for testing, we suggest using this emulator.

If Emulator connect to WIFI while the computer is connected: Cold Boot the Emulator
    1. Tools -> Device Manager
    2. Virtual Device -> right-most drop-down bar
    3. Click "Cold Boot Now"

There are five existing users in our database currently for the Grader to test our App.
    User 1. username: tigojian, password: 12345678
    User 2. username: xutianch, password: 12345678
    User 3. username: alvinshe, password: 12345678
    User 4. username: user1, password: 12345678
    User 5. username: user2, password: 12345678

These features are for Project 2.3

To Login:
    1. Enter the username and password, then click Login OR press enter on the keyboard

Map Page (Activity):
    1. Click the upper left Profile Icon / View Profile to view the profile
    2. Click on the red buttons on the map to go to the Booking Page of the corresponding recreation center
    3. Click on the window at the bottom of the page to view the booking summary (Note: can slide the window to view different upcoming timeslots)

Booking Page (Activity):
    1. Click the upper left Back to go back to the Map Page
    2. Click the upper right Refresh to refresh the current page (when you stay on the page for a long time and did nothing)
    3. Click the three buttons to select the date to make a reservation (can only select the next three days)
    4. Click Book or Join Waitlist to book a timeslot or join the waitlist as required (NOTE: a user can book at most one timeslot at a center a day)

Summary Page (Activity):
    1. Click the upper left Back to go back to the Map Page
    2. The first part shows the upcoming bookings, while the second part shows the history bookings, each window allows sliding to view more
    3. Click Cancel (for upcoming bookings only) to cancel an upcoming meeting

Profile Page (Activity):
    1. Click the upper left Back to go back to the Map Page
    2. Click the upper right Logout to logout this account (will then be redirected to Login Page)

To Test Notifications & Waitlist (need at least two devices):
    1. Since we set capacity=2 for all timeslots (convenient for testing), need to use two accounts to reserve the same timeslot
    2. Now use a third account to view the same timeslot, the button should now show "join waitlist" with available spot = 0
    3. Use this third account to join the waitlist
    4. Use another device to log in to one of the previous accounts (which booked this timeslot before)
    5. Click the small summary window at the bottom of the Map Page to go into the Summary Page
    6. Cancel the corresponding timeslot
    7. Now, the device with the third account logged in should receive the notification (need an internet connection)

These features are for Project 2.5 as extra functionalities in the Sprint

To Test Open / Close Notifications (need at least two devices):
    1. Since we set capacity=2 for all timeslots (convenient for testing), need to use two accounts to reserve the same timeslot
    2. Now use a third account to view the same timeslot, and join the waitlist
    3. Use the third account, click the upper right corner "notification" symbol on Map Page to go to the notification setting page, then close the notification
    4. Use another device to log in to one of the previous accounts (which booked this timeslot before)
    5. Click the small summary window at the bottom of the Map Page to go into the Summary Page
    6. Cancel the corresponding timeslot
    7. Now, the device with the third account logged in should NOT receive the notification (since it is turned off)
    8. Switch back to the third device, and turn the notification back on
    9. Now, the device with the third account logged in should receive the notification (since it is turned back on)

To Test Notifications on upcoming reservations:
    1. Book an upcoming reservation today
    2. Calculate how many minutes until the start of this reservation, say T minutes until the start
    3. Click the upper right corner "notification" symbol on Map Page to go to the Notification Setting Page
    4. Turn on the notification (if currently off)
    5. Set a time to something less than T minutes, say T-5 minutes, and click confirm
    6. About 5 min later, you will receive a notification about the upcoming reservation (may have -1to1 minute error)
    7. This process can be repeated multiple times to check if changing time works

To Test Notification Preference is Saved:
    1. Go to the Notification Setting Page, and create your own preference (notification on/off, if on how many minutes before next reservation)
    2. Go to the Profile Page, then logout
    3. Log in to the same account, and go to the Notification Setting Page again
    4. Now, you should see the preference as you modified it last time

To Test Change Password:
    1. Go to the Profile Page, then click "change password"
    2. Follow the instructions to set a new password, you must enter the same password twice for confirmation
    3. Login with the new password

To Test Highlight Today's Reservation
    1. Book an upcoming reservation today
    2. Go to the Map Page, looking at the small summary window, the time should be in the color RED
    3. Click the small window to go to the Summary Page. The time should also be in the color RED
# Fitness Tracker App

Company: CODTECH IT SOLUTION

Name: Vaghela Jeet Vijaybhai

Intern Id: CT04DM877

Domain: Android Development

Duration: 4 Weeks 

Mentor: Neela Santhosh Kumar

# Description

The Fitness Tracker App is a light, real-time Android app built in Java via Android Studio. The app uses Android's default step counter sensor to monitor physical activity and offers instant feedback on step count, walking distance, and calories expended. The app is built using a clean, contemporary UI with CardView components to give users a smooth and natural experience.

# Key Features
Step Counter Integration: Utilizes the Sensor.TYPE_STEP_COUNTER from Android's sensor API for accurate step tracking.

Distance Estimation: Translates steps to distance with an average step length of 0.78 meters per step.

Calorie Estimation: Estimates calories expended based on the steps taken (around 0.04 kcal per step).

Reset Functionality: Provides users with the ability to reset the number of steps to zero for a fresh session without having to restart the application.

Permissions Handling: Applies runtime permission requests for ACTIVITY_RECOGNITION to adhere to Android 10 (API 29) and later.

Responsive UI: Live updates and visually appealing CardViews for Steps, Distance, and Calories statistics.

Minimal Dependencies: Native Android solution—no external libraries or APIs needed.

# How It Works
On startup, the app looks for a step counter sensor. If found, it initializes the sensor and listens for real-time events. The app saves the initial value from the sensor and derives the steps walked since app launch (or since reset) by subtracting the initial count and offset.

The UI is updated with each detected step, showing:

Steps Taken

Distance Walked (Steps × 0.78 meters)

Calories Burned (Steps × 0.04 kcal)

There is a Reset button to reset the count during the session. The app cleverly uses an offset such that tracking remains accurate without using persistent storage or app reloads.

# User Interface
The UI is constructed with ScrollView and a LinearLayout which has three colored CardView components stacked vertically:

Light Blue for Steps

Light Green for Distance

Light Orange for Calories

Every card has an icon and text field that is updated in real time. A bold Reset button is placed at the bottom, designed with material design standards for easier user accessibility and visibility.

#Permissions
The app is requesting ACTIVITY_RECOGNITION permission at runtime for devices with Android 10 and later versions. If rejected, it informs the user that the app is unable to work properly without access to body activity data.

# Use Case
This app is ideal for:

Students studying Android sensors and permission handling.

Developers building health or fitness apps.

Individuals who want to see how to implement real-time hardware sensors to trigger UI updates in Android.

# Future Improvements (Ideas)
Progress charts daily/weekly using MPAndroidChart

Step goals and reminders

Saving data persistently using SQLite or Room

Google Fit integration

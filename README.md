# Group 2 - Campus / Home Zone Checker

## Project Purpose
The **Campus / Home Zone Checker** is an Android application designed to determine if a user is within a specific predefined geographical zone (e.g., a university campus). It provides a simple, one-tap interface to fetch the current location, compare it against a fixed reference point, and display the result along with the distance in meters.

## Technologies
- **Language:** Kotlin
- **Platform:** Android
- **Location Services:** Google Play Services FusedLocationProviderClient
- **UI:** XML Layout with Material Components

## How distanceTo() works
The application uses the `android.location.Location.distanceTo(dest: Location)` method provided by the Android framework. This method computes the approximate distance in meters between two locations using the WGS84 ellipsoid. 
1. The **Reference Location** is defined as a fixed Latitude and Longitude.
2. The **Current Location** is fetched via the Fused Location Provider.
3. The distance between these two points is calculated.
4. If `distance <= RADIUS`, the user is "Inside Zone"; otherwise, they are "Outside Zone".

## Emulator Testing Instructions
To test this application on an Android Emulator, follow these steps:

1. **Open Extended Controls:** Click the three dots (...) on the emulator sidebar.
2. **Navigate to Location:** Select the 'Location' tab.
3. **Set Mock Location:**
   - **Test 1 (Inside Zone):** 
     - Set Latitude: `6.9740`
     - Set Longitude: `79.9150`
     - Click 'Send'.
     - In the app, tap **Check My Zone**.
     - **Expected:** "Inside Zone" and "0.0 m".
   - **Test 2 (Outside Zone):** 
     - Set Latitude: `7.0000`
     - Set Longitude: `80.0000`
     - Click 'Send'.
     - In the app, tap **Check My Zone**.
     - **Expected:** "Outside Zone" and a distance > 200m.

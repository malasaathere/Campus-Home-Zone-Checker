# Group 2 - Demo Notes

## Member 1: Foundation & UI
- Established the Android project structure.
- Designed and implemented the Material UI in `activity_main.xml`.
- Integrated Google Play Services dependencies in `build.gradle.kts`.

## Member 2: Runtime Permissions
- Declared necessary location permissions in `AndroidManifest.xml`.
- Implemented the logic for checking and requesting `ACCESS_FINE_LOCATION` at runtime.

## Member 3: Location Retrieval
- Created `LocationHelper.kt`.
- Implemented `fetchCurrentLocation` using `FusedLocationProviderClient` with `PRIORITY_HIGH_ACCURACY`.

## Member 4: Zone Logic
- Created `ZoneChecker.kt`.
- Defined the fixed reference point (Latitude: 1.2966, Longitude: 103.7764) and radius (200m).
- Implemented the `checkZone` logic using `distanceTo()`.

## Member 5: Final Integration & Testing
- Integrated all components in `MainActivity.kt`.
- Connected the "Check My Zone" button to the full workflow (Permission -> Retrieval -> Calculation -> Display).
- Added comprehensive error handling for permission denial and location unavailability.
- Verified distance display formatting and UI feedback.
- Prepared testing documentation and finalized the project for submission.

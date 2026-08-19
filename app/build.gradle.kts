plugins {
    id("com.android.application")
}

android {
    namespace = "com.group2.campuszonechecker"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.group2.campuszonechecker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    // Added now so later members can use FusedLocationProviderClient.
    implementation("com.google.android.gms:play-services-location:21.3.0")
}

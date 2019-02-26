package com.appium;

import io.appium.java_client.android.AndroidDriver;

public class WebDriverPool {

    private static ThreadLocal<AndroidDriver> thread = new ThreadLocal<>();

    public static void setAndroidDriver(AndroidDriver driverInstance) {
        thread.set(driverInstance);
    }

    public static AndroidDriver getAndroidDriver() {
        return thread.get();
    }
}

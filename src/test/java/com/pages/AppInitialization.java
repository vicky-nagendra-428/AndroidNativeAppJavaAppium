package com.pages;

import com.appium.Utils;
import com.appium.WebDriverPool;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AppInitialization {

    public static void launchApp(String device) {

        AndroidDriver<AndroidElement> driver = null;

        //device will be chosen based on the tags given to specification
        HashMap desiredCaps = Utils.getSegment(System.getProperty("user.dir") + "/resources/device_specs.yaml", device);

        //Device Id is important when we have multiple devices with same OS
        //So we are getting device id, after the emulator is started
        String deviceIdToGet = checkEmulatorIsRunning(desiredCaps.get("deviceIdentifier").toString());
        if (deviceIdToGet.isEmpty()) {
            startEmulator(desiredCaps.get("DeviceName").toString());
            deviceIdToGet = checkEmulatorIsRunning(desiredCaps.get("deviceIdentifier").toString());
        }

        if (deviceIdToGet.isEmpty()) {
            return;
        }

        //clearing the app cache before starting the app
        //this step is optional
        clearAppCache(desiredCaps.get("App_Package").toString(), deviceIdToGet);

        //defining the capapbilities for starting the device
        DesiredCapabilities ds = new DesiredCapabilities();
        ds.setCapability(MobileCapabilityType.APPIUM_VERSION, System.getenv("APPIUM_VERSION"));
        ds.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        ds.setCapability(MobileCapabilityType.PLATFORM_VERSION, desiredCaps.get("PlatformVersion"));
        ds.setCapability(MobileCapabilityType.DEVICE_NAME, desiredCaps.get("DeviceName"));
        ds.setCapability(MobileCapabilityType.NO_RESET, true);
        ds.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        ds.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
        ds.setCapability(MobileCapabilityType.UDID, deviceIdToGet.toString());
        ds.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, desiredCaps.get("App_Package"));
        ds.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, desiredCaps.get("App_Activity"));
        //App is not required if the app is already installed on the device
//        ds.setCapability(MobileCapabilityType.APP, desiredCaps.get("App"));
        ds.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, desiredCaps.get("App_Activity"));

        try {
            URL appiumHost = new URL(System.getenv("APPIUM_HOST"));
            driver = new AndroidDriver<AndroidElement>(appiumHost, ds);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        WebDriverPool.setAndroidDriver(driver);
    }

    public static void clearAppCache(String appPackage, String device) {

        try {

            Runtime runTime = Runtime.getRuntime();
            String clearCacheCmd = "adb -s " + device + " shell pm clear " + appPackage;
            runTime.exec(clearCacheCmd);

            System.out.println("Command for clear app cache : " + clearCacheCmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean startEmulator(String deviceName) {

        try {
            System.out.println("Trying to start : " + deviceName);
            String[] commands = {"emulator", "-avd", deviceName};
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb = pb.directory(new File(System.getenv("EMULATOR_HOME")));
            Process process = pb.start();
            Utils.waitForSeconds(3); //giving 3 seconds grace time to start the device, if your computer is low end configuration, device takes time to start

//            BufferedReader bre = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            if (bre.lines().count() > 0) {
//                return false;
//            }

            System.out.println("Emulator command is executed sucessfully");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String checkEmulatorIsRunning(String deviceIdentifier) {

        String deviceId = "";
        Runtime rt = Runtime.getRuntime();
        String commandResult = null;

        try {

            Process process = rt.exec("adb devices -l");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //fetching the device id
            while((commandResult = br.readLine()) != null) {

                if (commandResult.contains(deviceIdentifier)) {
                    System.out.println("Found the device already running : \n" + commandResult);
                    deviceId = commandResult.split(" ")[0];
                    break;
                }
            }
            System.out.println("Reading adb devices output completed : device id is : [" + deviceId + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceId;
    }

    public static void closeTheApp() {
        WebDriverPool.getAndroidDriver().quit();
    }
}

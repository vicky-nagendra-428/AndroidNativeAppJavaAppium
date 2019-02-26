package com.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.net.ServerSocket;

public class Config {

    static int portNumber = Integer.parseInt(System.getenv("APPIUM_START_PORT"));
    static int bootStrapPort = Integer.parseInt(System.getenv("BOOTSTRAP_PORT"));

    public AppiumDriverLocalService startAppium() {

        AppiumDriverLocalService service = null;

        if (checkThePortIsInUse(portNumber)) {
            System.out.println("Port already in use, so not starting appium again");
            return service;
        }

        String nodePath = System.getenv("NODE_PATH");
        String appiumJsPath = System.getenv("APPIUM_JS_PATH");

//        System.setProperty(AppiumServiceBuilder.NODE_PATH, nodePath);
//        System.setProperty(AppiumServiceBuilder.APPIUM_PATH, appiumJsPath);

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .usingDriverExecutable(new File(nodePath))
                .withAppiumJS(new File(appiumJsPath))
                .withIPAddress("0.0.0.0").usingPort(portNumber++)
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(bootStrapPort++))
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        Utils.waitForSeconds(3); //giving time to appium to start
        System.out.println("=====================================>Appium started on port : " + portNumber);
        System.out.println("Starting Appium in start Appium method - starting successful : " + service.isRunning());

        return service;

    }

    private static boolean checkThePortIsInUse(int portNumber) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            serverSocket.close();
        } catch (Exception e) {
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public void stopAppium(AppiumDriverLocalService service) {
        service.stop();
        System.out.println("Stop Appium successful : " + service.isRunning());
    }
}

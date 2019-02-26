package com.appium;

import com.pages.AppInitialization;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.util.List;

public class GaugeLevel {

    AppiumDriverLocalService service = null;
    Config config = new Config();
    static DataStore ds = DataStoreFactory.getSpecDataStore();

    @BeforeSpec
    public void setup(ExecutionContext context) {

        System.out.println("===========Before Spec===========");

        System.out.println("Starting Appium");
        service = config.startAppium();
        ds.put(context.getCurrentSpecification().getName().toString(), service);

        List<String> specTags = context.getCurrentSpecification().getTags();
        String deviceInfo = "";

        for (String tag :  specTags) {
            System.out.println("Tag : " + tag);
            if (tag.contains("device")) {
                deviceInfo = tag.split("-")[1];
                break;
            }
        }

        System.out.println("Device : " + deviceInfo);
        AppInitialization.launchApp(deviceInfo);

    }

    @AfterSpec
    public void closeApp(ExecutionContext context) {
        System.out.println("===========After Spec===========");
        AppInitialization.closeTheApp();
        System.out.println("Stopping Appium");
        config.stopAppium((AppiumDriverLocalService) ds.get(context.getCurrentSpecification().getName().toString()));
    }

}

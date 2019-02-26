package com.pages;

import com.appium.WebDriverPool;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    public AndroidDriver getDriver() {
        return WebDriverPool.getAndroidDriver();
    }

    public void waitForElementVisibility(AndroidElement element, int timeToWait) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeToWait);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementClickability(AndroidElement element, int timeToWait) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeToWait);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean isElementDisplayed(AndroidElement element) {
        try {
            waitForElementVisibility(element, 20);
            return element.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickOnElement(AndroidElement element) {
        try {
            waitForElementClickability(element, 20);
            element.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollElementIntoView(String uiAutomatorIdentiifer) {
        getDriver().findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(" + uiAutomatorIdentiifer + ")");
    }

    public void swipeUp(AndroidElement element) {

        int startX = element.getCenter().x;
        int startY = element.getCenter().y;

        TouchAction ta = new TouchAction(getDriver());
        ta.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(0, startY - 200)).release().perform();
    }

    public void swipeDown(AndroidElement element) {

        int startX = element.getCenter().x;
        int startY = element.getCenter().y;

        TouchAction ta = new TouchAction(getDriver());
        ta.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(0, startY + 200)).release().perform();
    }

}

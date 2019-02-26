package com.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class PropertySearchFilterPage extends BasePage {

    public PropertySearchFilterPage() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    @AndroidFindBy(id = "android:id/action_bar_title")
    private AndroidElement propertySearchFilterPageTitle;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/rbSale")
    private AndroidElement saleSearch;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/rbRent")
    private AndroidElement rentSearch;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/rbRoom")
    private AndroidElement roomSearch;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/rswSelectPropertyType")
    private AndroidElement propertyTypeSection;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/rswSelectLocation")
    private AndroidElement locationSection;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/cbMoreOptions")
    private AndroidElement moreOptionsLink;

    @AndroidFindBy(id = "android:id/alertTitle")
    private AndroidElement propertyTypeAlertTitle;

    @AndroidFindBy(id = "android:id/select_dialog_listview")
    private AndroidElement propertyTypeList;

    @AndroidFindBy(id = "android:id/select_dialog_listview")
    private AndroidElement locationList;

    @AndroidFindBy(id = "android:id/button1")
    private AndroidElement okButtonOnAlert;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/llSecondaryOptions")
    private AndroidElement secondaryOptions;

    @AndroidFindBy(uiAutomator = "text(\"Price\")")
    private AndroidElement priceText;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/wvFrom")
    private AndroidElement priceFrom;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/wvTo")
    private AndroidElement priceTo;

    @AndroidFindBy(id = "com.allproperty.android.consumer.sg:id/btnSearch")
    private AndroidElement searchButton;

    public boolean whenInPropertySearchFilterPage() {
        return isElementDisplayed(propertySearchFilterPageTitle);
    }

    public void selectPropertyType(String propertyType) {
        waitForElementVisibility(propertyTypeSection, 10);
        clickOnElement((AndroidElement)propertyTypeSection.findElementByClassName("android.widget.Button"));
        waitForElementVisibility(propertyTypeAlertTitle, 5);
        clickOnElement((AndroidElement) propertyTypeList.findElementByAndroidUIAutomator("text(\"" + propertyType + "\")"));
    }

    public void clickOnLocationDropdown() {
        waitForElementClickability(locationSection, 4);
        clickOnElement((AndroidElement) locationSection.findElementByClassName("android.widget.Button"));
        waitForElementVisibility(propertyTypeAlertTitle, 4);
    }

    public void selectLocationType(String locationType) {
        clickOnElement((AndroidElement) locationList.findElementByAndroidUIAutomator("text(\"" + locationType + "\")"));
    }

    public void selectDistrictFromDistrictList(String district) {
        waitForElementVisibility(propertyTypeAlertTitle, 5);
        scrollElementIntoView("text(\"" + district + "\")");
        clickOnElement((AndroidElement) locationList.findElementByAndroidUIAutomator("text(\"" + district + "\")"));
    }

    public void clickOnOk() {
        clickOnElement(okButtonOnAlert);
    }

    public void clickOnPriceDropdown() {
        selectSecondaryDropdown("Price");
    }

    private void selectSecondaryDropdown(String dropdownLabel) {

        for (MobileElement element : secondaryOptions.findElementsByXPath("//android.widget.LinearLayout//android.widget.LinearLayout")) {
            if (element.findElementByClassName("android.widget.TextView").getText().equalsIgnoreCase(dropdownLabel)) {
                clickOnElement((AndroidElement) element.findElementByClassName("android.widget.Button"));
                break;
            }
        }
    }

    public void selectPriceFrom() {
        swipeUp(priceFrom);
    }

    public void selectPriceTo() {
        swipeDown(priceTo);
    }

    public void clickOnSearchButton() {
        clickOnElement(searchButton);
    }

}

package com.pages;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    @AndroidFindBy(accessibility = "Property Search")
    private AndroidElement propertySearch;

    @AndroidFindBy(accessibility = "Condo Directory")
    private AndroidElement condoDirectory;

    public boolean checkHomePageIsLoadedSuccessfully() {
        return isElementDisplayed(propertySearch);
    }

    public void enterIntoProeprtySearch() {
        clickOnElement(propertySearch);
    }

}

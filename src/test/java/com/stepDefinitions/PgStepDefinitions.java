package com.stepDefinitions;

import com.pages.HomePage;
import com.pages.PropertySearchFilterPage;
import com.thoughtworks.gauge.Step;

import static org.testng.AssertJUnit.assertTrue;

public class PgStepDefinitions {

    HomePage hp = new HomePage();
    PropertySearchFilterPage propertySearchFilterPage = new PropertySearchFilterPage();

    @Step ("I ensure that pg app is launched successfully")
    public void ensurePgAppIsLaunched() {
        assertTrue("PG home page is not loaded", hp.checkHomePageIsLoadedSuccessfully());
    }

    @Step ("When I am on PG home page")
    public void whenIamOnPgHomePage() {
        assertTrue("PG home page is not loaded", hp.checkHomePageIsLoadedSuccessfully());
    }

    @Step ("I click on Property Search")
    public void clickOnPropertySearch() {
        hp.enterIntoProeprtySearch();
    }

    @Step ("When I am on Property Search Filters Page")
    public void iamOnPropertySearchFiltersPage() {
        propertySearchFilterPage.whenInPropertySearchFilterPage();
    }

    @Step ("click on property type dropdown and select <propertyType>")
    public void selectPropertyType(String propertyType) {
        propertySearchFilterPage.selectPropertyType(propertyType);
    }

    @Step ("click on location dropdown")
    public void clickOnLocationDropdown() {
        propertySearchFilterPage.clickOnLocationDropdown();
    }

    @Step ("on location popup select find <locationType>")
    public void selectLocationTypeOnLocationPopup(String localtionType) {
        propertySearchFilterPage.selectLocationType(localtionType);
    }

    @Step ("on by district popup select <district>")
    public void selectDistrictFromDistrictPopup(String district) {
        propertySearchFilterPage.selectDistrictFromDistrictList(district);
    }

    @Step ("click on ok")
    public void clickOnOk() {
        propertySearchFilterPage.clickOnOk();
    }

    @Step ("Click on price dropdown")
    public void clickOnPriceDropdown() {
        propertySearchFilterPage.clickOnPriceDropdown();
    }

    @Step ("on price selection popup select min price")
    public void selectMinPrice() {
        propertySearchFilterPage.selectPriceFrom();
    }

    @Step ("on price selection popup select max price")
    public void selectMaxPrice() {
        propertySearchFilterPage.selectPriceTo();
    }

    @Step ("click on searchButton")
    public void clickOnSearchButton() {
        propertySearchFilterPage.clickOnSearchButton();
    }
}

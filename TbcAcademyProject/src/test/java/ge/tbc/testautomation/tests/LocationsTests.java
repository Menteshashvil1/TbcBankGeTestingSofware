package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.base.BaseTest;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.steps.LocationsSteps;
import ge.tbc.testautomation.utils.Retry;
import ge.tbc.testautomation.utils.RetryAnalyzer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocationsTests extends BaseTest {
    private LocationsSteps locationsSteps;

    @BeforeMethod(alwaysRun = true)
    public void openLocations() {
        locationsSteps = new LocationsSteps();
        locationsSteps.openLocationsPage();
    }

    @Test(description = "Filtering by ATMs returns only ATM locations")
    public void filterByAtmTypeShouldReturnOnlyAtms() {
        locationsSteps
                .selectTab(Constants.TAB_ATMS)
                .tabShouldBeActive(Constants.TAB_ATMS)
                .resultTypeShouldBe(Constants.ICON_ATM);
    }

    @Test(description = "Filtering by Branches returns only branch locations")
    public void filterByBranchTypeShouldReturnOnlyBranches() {
        locationsSteps
                .selectTab(Constants.TAB_BRANCHES)
                .tabShouldBeActive(Constants.TAB_BRANCHES)
                .resultTypeShouldBe(Constants.ICON_BRANCH);
    }

    @Test(description = "Selecting a city narrows the nationwide list down to that city")
    public void selectingCityShouldNarrowResults() {
        int nationwideCount = locationsSteps.resultCount();

        locationsSteps
                .selectCity(TestData.CITY_TBILISI)
                .selectedCityShouldBe(TestData.CITY_TBILISI)
                .resultCountShouldBeLessThan(nationwideCount)
                .firstResultShouldExist();
    }

    @Test(description = "Searching by street name returns only matching locations")
    public void searchingByStreetShouldReturnMatchingLocationsOnly() {
        locationsSteps
                .selectCity(TestData.CITY_TBILISI)
                .selectedCityShouldBe(TestData.CITY_TBILISI)
                .searchForLocation(TestData.SEARCH_TERM_RUSTAVELI)
                .everyResultTitleShouldContain(TestData.SEARCH_TERM_RUSTAVELI);
    }

    @Test(description = "The map renders location markers alongside the list",
            retryAnalyzer = RetryAnalyzer.class)
    @Retry(count = 1)
    public void mapShouldRenderLocationMarkers() {
        locationsSteps
                .mapShouldBeVisible()
                .mapShouldRenderMarkers();
    }
}

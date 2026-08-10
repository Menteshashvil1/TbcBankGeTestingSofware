package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.base.BaseTest;
import ge.tbc.testautomation.steps.LocationsSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LocationsTests extends BaseTest {

    private LocationsSteps locationsSteps;

    @BeforeMethod
    public void beforeMethod() {
        locationsSteps = new LocationsSteps();
        locationsSteps.openLocationsPage();
    }

    @Test
    public void filterByTypeShouldShowOnlyMatchingResults() {
        locationsSteps
                .clickTab("ATMs")
                .tabShouldBeActive("ATMs")
                .allResultsShouldMatchType("ATM");
    }

    @Test
    public void filterByCityShouldShowResults() {
        locationsSteps
                .selectCity("Tbilisi")
                 .resultsShouldNotBeEmpty();
    }
}
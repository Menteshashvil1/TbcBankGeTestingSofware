package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.pages.LocationsPage;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.testng.Assert.assertTrue;

public class LocationsSteps {
    private final LocationsPage locationsPage = new LocationsPage();
    private final CommonSteps commonSteps = new CommonSteps();

    public LocationsSteps openLocationsPage() {
        commonSteps.openPage(Constants.LOCATIONS_URL);
        locationsPage.root.shouldBe(visible);
        resultsShouldSettle();
        return this;
    }

    public LocationsSteps resultsShouldSettle() {
        locationsPage.resultItems.shouldHave(
                CollectionCondition.sizeGreaterThan(0), TestData.DATA_REFRESH_TIMEOUT);
        return this;
    }

    public LocationsSteps selectTab(String label) {
        locationsPage.tab(label).shouldBe(visible).click();
        return this;
    }

    public LocationsSteps tabShouldBeActive(String label) {
        locationsPage.tab(label).shouldHave(cssClass(Constants.LOCATIONS_TAB_ACTIVE_CLASS));
        return this;
    }

    public LocationsSteps resultTypeShouldBe(String expectedIconName) {
        resultsShouldSettle();
        assertTrue(locationsPage.resultIcons.size() > 0,
                "Expected at least one result to verify the type filter against");

        for (int i = 0; i < TestData.RESULT_SAMPLE_SIZE; i++) {
            if (i >= locationsPage.resultIcons.size()) {
                break;
            }
            locationsPage.resultIcons.get(i).shouldHave(text(expectedIconName));
        }
        return this;
    }

    public int resultCount() {
        return locationsPage.resultItems.size();
    }

    public LocationsSteps selectCity(String cityName) {
        locationsPage.cityDropdownButton.shouldBe(visible).click();
        locationsPage.cityOptions.shouldHave(
                CollectionCondition.sizeGreaterThan(0), TestData.OPTIONAL_ELEMENT_TIMEOUT);
        locationsPage.cityOption(cityName).shouldBe(visible).click();
        return this;
    }

    public LocationsSteps selectedCityShouldBe(String cityName) {
        locationsPage.citySelectedText.shouldHave(text(cityName));
        return this;
    }

    public LocationsSteps resultCountShouldBeLessThan(int previousCount) {
        locationsPage.resultItems.shouldHave(
                CollectionCondition.sizeLessThan(previousCount), TestData.DATA_REFRESH_TIMEOUT);
        return this;
    }

    public LocationsSteps searchForLocation(String term) {
        locationsPage.searchInput.shouldBe(visible).setValue(term);
        return this;
    }

    public LocationsSteps everyResultTitleShouldContain(String term) {
        resultsShouldSettle();
        locationsPage.resultTitles.shouldHave(
                CollectionCondition.allMatch(
                        "title containing '" + term + "'",
                        element -> element.getText().toLowerCase().contains(term.toLowerCase())),
                TestData.DATA_REFRESH_TIMEOUT);
        return this;
    }

    public LocationsSteps mapShouldBeVisible() {
        locationsPage.map.shouldBe(visible);
        return this;
    }

    public LocationsSteps mapShouldRenderMarkers() {
        locationsPage.mapMarkers.shouldHave(
                CollectionCondition.sizeGreaterThan(0), TestData.DATA_REFRESH_TIMEOUT);
        return this;
    }

    public LocationsSteps firstResultShouldExist() {
        SelenideElement first = locationsPage.resultItems.first();
        first.shouldBe(exist);
        locationsPage.resultTitles.first().shouldNotBe(Condition.empty);
        return this;
    }
}

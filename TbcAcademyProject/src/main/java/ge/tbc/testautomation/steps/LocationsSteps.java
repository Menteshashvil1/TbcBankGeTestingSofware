package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.pages.LocationsPage;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;

public class LocationsSteps {

    private final LocationsPage locationsPage = new LocationsPage();
    private final CommonSteps commonSteps = new CommonSteps();

    public LocationsSteps openLocationsPage() {
        Selenide.open(Constants.LOCATIONS_URL);
        commonSteps.acceptCookiesIfPresent();
        return this;
    }

    public LocationsSteps selectCity(String cityName) {
        locationsPage.cityDropdownButton().click();
        locationsPage.cityOption(cityName).click();
        return this;
    }

    public LocationsSteps clickTab(String label) {
        locationsPage.tab(label).click();
        return this;
    }

    public LocationsSteps tabShouldBeActive(String label) {
        locationsPage.tab(label).shouldHave(cssClass(Constants.LOCATIONS_TAB_ACTIVE_CLASS));
        return this;
    }

    public LocationsSteps allResultsShouldMatchType(String expectedTypeText) {
        for (SelenideElement item : locationsPage.resultItems().asFixedIterable()) {
            locationsPage.resultDescription(item).shouldHave(text(expectedTypeText));
        }
        return this;
    }

    public LocationsSteps resultsShouldNotBeEmpty(){
        locationsPage.resultItems().shouldHave(CollectionCondition.sizeGreaterThan(0));
        return this;
    }


}
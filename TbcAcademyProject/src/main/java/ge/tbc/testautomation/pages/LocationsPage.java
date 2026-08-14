package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LocationsPage extends CommonPage {
    public SelenideElement root = $(Constants.LOCATIONS_ROOT);

    public SelenideElement cityDropdownButton = $(Constants.LOCATIONS_CITY_DROPDOWN_BUTTON);
    public SelenideElement citySelectedText = $(Constants.LOCATIONS_CITY_SELECTED_TEXT);
    public ElementsCollection cityOptions = $$(Constants.LOCATIONS_CITY_OPTION);

    public SelenideElement searchInput = $(Constants.LOCATIONS_SEARCH_INPUT);

    public ElementsCollection tabs = $$(Constants.LOCATIONS_TAB_BUTTON);

    public ElementsCollection resultItems = $$(Constants.LOCATIONS_RESULT_ITEM);
    public ElementsCollection resultTitles = $$(Constants.LOCATIONS_RESULT_TITLE);
    public ElementsCollection resultDescriptions = $$(Constants.LOCATIONS_RESULT_DESCRIPTION);
    public ElementsCollection resultIcons = $$(Constants.LOCATIONS_RESULT_ICON);

    public SelenideElement map = $(Constants.LOCATIONS_MAP);
    public ElementsCollection mapMarkers = $$(Constants.LOCATIONS_MAP_MARKER);

    public SelenideElement tab(String label) {
        return tabs.findBy(exactText(label));
    }

    public SelenideElement cityOption(String cityName) {
        return cityOptions.findBy(text(cityName));
    }
}

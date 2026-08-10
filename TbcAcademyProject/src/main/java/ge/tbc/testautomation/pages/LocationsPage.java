package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LocationsPage {

    public SelenideElement cityDropdownButton() {
        return $(Constants.LOCATIONS_CITY_DROPDOWN_BUTTON);
    }

    public SelenideElement cityOption(String cityName) {
        return $$(Constants.LOCATIONS_CITY_OPTION).findBy(text(cityName));
    }

    public SelenideElement tab(String label) {
        return $$(Constants.LOCATIONS_TAB_BUTTON).findBy(text(label));
    }

    public ElementsCollection resultItems() {
        return $$(Constants.LOCATIONS_RESULT_ITEM);
    }

    public SelenideElement resultDescription(SelenideElement item) {
        return item.$(Constants.LOCATIONS_RESULT_DESCRIPTION);
    }


}

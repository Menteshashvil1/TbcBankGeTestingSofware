package ge.tbc.testautomation.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CommonPage {
    public SelenideElement cookieBanner = $(Constants.COOKIE_CONSENT_BANNER);
    public ElementsCollection cookieButtons = $$(Constants.COOKIE_CONSENT_BUTTON);

    public SelenideElement header = $(Constants.HEADER);
    public SelenideElement desktopNavigation = $(Constants.HEADER_NAVIGATION);
    public ElementsCollection desktopNavigationItems = $$(Constants.HEADER_NAVIGATION_ITEM);
    public SelenideElement burgerButton = $(Constants.HEADER_BURGER_BUTTON);
    public SelenideElement megaMenu = $(Constants.MEGA_MENU);
    public ElementsCollection megaMenuLinks = $$(Constants.MEGA_MENU_LINK);

    public SelenideElement breadcrumbs = $(Constants.BREADCRUMBS);
    public ElementsCollection breadcrumbItems = $$(Constants.BREADCRUMB_ITEM);

    public SelenideElement breadcrumbFor(String href) {
        return breadcrumbItems.findBy(Condition.attributeMatching("innerHTML", "(?s).*href=\"" + href + "\".*"));
    }

    public ElementsCollection visibleBreadcrumbs() {
        return breadcrumbItems.filterBy(Condition.visible);
    }
}

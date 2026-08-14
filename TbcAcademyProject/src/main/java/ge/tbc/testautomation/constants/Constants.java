package ge.tbc.testautomation.constants;

public class Constants {
    private Constants() {
    }

    public static final String BASE_URL = "https://www.tbcbank.ge";
    public static final String HOME_URL = BASE_URL + "/en";
    public static final String LOCATIONS_URL = BASE_URL + "/en/atms&branches";
    public static final String CURRENCY_URL = BASE_URL + "/en/valutis-kursi";
    public static final String MONEY_TRANSFERS_URL = BASE_URL + "/en/other-products/money-transfers";

    public static final String COOKIE_CONSENT_BANNER = ".tbcx-pw-cookie-consent";
    public static final String COOKIE_CONSENT_BUTTON = ".tbcx-pw-cookie-consent button";
    public static final String COOKIE_REJECT_ALL_TEXT = "REJECT ALL";

    public static final String HEADER = "tbcx-pw-header";
    public static final String HEADER_NAVIGATION = "tbcx-pw-navigation";
    public static final String HEADER_NAVIGATION_ITEM = "tbcx-pw-navigation .tbcx-pw-navigation-item__link";
    public static final String HEADER_BURGER_BUTTON = ".tbcx-pw-hamburger-menu__button";
    public static final String MEGA_MENU = "tbcx-pw-mega-menu";
    public static final String MEGA_MENU_OPEN_CLASS = "tbcx-pw-mega-menu--open";
    public static final String MEGA_MENU_LINK = "tbcx-pw-mega-menu a";

    public static final String BREADCRUMBS = "tbcx-pw-breadcrumbs";
    public static final String BREADCRUMB_ITEM = ".tbcx-pw-breadcrumbs__item";

    public static final String LOCATIONS_ROOT = "app-atm-branches";
    public static final String LOCATIONS_CITY_DROPDOWN_BUTTON = "tbcx-dropdown-selector button";
    public static final String LOCATIONS_CITY_SELECTED_TEXT = ".tbcx-selection-text";
    public static final String LOCATIONS_CITY_OPTION = "tbcx-dropdown-popover-item";
    public static final String LOCATIONS_SEARCH_INPUT = "tbcx-search-input input.search-input";
    public static final String LOCATIONS_TAB_BUTTON = "tbcx-pw-tab-menu button.tbcx-pw-tab-menu__item";
    public static final String LOCATIONS_TAB_ACTIVE_CLASS = "active";
    public static final String LOCATIONS_RESULT_ITEM = "app-atm-branches-section-list-item";
    public static final String LOCATIONS_RESULT_TITLE = ".tbcx-pw-atm-branches-section__list-item-title";
    public static final String LOCATIONS_RESULT_DESCRIPTION = ".tbcx-pw-atm-branches-section__list-item-description";

    public static final String LOCATIONS_RESULT_ICON =
            ".tbcx-pw-atm-branches-section__list-item-icon tbcx-icon";
    public static final String ICON_ATM = "atm-outlined";
    public static final String ICON_BRANCH = "bank-outlined";
    public static final String LOCATIONS_MAP = "app-atm-branches-section-map";
    public static final String LOCATIONS_MAP_MARKER = "gmp-advanced-marker";

    public static final String TAB_ALL = "All";
    public static final String TAB_ATMS = "ATMs";
    public static final String TAB_BRANCHES = "Branches";
    public static final String TAB_CDMS = "CDMs";

    public static final String CURRENCY_SELL_INPUT = "#sell-amount";
    public static final String CURRENCY_BUY_INPUT = "#buy-amount";
    public static final String CURRENCY_DROPDOWN_TRIGGER = ".currency-dropdown__trigger";
    public static final String CURRENCY_DROPDOWN_SELECTED = ".currency-dropdown__selected";
    public static final String CURRENCY_SWAP_BUTTON = ".currency-calculator__swap__button";
    public static final String CURRENCY_RATE_DESCRIPTION = "p.exchange-rates-calculator__description";

    public static final String FAQ_ITEM = "tbcx-pw-accordion-section tbcx-pw-accordion-item";
    public static final String FAQ_TITLE = ".tbc-accordion__title";
    public static final String FAQ_BODY = ".tbc-accordion__wrapper";
    public static final String FAQ_ROOT = ".tbc-accordion";
    public static final String FAQ_EXPANDED_CLASS = "tbc-accordion--expanded";
}

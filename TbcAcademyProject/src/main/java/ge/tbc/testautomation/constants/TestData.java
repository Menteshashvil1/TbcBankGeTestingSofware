package ge.tbc.testautomation.constants;

import java.time.Duration;

public class TestData {
    private TestData() {
    }

    public static final long DEFAULT_TIMEOUT_MS = 15_000;
    public static final long PAGE_LOAD_TIMEOUT_MS = 60_000;

    public static final Duration OPTIONAL_ELEMENT_TIMEOUT = Duration.ofSeconds(8);
    public static final Duration DATA_REFRESH_TIMEOUT = Duration.ofSeconds(25);

    public static final String CITY_TBILISI = "Tbilisi";
    public static final String CITY_BATUMI = "Batumi";
    public static final String SEARCH_TERM_RUSTAVELI = "Rustaveli";

    public static final int RESULT_SAMPLE_SIZE = 15;

    public static final String CURRENCY_USD = "USD";
    public static final String CURRENCY_GEL = "GEL";
    public static final String CONVERSION_AMOUNT = "100";
    public static final String USD_TO_GEL_URL_PART = "USD-to-GEL";
    public static final String GEL_TO_USD_URL_PART = "GEL-to-USD";

    public static final String MONEY_TRANSFERS_HEADING = "Quick money transfers";
    public static final String MONEY_TRANSFERS_HREF = "/en/other-products/money-transfers";
    public static final String OTHER_PRODUCTS_HREF = "/en/other-products";
}

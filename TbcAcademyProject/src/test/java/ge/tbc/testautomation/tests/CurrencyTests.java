package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.base.BaseTest;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.steps.CurrencySteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CurrencyTests extends BaseTest {
    private CurrencySteps currencySteps;

    @BeforeMethod(alwaysRun = true)
    public void openCurrencyPage() {
        currencySteps = new CurrencySteps();
        currencySteps.openCurrencyPage();
    }

    @Test(description = "Swapping currencies inverts the conversion direction and the URL")
    public void swappingCurrenciesShouldInvertConversionDirection() {
        currencySteps
                .defaultDirectionShouldBeUsdToGel()
                .conversionDirectionShouldBe(TestData.USD_TO_GEL_URL_PART)
                .swapCurrencies()
                .sellCurrencyShouldBe(TestData.CURRENCY_GEL)
                .buyCurrencyShouldBe(TestData.CURRENCY_USD)
                .conversionDirectionShouldBe(TestData.GEL_TO_USD_URL_PART);
    }

    @Test(description = "The converted amount agrees with the rate the page quotes")
    public void convertedAmountShouldAgreeWithQuotedRate() {
        currencySteps
                .calculatorShouldBeUsable()
                .enterSellAmount(TestData.CONVERSION_AMOUNT)
                .sellAmountShouldBe(TestData.CONVERSION_AMOUNT)
                .buyAmountShouldNotBeEmpty()
                .rateDescriptionShouldMention(TestData.CURRENCY_USD, TestData.CURRENCY_GEL)
                .convertedAmountShouldMatchQuotedRate(
                        Double.parseDouble(TestData.CONVERSION_AMOUNT));
    }
}

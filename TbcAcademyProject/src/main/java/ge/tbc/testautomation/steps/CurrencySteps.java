package ge.tbc.testautomation.steps;

import com.codeborne.selenide.Condition;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.pages.CurrencyPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CurrencySteps {
    private final CurrencyPage currencyPage = new CurrencyPage();
    private final CommonSteps commonSteps = new CommonSteps();

    public CurrencySteps openCurrencyPage() {
        commonSteps.openPage(Constants.CURRENCY_URL);
        currencyPage.sellAmountInput.shouldBe(visible);
        return this;
    }

    public CurrencySteps enterSellAmount(String amount) {
        currencyPage.sellAmountInput.shouldBe(visible).setValue(amount);
        return this;
    }

    public CurrencySteps swapCurrencies() {
        currencyPage.swapButton.shouldBe(visible).click();
        return this;
    }

    public CurrencySteps sellCurrencyShouldBe(String code) {
        currencyPage.sellCurrency().shouldHave(exactText(code));
        return this;
    }

    public CurrencySteps buyCurrencyShouldBe(String code) {
        currencyPage.buyCurrency().shouldHave(exactText(code));
        return this;
    }

    public CurrencySteps conversionDirectionShouldBe(String urlPart) {
        commonSteps.currentUrlShouldContain(urlPart);
        return this;
    }

    public CurrencySteps buyAmountShouldNotBeEmpty() {
        currencyPage.buyAmountInput.shouldNotBe(Condition.empty);
        assertTrue(parsedBuyAmount() > 0,
                "Expected a positive converted amount but the Buy field read '"
                        + currencyPage.buyAmountInput.getValue() + "'");
        return this;
    }

    public CurrencySteps rateDescriptionShouldMention(String from, String to) {
        currencyPage.rateDescription
                .shouldBe(visible)
                .shouldHave(Condition.matchText("1\\s*" + from + "\\s*=\\s*[\\d.,]+\\s*" + to));
        return this;
    }

    public CurrencySteps convertedAmountShouldMatchQuotedRate(double sellAmount) {
        double rate = parsedRate();
        double actual = parsedBuyAmount();
        double expected = sellAmount * rate;
        double tolerance = Math.max(0.5, expected * 0.01);

        assertTrue(Math.abs(actual - expected) <= tolerance,
                "Converted amount does not match the quoted rate. sell=" + sellAmount
                        + ", quoted rate=" + rate + ", expected~" + expected + ", actual=" + actual);
        return this;
    }

    public CurrencySteps sellAmountShouldBe(String expected) {
        assertEquals(currencyPage.sellAmountInput.getValue(), expected,
                "Sell amount was not preserved");
        return this;
    }

    private double parsedBuyAmount() {
        String raw = currencyPage.buyAmountInput.getValue();
        return Double.parseDouble(raw == null ? "0" : raw.replace(",", "").trim());
    }

    private double parsedRate() {
        String caption = currencyPage.rateDescription.shouldBe(visible).getText();
        String[] parts = caption.split("=");
        assertTrue(parts.length == 2, "Unexpected rate caption format: '" + caption + "'");
        String number = parts[1].replaceAll("[^0-9.]", "");
        return Double.parseDouble(number);
    }

    public CurrencySteps calculatorShouldBeUsable() {
        currencyPage.sellAmountInput.shouldBe(visible);
        currencyPage.buyAmountInput.shouldBe(visible);
        currencyPage.swapButton.shouldBe(visible);
        currencyPage.currencyTriggers.shouldHave(
                com.codeborne.selenide.CollectionCondition.size(2));
        return this;
    }

    public CurrencySteps defaultDirectionShouldBeUsdToGel() {
        sellCurrencyShouldBe(TestData.CURRENCY_USD);
        buyCurrencyShouldBe(TestData.CURRENCY_GEL);
        return this;
    }
}

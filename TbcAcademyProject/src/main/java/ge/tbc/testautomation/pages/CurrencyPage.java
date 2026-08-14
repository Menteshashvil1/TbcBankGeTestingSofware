package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CurrencyPage extends CommonPage {
    public SelenideElement sellAmountInput = $(Constants.CURRENCY_SELL_INPUT);
    public SelenideElement buyAmountInput = $(Constants.CURRENCY_BUY_INPUT);
    public SelenideElement swapButton = $(Constants.CURRENCY_SWAP_BUTTON);
    public SelenideElement rateDescription = $(Constants.CURRENCY_RATE_DESCRIPTION);

    public ElementsCollection currencyTriggers = $$(Constants.CURRENCY_DROPDOWN_TRIGGER);
    public ElementsCollection selectedCurrencies = $$(Constants.CURRENCY_DROPDOWN_SELECTED);

    public SelenideElement sellCurrency() {
        return selectedCurrencies.get(0);
    }

    public SelenideElement buyCurrency() {
        return selectedCurrencies.get(1);
    }
}

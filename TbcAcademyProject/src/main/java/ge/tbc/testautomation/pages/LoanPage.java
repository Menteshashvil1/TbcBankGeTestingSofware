package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoanPage {

    public SelenideElement moneyInputField(){
        return $("input#sell-amount");
    }

    public SelenideElement moneyOutputField(){
        return $("input#buy-amount");
    }

    public SelenideElement swapButton() {
        return $("button.currency-calculator__swap_button");
    }

    public SelenideElement sellCurrencyDropdown() {
        return  $$("button.currency-dropdown__trigger").get(0);
    }

    public SelenideElement buyCurrencyDropdown() {
        return $$("button.currency-dropdown__trigger").get(1);
    }

}

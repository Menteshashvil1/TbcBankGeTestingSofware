package ge.tbc.testautomation.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MoneyTransfersPage extends CommonPage {
    public SelenideElement heading = $("h1");

    public ElementsCollection faqItems = $$(Constants.FAQ_ITEM);

    public SelenideElement faqTitle(SelenideElement faqItem) {
        return faqItem.$(Constants.FAQ_TITLE);
    }

    public SelenideElement faqBody(SelenideElement faqItem) {
        return faqItem.$(Constants.FAQ_BODY);
    }

    public SelenideElement faqRoot(SelenideElement faqItem) {
        return faqItem.$(Constants.FAQ_ROOT);
    }
}

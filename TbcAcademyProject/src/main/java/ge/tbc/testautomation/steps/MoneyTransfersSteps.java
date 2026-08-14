package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.pages.MoneyTransfersPage;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MoneyTransfersSteps {
    private final MoneyTransfersPage page = new MoneyTransfersPage();
    private final CommonSteps commonSteps = new CommonSteps();

    public MoneyTransfersSteps openMoneyTransfersPage() {
        commonSteps.openPage(Constants.MONEY_TRANSFERS_URL);
        page.heading.shouldBe(visible);
        return this;
    }

    public MoneyTransfersSteps headingShouldBe(String expected) {
        page.heading.shouldHave(text(expected));
        return this;
    }

    public MoneyTransfersSteps faqSectionShouldBePresent() {
        page.faqItems.shouldHave(
                CollectionCondition.sizeGreaterThan(0), TestData.DATA_REFRESH_TIMEOUT);
        return this;
    }

    public MoneyTransfersSteps firstQuestionShouldBeCollapsed() {
        page.faqRoot(page.faqItems.first()).shouldNotHave(cssClass(Constants.FAQ_EXPANDED_CLASS));
        return this;
    }

    public MoneyTransfersSteps expandFirstQuestion() {
        SelenideElement first = page.faqItems.first();
        commonSteps.scrollTo(first);
        page.faqTitle(first).shouldBe(visible).click();
        return this;
    }

    public MoneyTransfersSteps firstAnswerShouldBeVisible() {
        SelenideElement first = page.faqItems.first();
        page.faqRoot(first).shouldHave(cssClass(Constants.FAQ_EXPANDED_CLASS));
        page.faqBody(first).shouldBe(visible);
        return this;
    }
}

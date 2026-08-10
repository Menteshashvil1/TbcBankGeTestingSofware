package ge.tbc.testautomation.steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.pages.CommonPage;

public class CommonSteps {

    private final CommonPage commonPage = new CommonPage();

    public void acceptCookiesIfPresent() {
        try {
            commonPage.cookieAcceptButton().shouldBe(Condition.visible, java.time.Duration.ofSeconds(8)).click();
        } catch (Throwable ignored) {
        }
    }

    public void closeSurveyPopupIfPresent() {
        try {
            commonPage.surveyPopupCloseButton().shouldBe(Condition.visible, java.time.Duration.ofSeconds(8)).click();
        } catch (Throwable ignored) {
        }
    }

    public void scrollByPixels(int pixels) {
        Selenide.executeJavaScript("window.scrollBy(0, " + pixels + ")");
    }
}

package ge.tbc.testautomation.pages;

import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;

import static com.codeborne.selenide.Selenide.$;

public class CommonPage {

    public SelenideElement cookieAcceptButton() {
        return $(Constants.COOKIE_ACCEPT_BUTTON);
    }

    public SelenideElement surveyPopupCloseButton() {
        return $(Constants.SURVEY_POPUP_CLOSE_BUTTON);
    }
}

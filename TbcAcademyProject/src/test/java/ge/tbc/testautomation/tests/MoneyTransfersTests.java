package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.base.BaseTest;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.steps.CommonSteps;
import ge.tbc.testautomation.steps.MoneyTransfersSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MoneyTransfersTests extends BaseTest {
    private MoneyTransfersSteps moneyTransfersSteps;
    private CommonSteps commonSteps;

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setUpBrowser")
    public void initSteps() {
        moneyTransfersSteps = new MoneyTransfersSteps();
        commonSteps = new CommonSteps();
    }

    @Test(description = "The money transfers page loads with the breadcrumb affordance its profile uses")
    public void moneyTransfersPageShouldLoadWithBreadcrumbs() {
        moneyTransfersSteps
                .openMoneyTransfersPage()
                .headingShouldBe(TestData.MONEY_TRANSFERS_HEADING);

        commonSteps.breadcrumbTrailShouldMatchPlatform(
                TestData.MONEY_TRANSFERS_HREF, TestData.OTHER_PRODUCTS_HREF);
    }

    @Test(description = "A FAQ entry expands to reveal its answer",
            dependsOnMethods = "moneyTransfersPageShouldLoadWithBreadcrumbs")
    public void faqEntryShouldExpandToRevealAnswer() {
        moneyTransfersSteps
                .faqSectionShouldBePresent()
                .firstQuestionShouldBeCollapsed()
                .expandFirstQuestion()
                .firstAnswerShouldBeVisible();
    }
}

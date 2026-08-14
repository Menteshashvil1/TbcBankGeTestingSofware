package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.base.BaseTest;
import ge.tbc.testautomation.constants.BrowserConstants;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.steps.CommonSteps;
import ge.tbc.testautomation.utils.Platform;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResponsiveNavigationTests extends BaseTest {
    private CommonSteps commonSteps;

    @BeforeMethod(alwaysRun = true)
    public void openHomePage() {
        commonSteps = new CommonSteps();
        commonSteps.openPage(Constants.HOME_URL);
    }

    @Test(description = "The browser really is running at the resolution its profile requires")
    public void viewportShouldMatchExecutionProfile() {
        commonSteps.viewportShouldMatchProfile(
                Platform.isMobile() ? BrowserConstants.MOBILE_WIDTH : BrowserConstants.DESKTOP_WIDTH);
    }

    @Test(description = "Header shows the nav strip on desktop and the burger on mobile")
    public void headerNavigationShouldAdaptToViewport() {
        commonSteps.headerNavigationShouldMatchPlatform();
    }

    @Test(description = "Mobile: the burger opens the menu and exposes the product links")
    public void burgerMenuShouldExposeProductNavigationOnMobile() {
        if (!Platform.isMobile()) {
            throw new org.testng.SkipException(
                    "Burger navigation does not exist on the desktop profile - covered by "
                            + "headerNavigationShouldAdaptToViewport instead");
        }
        commonSteps
                .openBurgerMenu()
                .megaMenuShouldBeOpen()
                .megaMenuShouldContainLink("/en/loans")
                .megaMenuShouldContainLink("/en/atms&branches");
    }

    @Test(description = "The home page fits its viewport without horizontal scrolling")
    public void homePageShouldNotOverflowHorizontally() {
        commonSteps.pageShouldNotScrollHorizontally();
    }
}

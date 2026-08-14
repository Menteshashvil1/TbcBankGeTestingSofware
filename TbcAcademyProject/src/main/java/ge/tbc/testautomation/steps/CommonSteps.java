package ge.tbc.testautomation.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ge.tbc.testautomation.constants.Constants;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.pages.CommonPage;
import ge.tbc.testautomation.utils.Platform;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertTrue;

public class CommonSteps {
    private final CommonPage commonPage = new CommonPage();

    public CommonSteps openPage(String url) {
        open(url);
        dismissCookieBannerIfPresent();
        return this;
    }

    public CommonSteps dismissCookieBannerIfPresent() {
        SelenideElement banner = commonPage.cookieBanner;
        if (!banner.is(visible, TestData.OPTIONAL_ELEMENT_TIMEOUT)) {
            return this;
        }
        commonPage.cookieButtons
                .findBy(Condition.text(Constants.COOKIE_REJECT_ALL_TEXT))
                .shouldBe(visible)
                .click();
        banner.shouldNotBe(visible, TestData.OPTIONAL_ELEMENT_TIMEOUT);
        return this;
    }

    public CommonSteps headerNavigationShouldMatchPlatform() {
        if (Platform.isMobile()) {
            commonPage.burgerButton.shouldBe(visible);
            commonPage.desktopNavigation.shouldNotBe(visible);
        } else {
            commonPage.desktopNavigation.shouldBe(visible);
            commonPage.desktopNavigationItems.first().shouldBe(visible);
            commonPage.burgerButton.shouldNotBe(visible);
        }
        return this;
    }

    public CommonSteps openBurgerMenu() {
        commonPage.burgerButton.shouldBe(visible).click();
        commonPage.megaMenu.shouldBe(visible);
        return this;
    }

    public CommonSteps megaMenuShouldBeOpen() {
        commonPage.megaMenu.shouldBe(visible)
                .$("div." + Constants.MEGA_MENU_OPEN_CLASS)
                .shouldBe(visible);
        return this;
    }

    public CommonSteps megaMenuShouldContainLink(String href) {
        commonPage.megaMenuLinks
                .findBy(Condition.attributeMatching("href", ".*" + href + ".*"))
                .shouldBe(Condition.exist);
        return this;
    }

    public CommonSteps breadcrumbsShouldBeVisible() {
        commonPage.breadcrumbs.shouldBe(visible);
        return this;
    }

    public CommonSteps breadcrumbTrailShouldMatchPlatform(String currentPageHref, String parentHref) {
        commonPage.breadcrumbs.shouldBe(visible);
        commonPage.breadcrumbFor(currentPageHref).shouldBe(Condition.exist);

        if (Platform.isMobile()) {
            commonPage.visibleBreadcrumbs().shouldHave(CollectionCondition.size(1));
            commonPage.breadcrumbFor(parentHref).shouldBe(visible);
            commonPage.breadcrumbFor(currentPageHref).shouldNotBe(visible);
        } else {
            commonPage.breadcrumbFor(currentPageHref).shouldBe(visible);
            commonPage.breadcrumbFor(parentHref).shouldBe(visible);
            commonPage.visibleBreadcrumbs().shouldHave(CollectionCondition.sizeGreaterThan(1));
        }
        return this;
    }

    public CommonSteps currentUrlShouldContain(String fragment) {
        String url = com.codeborne.selenide.WebDriverRunner.url();
        assertTrue(url.contains(fragment),
                "Expected URL to contain '" + fragment + "' but it was '" + url + "'");
        return this;
    }

    public CommonSteps scrollTo(SelenideElement element) {
        element.scrollIntoView("{block: 'center'}");
        return this;
    }

    public int viewportWidth() {
        Long width = Selenide.executeJavaScript("return window.innerWidth;");
        return width == null ? 0 : width.intValue();
    }

    public CommonSteps viewportShouldMatchProfile(int expectedWidth) {
        int actual = viewportWidth();
        assertTrue(actual == expectedWidth,
                "Expected a " + expectedWidth + "px viewport for the " + Platform.current()
                        + " profile but the browser reported " + actual + "px");
        return this;
    }

    public CommonSteps pageShouldNotScrollHorizontally() {
        Long scrollWidth = Selenide.executeJavaScript("return document.documentElement.scrollWidth;");
        Long innerWidth = Selenide.executeJavaScript("return window.innerWidth;");
        assertTrue(scrollWidth != null && innerWidth != null && scrollWidth <= innerWidth + 1,
                "Page overflows horizontally: document is " + scrollWidth
                        + "px wide inside a " + innerWidth + "px viewport");
        return this;
    }

    public CommonSteps elementShouldHaveCssClass(SelenideElement element, String className) {
        element.shouldHave(cssClass(className));
        return this;
    }
}

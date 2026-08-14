package ge.tbc.testautomation.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import ge.tbc.testautomation.constants.BrowserConstants;
import ge.tbc.testautomation.constants.TestData;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class BrowserConfiguration {
    private BrowserConfiguration() {
    }

    public static void configureGlobals() {
        Configuration.timeout = TestData.DEFAULT_TIMEOUT_MS;
        Configuration.pageLoadTimeout = TestData.PAGE_LOAD_TIMEOUT_MS;
        Configuration.screenshots = true;
        Configuration.savePageSource = false;
        Configuration.reportsFolder = "target/screenshots";
        Configuration.browser = "chrome";
    }

    public static void startBrowser() {
        ChromeOptions options = baseOptions();

        if (Platform.isMobile()) {
            applyMobileEmulation(options);
        }

        ChromeDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);

        if (Platform.isDesktop()) {
            applyDesktopViewport(driver);
        }
    }

    private static void applyDesktopViewport(ChromeDriver driver) {
        driver.manage().window().setSize(
                new Dimension(BrowserConstants.DESKTOP_WIDTH, BrowserConstants.DESKTOP_HEIGHT));

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("width", BrowserConstants.DESKTOP_WIDTH);
        metrics.put("height", BrowserConstants.DESKTOP_HEIGHT);
        metrics.put("deviceScaleFactor", 1);
        metrics.put("mobile", false);
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
    }

    public static void stopBrowser() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
        Platform.clear();
    }

    private static ChromeOptions baseOptions() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("profile.default_content_setting_values.geolocation", 2);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-background-timer-throttling");

        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--lang=en-US");
        options.addArguments("--disable-features="
                + "PasswordLeakDetection,AutofillServerCommunication,CalculateNativeWinOcclusion");

        return options;
    }

    private static void applyMobileEmulation(ChromeOptions options) {
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", BrowserConstants.MOBILE_WIDTH);
        deviceMetrics.put("height", BrowserConstants.MOBILE_HEIGHT);
        deviceMetrics.put("pixelRatio", BrowserConstants.MOBILE_PIXEL_RATIO);
        deviceMetrics.put("touch", true);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", BrowserConstants.MOBILE_USER_AGENT);

        options.setExperimentalOption("mobileEmulation", mobileEmulation);
    }
}

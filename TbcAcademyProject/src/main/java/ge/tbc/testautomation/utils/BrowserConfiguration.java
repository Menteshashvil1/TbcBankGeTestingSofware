package ge.tbc.testautomation.utils;

import com.codeborne.selenide.Configuration;
import ge.tbc.testautomation.constants.BrowserConstants;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class BrowserConfiguration {

    public static void configureFor(String platform) {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2); // 2 = block silently
        options.setExperimentalOption("prefs", prefs);

        if (BrowserConstants.PLATFORM_MOBILE.equalsIgnoreCase(platform)) {
            Configuration.browserSize = null;
            applyMobileEmulation(options);
        } else {
            Configuration.browserSize = BrowserConstants.DESKTOP_RESOLUTION;
        }

        Configuration.browserCapabilities = options;
    }

    private static void applyMobileEmulation(ChromeOptions options) {
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", BrowserConstants.MOBILE_WIDTH);
        deviceMetrics.put("height", BrowserConstants.MOBILE_HEIGHT);
        deviceMetrics.put("pixelRatio", BrowserConstants.MOBILE_PIXEL_RATIO);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", BrowserConstants.MOBILE_USER_AGENT);

        options.setExperimentalOption("mobileEmulation", mobileEmulation);
    }
}
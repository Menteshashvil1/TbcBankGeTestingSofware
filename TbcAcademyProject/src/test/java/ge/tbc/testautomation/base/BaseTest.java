package ge.tbc.testautomation.base;

import com.codeborne.selenide.Configuration;
import ge.tbc.testautomation.constants.TestData;
import ge.tbc.testautomation.utils.BrowserConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    @BeforeClass(alwaysRun = true)
    @Parameters("platform")
    public void setUpBrowser(@Optional("desktop") String platform) {
        Configuration.browser = "chrome";
        Configuration.timeout = TestData.DEFAULT_TIMEOUT_MS;
        Configuration.headless = false;
        BrowserConfiguration.configureFor(platform);
    }
}

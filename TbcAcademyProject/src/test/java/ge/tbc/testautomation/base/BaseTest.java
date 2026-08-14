package ge.tbc.testautomation.base;

import ge.tbc.testautomation.utils.BrowserConfiguration;
import ge.tbc.testautomation.utils.Platform;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void configureSelenide() {
        BrowserConfiguration.configureGlobals();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters("platform")
    public void setUpBrowser(@Optional("desktop") String platform) {
        Platform.set(platform);
        BrowserConfiguration.startBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownBrowser() {
        BrowserConfiguration.stopBrowser();
    }
}

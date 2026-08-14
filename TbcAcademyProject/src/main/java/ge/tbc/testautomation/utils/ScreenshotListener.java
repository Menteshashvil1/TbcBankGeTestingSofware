package ge.tbc.testautomation.utils;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ScreenshotListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }
        String name = Platform.current() + "_" + result.getTestClass().getRealClass().getSimpleName()
                + "_" + result.getName();
        File screenshot = Screenshots.takeScreenShotAsFile();
        System.out.printf("[failure] %s - screenshot: %s%n", name,
                screenshot == null ? "not captured" : screenshot.getAbsolutePath());
    }
}

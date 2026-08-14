package ge.tbc.testautomation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attemptsUsed = 0;

    @Override
    public boolean retry(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method == null || !method.isAnnotationPresent(Retry.class)) {
            return false;
        }
        int budget = method.getAnnotation(Retry.class).count();
        if (attemptsUsed < budget) {
            attemptsUsed++;
            System.out.printf("[retry] %s failed, re-running (attempt %d of %d)%n",
                    result.getName(), attemptsUsed, budget);
            return true;
        }
        return false;
    }
}

package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.constants.BrowserConstants;

public final class Platform {
    private static final ThreadLocal<String> CURRENT = ThreadLocal.withInitial(() -> BrowserConstants.PLATFORM_DESKTOP);

    private Platform() {
    }

    public static void set(String platformFromSuite) {
        String override = System.getProperty("platform");
        String resolved = (override != null && !override.isBlank()) ? override : platformFromSuite;
        CURRENT.set(resolved.trim().toLowerCase());
    }

    public static String current() {
        return CURRENT.get();
    }

    public static boolean isMobile() {
        return BrowserConstants.PLATFORM_MOBILE.equals(CURRENT.get());
    }

    public static boolean isDesktop() {
        return !isMobile();
    }

    public static void clear() {
        CURRENT.remove();
    }
}

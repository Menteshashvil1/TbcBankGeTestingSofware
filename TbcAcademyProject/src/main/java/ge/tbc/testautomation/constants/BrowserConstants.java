package ge.tbc.testautomation.constants;

public class BrowserConstants {
    private BrowserConstants() {
    }

    public static final String PLATFORM_DESKTOP = "desktop";
    public static final String PLATFORM_MOBILE = "mobile";

    public static final int DESKTOP_WIDTH = 1440;
    public static final int DESKTOP_HEIGHT = 900;

    public static final int MOBILE_WIDTH = 390;
    public static final int MOBILE_HEIGHT = 844;
    public static final double MOBILE_PIXEL_RATIO = 3.0;

    public static final String MOBILE_USER_AGENT =
            "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 "
                    + "(KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1";
}

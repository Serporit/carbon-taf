package screens;

import static appium.Bot.click;

public class PermissionScreen extends AbstractScreen {
    private final static String ALLOW_ALWAYS_BUTTON = "com.android.permissioncontroller:id/permission_allow_always_button";
    private final static String ALLOW_BUTTON = "com.android.permissioncontroller:id/permission_allow_button";

    public PermissionScreen allowAlways() {
        click(ALLOW_ALWAYS_BUTTON);
        return this;
    }

    public PermissionScreen allow() {
        click(ALLOW_BUTTON);
        return this;
    }

//    public void allowAll() {
//        allowAlways().allow().allow();
//        waitForDisappear(ALLOW_BUTTON);
//    }
}

package screens;

import static wrapper.Bot.click;
import static wrapper.Bot.waitForElementDisappear;

public class PermissionScreen {
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

    public void allowAll() {
        allowAlways().allow().allow();
        waitForElementDisappear(ALLOW_BUTTON);
    }
}

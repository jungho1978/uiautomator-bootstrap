package com.lge.uiautomator.handler;

import java.io.File;

import com.android.uiautomator.core.UiDevice;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;

/**
 * This handler is used to TakeScreenshot.
 *
 */
public class TakeScreenshot extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand} used for this handler.
     * 
     * @return {@link AndroidCommandResult}
     * 
     * @throws JSONException
     * 
     * @see io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.bootstrap.AndroidCommand)
     */
    @Override
    public AndroidCommandResult execute(final AndroidCommand command) {
        final File screenshot = new File("/data/local/tmp/screenshot.png");

        try {
            screenshot.getParentFile().mkdirs();
        } catch (final Exception e) {
        }

        if (screenshot.exists()) {
            screenshot.delete();
        }

        UiDevice.getInstance().takeScreenshot(screenshot);
        return getSuccessResult(screenshot.exists());
    }
}

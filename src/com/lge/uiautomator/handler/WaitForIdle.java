package com.lge.uiautomator.handler;

import java.util.Hashtable;

import org.json.JSONException;

import com.android.uiautomator.core.UiDevice;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;

/**
 * This handler is used to clear elements in the Android UI.
 *
 * Based on the element Id, clear that element.
 *
 */
public class WaitForIdle extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand}
     * 
     * @return {@link AndroidCommandResult}
     * 
     * @throws JSONException
     * 
     * @see io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.bootstrap.AndroidCommand)
     */
    @Override
    public AndroidCommandResult execute(final AndroidCommand command) throws JSONException {
        final Hashtable<String, Object> params = command.params();
        long timeout = 10;
        if (params.containsKey("timeout")) {
            timeout = (Integer)params.get("timeout");
        }

        UiDevice d = UiDevice.getInstance();
        d.waitForIdle(timeout);
        return getSuccessResult(true);
    }
}

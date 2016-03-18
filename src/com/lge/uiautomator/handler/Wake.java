package com.lge.uiautomator.handler;

import org.json.JSONException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;
import com.lge.uiautomator.bootstrap.WDStatus;

/**
 * This handler is used to power on the device if it's not currently awake.
 *
 */
public class Wake extends CommandHandler {

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
        // only makes sense on a device
        try {
            UiDevice.getInstance().wakeUp();
            return getSuccessResult(true);
        } catch (final RemoteException e) {
            return getErrorResult("Error waking up device");
        }
    }
}

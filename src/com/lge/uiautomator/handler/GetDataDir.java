package com.lge.uiautomator.handler;

import org.json.JSONException;

import android.os.Environment;

import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;

/**
 * This handler is used to get the data dir.
 *
 */
public class GetDataDir extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand} used for this handler.
     * 
     * @return {@link AndroidCommandResult}
     * 
     * @throws JSONException
     * 
     * @see
     * io.appium.android.bootstrap.CommandHandler#execute(io.appium.android.
     * bootstrap.AndroidCommand)
     */
    @Override
    public AndroidCommandResult execute(final AndroidCommand command) {
        return getSuccessResult(Environment.getDataDirectory());
    }
}
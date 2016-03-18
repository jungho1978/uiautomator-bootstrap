package com.lge.uiautomator.handler;

import org.json.JSONException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.AndroidElement;
import com.lge.uiautomator.bootstrap.CommandHandler;
import com.lge.uiautomator.bootstrap.WDStatus;

/**
 * This handler is used to get the text of elements that support it.
 *
 */
public class GetName extends CommandHandler {

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
    public AndroidCommandResult execute(final AndroidCommand command) throws JSONException {
        if (!command.isElementCommand()) {
            return getErrorResult("Unable to get name without an element.");
        }

        try {
            final AndroidElement el = command.getElement();
            return getSuccessResult(el.getContentDesc());
        } catch (final UiObjectNotFoundException e) {
            return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
        } catch (final Exception e) { // handle NullPointerException
            return getErrorResult("Unknown error");
        }
    }
}

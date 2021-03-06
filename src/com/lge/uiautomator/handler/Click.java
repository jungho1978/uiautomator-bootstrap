package com.lge.uiautomator.handler;

import java.util.Hashtable;

import org.json.JSONException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.AndroidElement;
import com.lge.uiautomator.bootstrap.CommandHandler;
import com.lge.uiautomator.bootstrap.PositionHelper;
import com.lge.uiautomator.bootstrap.WDStatus;
import com.lge.uiautomator.bootstrap.utils.Point;
import com.lge.uiautomator.exceptions.InvalidCoordinatesException;

/**
 * This handler is used to click elements in the Android UI.
 *
 * Based on the element Id, click that element.
 *
 */
public class Click extends CommandHandler {

    /*
     * @param command The {@link AndroidCommand}
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
    public AndroidCommandResult execute(final AndroidCommand command) throws JSONException {
        if (command.isElementCommand()) {
            try {
                final AndroidElement el = command.getElement();
                el.click();
                return getSuccessResult(true);
            } catch (final UiObjectNotFoundException e) {
                return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
            } catch (final Exception e) { // handle NullPointerException
                return getErrorResult("Unknown error");
            }
        } else {
            final Hashtable<String, Object> params = command.params();
            Point coords = new Point(Double.parseDouble(params.get("x").toString()), Double.parseDouble(params.get("y").toString()));

            try {
                coords = PositionHelper.getDeviceAbsPos(coords);
            } catch (final UiObjectNotFoundException e) {
                return new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
            } catch (final InvalidCoordinatesException e) {
                return new AndroidCommandResult(WDStatus.INVALID_ELEMENT_COORDINATES, e.getMessage());
            }

            final boolean res = UiDevice.getInstance().click(coords.x.intValue(), coords.y.intValue());
            return getSuccessResult(res);
        }
    }
}

package com.lge.uiautomator.bootstrap;

import java.util.HashMap;

import org.json.JSONException;

import com.lge.uiautomator.handler.Clear;
import com.lge.uiautomator.handler.Click;
import com.lge.uiautomator.handler.CompressedLayoutHierarchy;
import com.lge.uiautomator.handler.Drag;
import com.lge.uiautomator.handler.Find;
import com.lge.uiautomator.handler.Flick;
import com.lge.uiautomator.handler.GetAttribute;
import com.lge.uiautomator.handler.GetDataDir;
import com.lge.uiautomator.handler.GetDeviceSize;
import com.lge.uiautomator.handler.GetLocation;
import com.lge.uiautomator.handler.GetName;
import com.lge.uiautomator.handler.GetSize;
import com.lge.uiautomator.handler.GetText;
import com.lge.uiautomator.handler.LongPressKeyCode;
import com.lge.uiautomator.handler.MultiPointerGesture;
import com.lge.uiautomator.handler.OpenNotification;
import com.lge.uiautomator.handler.Orientation;
import com.lge.uiautomator.handler.Pinch;
import com.lge.uiautomator.handler.PressBack;
import com.lge.uiautomator.handler.PressKeyCode;
import com.lge.uiautomator.handler.ScrollTo;
import com.lge.uiautomator.handler.SetText;
import com.lge.uiautomator.handler.Source;
import com.lge.uiautomator.handler.Swipe;
import com.lge.uiautomator.handler.TakeScreenshot;
import com.lge.uiautomator.handler.TouchDown;
import com.lge.uiautomator.handler.TouchLongClick;
import com.lge.uiautomator.handler.TouchMove;
import com.lge.uiautomator.handler.TouchUp;
import com.lge.uiautomator.handler.UpdateStrings;
import com.lge.uiautomator.handler.WaitForIdle;
import com.lge.uiautomator.handler.Wake;

/**
 * Command execution dispatch class. This class relays commands to the various
 * handlers.
 *
 */
public class AndroidCommandExecutor {
    private static HashMap<String, CommandHandler> map = new HashMap<String, CommandHandler>();

    static {
        map.put("waitForIdle", new WaitForIdle());
        map.put("clear", new Clear());
        map.put("orientation", new Orientation());
        map.put("swipe", new Swipe());
        map.put("flick", new Flick());
        map.put("drag", new Drag());
        map.put("pinch", new Pinch());
        map.put("click", new Click());
        map.put("touchLongClick", new TouchLongClick());
        map.put("touchDown", new TouchDown());
        map.put("touchUp", new TouchUp());
        map.put("touchMove", new TouchMove());
        map.put("getText", new GetText());
        map.put("setText", new SetText());
        map.put("getName", new GetName());
        map.put("getAttribute", new GetAttribute());
        map.put("getDeviceSize", new GetDeviceSize());
        map.put("scrollTo", new ScrollTo());
        map.put("find", new Find());
        map.put("getLocation", new GetLocation());
        map.put("getSize", new GetSize());
        map.put("wake", new Wake());
        map.put("pressBack", new PressBack());
        map.put("pressKeyCode", new PressKeyCode());
        map.put("longPressKeyCode", new LongPressKeyCode());
        map.put("takeScreenshot", new TakeScreenshot());
        map.put("updateStrings", new UpdateStrings());
        map.put("getDataDir", new GetDataDir());
        map.put("performMultiPointerGesture", new MultiPointerGesture());
        map.put("openNotification", new OpenNotification());
        map.put("source", new Source());
        map.put("compressedLayoutHierarchy", new CompressedLayoutHierarchy());
    }

    /**
     * Gets the handler out of the map, and executes the command.
     *
     * @param command The {@link AndroidCommand}
     * @return {@link AndroidCommandResult}
     */
    public AndroidCommandResult execute(AndroidCommand command) {
        try {
            Logger.debug("Got command action: " + command.action());

            if (map.containsKey(command.action())) {
                return map.get(command.action()).execute(command);
            } else {
                return new AndroidCommandResult(WDStatus.UNKNOWN_COMMAND, "Unknown command: " + command.action());
            }
        } catch (JSONException e) {
            Logger.error("Could not decode action/params of command");
            return new AndroidCommandResult(WDStatus.JSON_DECODER_ERROR, "Could not decode action/params of command, please check format!");
        }
    }

}

package com.lge.uiautomator.bootstrap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Results class that converts status to JSON messages.
 *
 */
public class AndroidCommandResult {
    JSONObject json;

    public AndroidCommandResult(final WDStatus status, final JSONObject val) {
        json = new JSONObject();
        try {
            json.put("status", status.code());
            json.put("value", val);
        } catch (JSONException e) {
            Logger.error("Couldn't create android command result!");
        }
    }

    public AndroidCommandResult(final WDStatus status, final Object val) {
        json = new JSONObject();
        try {
            json.put("status", status.code());
            json.put("value", val);
        } catch (JSONException e) {
            Logger.error("Couldn't create android command result!");
        }
    }

    public AndroidCommandResult(final WDStatus status, final String val) {
        json = new JSONObject();
        try {
            json.put("status", status.code());
            json.put("value", val);
        } catch (JSONException e) {
            Logger.error("Couldn't create android command result!");
        }
    }

    @Override
    public String toString() {
        return json.toString();
    }
}

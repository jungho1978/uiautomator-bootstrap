package com.goodluck.bootstrap.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.goodluck.bootstrap.exceptions.CommandTypeException;

public class AndroidCommand {
    JSONObject json;
    AndroidCommandType cmdType;

    public AndroidCommand(final String jsonStr) throws JSONException, CommandTypeException {
        json = new JSONObject(jsonStr);
        setType(json.getString("cmd"));
    }

    public AndroidCommandType commandType() {
        return cmdType;
    }

    public void setType(final String stringType) throws CommandTypeException {
        if (stringType.equals("shutdown")) {
            cmdType = AndroidCommandType.SHUTDOWN;
        } else if (stringType.equals("action")) {
            cmdType = AndroidCommandType.ACTION;
        } else {
            throw new CommandTypeException("Got bad command type: " + stringType);
        }
    }
}

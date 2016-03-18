package com.lge.uiautomator.handler;

import java.util.Hashtable;

import org.json.JSONException;

import com.lge.uiautomator.bootstrap.AndroidCommand;
import com.lge.uiautomator.bootstrap.AndroidCommandResult;
import com.lge.uiautomator.bootstrap.CommandHandler;
import com.lge.uiautomator.bootstrap.utils.NotImportantViews;

/**
 * Calls the uiautomator setCompressedLayoutHierarchy() function. If set to
 * true, ignores some views during all Accessibility operations.
 */
public class CompressedLayoutHierarchy extends CommandHandler {

    @Override
    public AndroidCommandResult execute(AndroidCommand command) throws JSONException {

        boolean compressLayout;

        try {
            final Hashtable<String, Object> params = command.params();
            compressLayout = (Boolean)params.get("compressLayout");
            NotImportantViews.discard(compressLayout);
        } catch (ClassCastException e) {
            return getErrorResult("must supply a 'compressLayout' boolean parameter");
        } catch (Exception e) {
            return getErrorResult("error setting compressLayoutHierarchy " + e.getMessage());
        }

        return getSuccessResult(compressLayout);
    }

}

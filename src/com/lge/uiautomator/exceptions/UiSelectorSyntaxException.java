package com.lge.uiautomator.exceptions;

@SuppressWarnings("serial")
public class UiSelectorSyntaxException extends Exception {

    /**
     * An exception involving an {@link UiSelectorParser}.
     *
     * @param msg A descriptive message describing the error.
     */
    public UiSelectorSyntaxException(final String msg) {
        super(msg);
    }
}

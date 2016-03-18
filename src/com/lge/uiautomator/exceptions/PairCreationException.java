package com.lge.uiautomator.exceptions;

/**
 * For trying to create a ClassInstancePair and something goes wrong.
 */
public class PairCreationException extends Throwable {
    public PairCreationException(String msg) {
        super(msg);
    }
}
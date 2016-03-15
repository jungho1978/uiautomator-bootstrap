package com.goodluck.bootstrap.exceptions;

public class SocketServerException extends Exception {
    private String reason;

    public SocketServerException(final String msg) {
        super(msg);
        reason = msg;
    }

    public String getError() {
        return reason;
    }
}

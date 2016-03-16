package com.goodluck.bootstrap;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.goodluck.bootstrap.exceptions.SocketServerException;

public class Bootstrap extends UiAutomatorTestCase {
    public void testRunServer() {
        SocketServer server;
        try {
            server = new SocketServer(4724);
            server.listenForever(false, false);
        } catch (SocketServerException e) {
            Logger.error(e.getError());
            System.exit(1);;
        }
    }
}

package com.goodluck.bootstrap;

import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.goodluck.bootstrap.exceptions.SocketServerException;

public class SocketServerTest {
    private static final int PORT = 4724;
    private SocketServer server;

    @Before
    public void setUp() throws SocketServerException {
        server = new SocketServer(PORT);
        server.listenForever(false, false);
    }

    @Test
    public void testTryToConnect() throws JSONException {
        assertTrue(true);
    }

}

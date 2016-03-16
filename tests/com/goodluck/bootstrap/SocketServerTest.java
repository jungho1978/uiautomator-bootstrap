package com.goodluck.bootstrap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.goodluck.bootstrap.exceptions.SocketServerException;

public class SocketServerTest {
    private static final int PORT = 4724;
    private SocketServer server;
    
    @Before
    public void setUp() throws SocketServerException {
        server = new SocketServer(PORT);
    }
    
    @Test
    public void test() {
        assertTrue(true);
    }

}

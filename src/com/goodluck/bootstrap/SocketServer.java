package com.goodluck.bootstrap;

import java.io.IOException;
import java.net.ServerSocket;

import com.goodluck.bootstrap.exceptions.SocketServerException;

public class SocketServer {
    ServerSocket server;
    boolean keepListening;

    public SocketServer(final int port) throws SocketServerException {
        keepListening = true;
        try {
            server = new ServerSocket(port);
            Logger.debug("Socket opened on port " + port);
        } catch (final IOException e) {
            throw new SocketServerException(
                    "Could not start socket server listening on " + port);
        }
    }
    
    public void listenForever(boolean disableAndroidWatchers, boolean acceptSSLCerts) {
        Logger.debug("Bootstrap Socket Server Ready");
    }
}

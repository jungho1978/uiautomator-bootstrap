package com.goodluck.bootstrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;

import com.android.uiautomator.common.UiWatchers;
import com.goodluck.bootstrap.exceptions.CommandTypeException;
import com.goodluck.bootstrap.exceptions.SocketServerException;
import com.goodluck.bootstrap.handler.UpdateStrings;
import com.goodluck.bootstrap.utils.AndroidCommand;
import com.goodluck.bootstrap.utils.AndroidCommandExecutor;
import com.goodluck.bootstrap.utils.AndroidCommandResult;
import com.goodluck.bootstrap.utils.AndroidCommandType;
import com.goodluck.bootstrap.utils.WDStatus;

public class SocketServer {
    ServerSocket server;
    Socket client;
    BufferedReader in;
    BufferedWriter out;
    boolean keepListening;

    private final AndroidCommandExecutor executor;

    public SocketServer(final int port) throws SocketServerException {
        keepListening = true;
        executor = new AndroidCommandExecutor();
        try {
            server = new ServerSocket(port);
            Logger.debug("Socket opened on port " + port);
        } catch (final IOException e) {
            throw new SocketServerException("Could not start socket server listening on " + port);
        }
    }

    private AndroidCommand getCommand(final String data) throws JSONException, CommandTypeException {
        return new AndroidCommand(data);
    }

    private void handleClientData() throws SocketServerException {
        StringBuilder input = new StringBuilder();

        try {
            String res = null;
            int a;
            // (char) -1 is not equal to -1.
            // ready is checked to ensure the read call doesn't block.
            while ((a = in.read()) != -1 && in.ready()) {
                input.append((char)a);
            }
            String inputString = input.toString();
            Logger.debug("Got data from client: " + inputString);
            try {
                AndroidCommand cmd = getCommand(inputString);
                res = runCommand(cmd);
            } catch (final CommandTypeException e) {
                res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, e.getMessage()).toString();
            } catch (final JSONException e) {
                res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, "Error running and parsing command").toString();
            }
            out.write(res);
            out.flush();
        } catch (IOException e) {
            throw new SocketServerException("Error processing data to/from socket (" + e.toString() + ")");
        }
    }

    public void listenForever(boolean disableAndroidWatchers, boolean acceptSSLCerts) throws SocketServerException {
        Logger.debug("Bootstrap Socket Server Ready");
        UpdateStrings.loadStringsJson();
        if (disableAndroidWatchers) {
            Logger.debug("Skipped registering crash watcher");
        } else {
            // TODO: not implemented yet
        }

        if (acceptSSLCerts) {
            // TODO: not implemented yet
        }

        // TODO: not implemented yet (TimerTask)
        try {
            client = server.accept();
            Logger.debug("Client connected");
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF_8"));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
            while (keepListening) {
                handleClientData();
            }
            in.close();
            out.close();
            Logger.debug("Closed client connection");
        } catch (final IOException e) {
            throw new SocketServerException("Error when client was trying to connect");
        }

    }

    public void dismissCrashAlerts() {
        try {
            new UiWatchers().registerAnrAndCrashWatchers();
            Logger.debug("Registered crash watchers.");
        } catch (Exception e) {
            Logger.debug("Unable to register crash watchers.");
        }
    }

    public void acceptSSLCertificates() {
        try {
            new UiWatchers().registerAcceptSSLCertWatcher();
            Logger.debug("Registered SSL certificate error watcher.");
        } catch (Exception e) {
            Logger.debug("Unable to register SSL certificates error watcher.");
        }
    }

    private String runCommand(final AndroidCommand cmd) {
        AndroidCommandResult res = null;
        if (cmd.commandType() == AndroidCommandType.SHUTDOWN) {
            keepListening = false;
            res = new AndroidCommandResult(WDStatus.SUCCESS, "OK, shutting down");
        } else if (cmd.commandType() == AndroidCommandType.ACTION) {
            res = executor.execute(cmd);
        } else {
            res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, "Unknown command type, could not execute!");
        }
        return res.toString();
    }
}

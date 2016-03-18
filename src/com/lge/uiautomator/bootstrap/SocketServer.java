package com.lge.uiautomator.bootstrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import com.android.uiautomator.common.UiWatchers;
import com.lge.uiautomator.bootstrap.utils.TheWatchers;
import com.lge.uiautomator.exceptions.CommandTypeException;
import com.lge.uiautomator.exceptions.SocketServerException;
import com.lge.uiautomator.handler.UpdateStrings;

/**
 * The SocketServer class listens on a specific port for commands from Appium,
 * and then passes them on to the {@link AndroidCommandExecutor} class. It will
 * continue to listen until the command is sent to exit.
 */
public class SocketServer {
    ServerSocket server;
    Socket client;
    BufferedReader in;
    PrintWriter out;
    boolean keepListening;
    private final AndroidCommandExecutor executor;
    private final TheWatchers watchers = TheWatchers.getInstance();
    private final Timer timer = new Timer("WatchTimer");

    /**
     * Constructor
     *
     * @param port
     * @throws SocketServerException
     */
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

    /**
     * Constructs an @{link AndroidCommand} and returns it.
     *
     * @param data
     * @return @{link AndroidCommand}
     * @throws JSONException
     * @throws CommandTypeException
     */
    private AndroidCommand getCommand(final String data) throws JSONException, CommandTypeException {
        return new AndroidCommand(data);
    }

    /**
     * When data is available on the socket, this method is called to run the
     * command or throw an error if it can't.
     *
     * @throws SocketServerException
     */
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
            out.println(res);
            out.flush();
        } catch (IOException e) {
            throw new SocketServerException("Error processing data to/from socket (" + e.toString() + ")");
        }
    }

    /**
     * Listens on the socket for data, and calls {@link #handleClientData()}
     * when it's available.
     *
     * @throws SocketServerException
     */
    public void listenForever(boolean disableAndroidWatchers, boolean acceptSSLCerts) throws SocketServerException {
        Logger.debug("Bootstrap Socket Server Ready");
        UpdateStrings.loadStringsJson();
        if (disableAndroidWatchers) {
            Logger.debug("Skipped registering crash watcher");
        } else {
            dismissCrashAlerts();
        }

        if (acceptSSLCerts) {
            Logger.debug("Accepting SSL certificate errors.");
            acceptSSLCertificates();
        }

        final TimerTask updateWatchers = new TimerTask() {
            @Override
            public void run() {
                try {
                    watchers.check();
                } catch (final Exception e) {
                }
            }
        };
        timer.scheduleAtFixedRate(updateWatchers, 100, 100);

        try {
            client = server.accept();
            Logger.debug("Client connected");
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
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

    /**
     * When {@link #handleClientData()} has valid data, this method delegates
     * the command.
     *
     * @param cmd AndroidCommand
     * @return Result
     */
    private String runCommand(final AndroidCommand cmd) {
        AndroidCommandResult res = null;
        if (cmd.commandType() == AndroidCommandType.SHUTDOWN) {
            keepListening = false;
            res = new AndroidCommandResult(WDStatus.SUCCESS, "OK, shutting down");
        } else if (cmd.commandType() == AndroidCommandType.ACTION) {
            try {
                res = executor.execute(cmd);
            } catch (final NoSuchElementException e) {
                res = new AndroidCommandResult(WDStatus.NO_SUCH_ELEMENT, e.getMessage());
            } catch (final Exception e) { // Here we catch all possible
                                          // exceptions and return a JSON Wire
                                          // Protocol UnknownError
                                          // This prevents exceptions from
                                          // halting the bootstrap app
                Logger.debug("Command returned error:" + e);
                res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, e.getMessage());
            }
        } else {
            // this code should never be executed, here for future-proofing
            res = new AndroidCommandResult(WDStatus.UNKNOWN_ERROR, "Unknown command type, could not execute!");
        }
        return res.toString();
    }
}

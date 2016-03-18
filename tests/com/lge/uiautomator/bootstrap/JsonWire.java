package com.lge.uiautomator.bootstrap;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

public class JsonWire implements Closeable {
    private BufferedReader reader;
    private PrintWriter writer;

    public JsonWire(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
    }

    public void /* String */sendCommand(final String command, final String action) {
        sendRequest(command, action);
        System.out.println("Received: " + readResponse());
    }

    private void sendRequest(final String command, final String action) {
        JSONObject request = new JSONObject();
        request.put("cmd", command);
        request.put("action", action);
        writer.println(request.toString());
        writer.flush();
    }

    private String readResponse() {
        String json;
        try {
            json = reader.readLine();
            JSONObject response = new JSONObject(json);
            return String.valueOf(response.get("value"));
        } catch (final IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
    }
}

package com.goodluck.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;

public class TestClient {
    private static final int PORT = 4724;

    public static void main(String[] args) throws UnknownHostException, IOException, JSONException {
        Socket socket = new Socket("localhost", PORT);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        String request = "{\"cmd\": \"shutdown\"}";
        out.println(request);
        out.flush();
        out.flush();
        
        socket.close();
    }
}

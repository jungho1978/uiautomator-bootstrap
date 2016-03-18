package com.lge.uiautomator.bootstrap;

import java.io.IOException;
import java.net.Socket;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.TimeoutException;

public class TestClient {
    private static final int BOOTSTRAP_PORT = 4724;

    public static void main(String[] args) throws InterruptedException, TimeoutException, AdbCommandRejectedException, IOException {
        DebugBridge.setAdbLocation("adb");
        DebugBridge.init();

        int retryCnt = 5;
        do {
            Thread.sleep(500);
            System.out.println("Waiting for device...");
        } while (--retryCnt > 0 && DebugBridge.getDevices().size() == 0);

        if (DebugBridge.getDevices().size() == 0) {
            System.err.println("No device found by adb");
            System.exit(-1);
        }

        IDevice device = DebugBridge.getDevices().get(0);
        System.out.println("Connected: " + device);

        // Port forwarding
        device.createForward(BOOTSTRAP_PORT, BOOTSTRAP_PORT);

        // bootstrap wire
        JsonWire wire = new JsonWire(new Socket("localhost", BOOTSTRAP_PORT));
        wire.sendCommand("action", "wake");
        wire.sendCommand("shutdown", "");

        System.exit(0);
    }
}

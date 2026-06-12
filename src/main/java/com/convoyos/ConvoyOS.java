package com.convoyos;

import com.convoyos.model.TelemetryData;
import com.convoyos.telemetry.TelemetryService;
import com.convoyos.ui.ConsoleDashboard;

public class ConvoyOS {

    public static void main(String[] args) {

        TelemetryService telemetryService =
                new TelemetryService();

        if (!telemetryService.start()) {
            return;
        }

        try {

            while (true) {

                TelemetryData telemetry =
                        telemetryService.readTelemetry();

                ConsoleDashboard.print(
                        telemetry,
                        telemetryService.getSessionStats());

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            telemetryService.stop();
        }
    }
}
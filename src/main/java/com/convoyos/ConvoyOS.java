package com.convoyos;

import com.convoyos.memory.SharedMemoryReader;
import com.convoyos.memory.TelemetryParser;
import com.convoyos.model.TelemetryData;
import com.convoyos.ui.ConsoleDashboard;

public class ConvoyOS {

    public static void main(String[] args) {

        SharedMemoryReader reader = new SharedMemoryReader();

        if (!reader.connect()) {
            return;
        }

        if (!reader.mapMemory()) {
            return;
        }

        TelemetryParser parser = new TelemetryParser();

        try {

            while (true) {

                TelemetryData telemetry =
                        parser.parse(
                                reader.readMemory());

                ConsoleDashboard.print(
                        telemetry);

                Thread.sleep(1000);

            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            reader.close();
        }
    }
}
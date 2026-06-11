package com.convoyos;

import com.convoyos.memory.SharedMemoryReader;
import com.convoyos.memory.TelemetryParser;
import com.convoyos.model.TelemetryData;

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

                byte[] data = reader.readMemory();

                TelemetryData telemetry =
                        parser.parse(data);

                System.out.println("--------------------------------");

                System.out.println(
                        "Game ID: "
                        + telemetry.getGameId());

                System.out.println(
                        "Speed (m/s): "
                        + telemetry.getSpeed());

                System.out.println(
                        "Speed (km/h): "
                        + (telemetry.getSpeed() * 3.6f));
                
                System.out.println(
                        "Speed Corregida (km/h): "
                        + telemetry.getSpeedKmh());

                System.out.println(
                        "Odometer: "
                        + telemetry.getOdometer());

                Thread.sleep(1000);

            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            reader.close();
        }
    }
}
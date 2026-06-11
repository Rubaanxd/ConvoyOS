package com.convoyos.ui;

import com.convoyos.model.TelemetryData;

public final class ConsoleDashboard {

    private ConsoleDashboard() {
    }

    public static void print(
            TelemetryData telemetry) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("================================");
        System.out.println("          ConvoyOS");
        System.out.println("================================");
        System.out.println();

        printGameInfo(telemetry);

        System.out.println();

        printTruckInfo(telemetry);

        System.out.println();

        printJobInfo(telemetry);
    }

    private static void printGameInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Juego: "
                + telemetry.getGameName());
    }

    private static void printTruckInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Camión: "
                + telemetry.getTruckFullName());

        System.out.println(
                "Velocidad: "
                + telemetry.getSpeedKmhRounded()
                + " km/h");

        System.out.println(
                "RPM: "
                + telemetry.getEngineRpmRounded());

        System.out.println(
                "Combustible: "
                + telemetry.getFuelStatus());

        System.out.println(
                "Autonomía: "
                + telemetry.getFuelRangeFormatted());

        System.out.println(
                "Odómetro: "
                + telemetry.getOdometerFormatted());

        if (telemetry.hasSpeedLimit()) {

            System.out.println(
                    "Límite de velocidad: "
                    + telemetry.getSpeedLimitKmh()
                    + " km/h");
        }
    }

    private static void printJobInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Estado: "
                + telemetry.getJobStatus());

        if (!telemetry.isOnJob()) {
            return;
        }

        System.out.println();

        System.out.println(
                "Carga: "
                + telemetry.getCargo());

        System.out.println(
                "Origen: "
                + telemetry.getCitySrc());

        System.out.println(
                "Destino: "
                + telemetry.getCityDst());
    }
}
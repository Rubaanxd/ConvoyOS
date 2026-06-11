package com.convoyos.ui;

import com.convoyos.model.TelemetryData;

public final class ConsoleDashboard {

    private ConsoleDashboard() {
    }

    public static void print(
        TelemetryData telemetry) {
        
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("--------------------------------");

        printGameInfo(telemetry);

        printTruckInfo(telemetry);

        printJobInfo(telemetry);
    }

    private static void printGameInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Game ID: "
                + telemetry.getGameId());
    }

    private static void printTruckInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Marca: "
                + telemetry.getTruckBrand());

        System.out.println(
                "Modelo: "
                + telemetry.getTruckName());

        System.out.println(
                "Speed (m/s): "
                + telemetry.getSpeed());

        System.out.println(
                "Speed (km/h): "
                + (telemetry.getSpeed() * 3.6f));

        System.out.println(
                "Odometer: "
                + telemetry.getOdometer());
        
        System.out.println(
                "RPM: "
                + telemetry.getEngineRpm());

        System.out.println(
                "Combustible: "
                + telemetry.getFuel());

        System.out.println(
                "Límite de velocidad: "
                + telemetry.getSpeedLimit());
        
        System.out.println(
                "Combustible: "
                + telemetry.getFuelStatus());

        System.out.println(
                "Autonomía: "
                + String.format("%.0f km",
                        telemetry.getFuelRange()));
        
    }

    private static void printJobInfo(
            TelemetryData telemetry) {

        System.out.println(
                "Carga: "
                + telemetry.getCargo());

        System.out.println(
                "Origen: "
                + telemetry.getCitySrc());

        System.out.println(
                "Destino: "
                + telemetry.getCityDst());

        System.out.println(
                "En trabajo: "
                + telemetry.isOnJob());
    }
}
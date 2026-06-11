package com.convoyos.memory;

import com.convoyos.model.TelemetryData;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class TelemetryParser {

    public TelemetryData parse(byte[] data) {

        ByteBuffer buffer =
                ByteBuffer.wrap(data)
                          .order(ByteOrder.LITTLE_ENDIAN);

        TelemetryData telemetry =
                new TelemetryData();

        telemetry.setGameId(
                buffer.getInt(TelemetryOffsets.GAME_ID));

        telemetry.setSpeed(
                buffer.getFloat(TelemetryOffsets.SPEED));

        telemetry.setOdometer(
                buffer.getFloat(TelemetryOffsets.ODOMETER));
        
         /**
        * ------------------------------------------------
        * ---------------- Truck ----------------
        * ------------------------------------------------
        */
        
        telemetry.setTruckBrand(
                readString(
                        buffer,
                        TelemetryOffsets.TRUCK_BRAND,
                        64));

        telemetry.setTruckName(
                readString(
                        buffer,
                        TelemetryOffsets.TRUCK_NAME,
                        64));
        
        
         /**
        * ------------------------------------------------
        * ---------------- JOBS ----------------
        * ------------------------------------------------
        */

        
        telemetry.setCargo(
                readString(buffer,
                        TelemetryOffsets.CARGO,
                        64));

        telemetry.setCitySrc(
                readString(buffer,
                        TelemetryOffsets.CITY_SRC,
                        64));

        telemetry.setCityDst(
                readString(buffer,
                        TelemetryOffsets.CITY_DST,
                        64));
        
        telemetry.setOnJob(
                buffer.get(TelemetryOffsets.ON_JOB) != 0);

        return telemetry;
    }

    private String readString(
            ByteBuffer buffer,
            int offset,
            int length) {

        byte[] bytes = new byte[length];

        buffer.position(offset);

        buffer.get(bytes);

        int end = 0;

        while (end < bytes.length && bytes[end] != 0) {
            end++;
        }

        return new String(
                bytes,
                0,
                end,
                java.nio.charset.Charset.forName("Windows-1252"));
    }
}
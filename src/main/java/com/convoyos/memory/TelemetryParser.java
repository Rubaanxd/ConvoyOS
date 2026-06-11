package com.convoyos.memory;

import com.convoyos.model.TelemetryData;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TelemetryParser {

    public TelemetryData parse(byte[] data) {

        ByteBuffer buffer =
                ByteBuffer.wrap(data)
                          .order(ByteOrder.LITTLE_ENDIAN);

        TelemetryData telemetry =
                new TelemetryData();

        telemetry.setGameId(
                buffer.getInt(
                        TelemetryOffsets.GAME_ID));

        telemetry.setSpeed(
                buffer.getFloat(
                        TelemetryOffsets.SPEED));

        telemetry.setOdometer(
                buffer.getFloat(
                        TelemetryOffsets.ODOMETER));

        return telemetry;
    }
}
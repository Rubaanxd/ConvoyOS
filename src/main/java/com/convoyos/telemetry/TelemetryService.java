package com.convoyos.telemetry;

import com.convoyos.memory.SharedMemoryReader;
import com.convoyos.memory.TelemetryParser;
import com.convoyos.model.TelemetryData;
import com.convoyos.stats.SessionStats;

public class TelemetryService {

    private final SharedMemoryReader reader;
    private final TelemetryParser parser;
    private final SessionStats sessionStats;

    public TelemetryService() {
        reader = new SharedMemoryReader();
        parser = new TelemetryParser();
        sessionStats = new SessionStats();
    }

    public boolean start() {

        if (!reader.connect()) return false;

        if (!reader.mapMemory()) return false;

        byte[] data = reader.readMemory();

        TelemetryData telemetry = parser.parse(data);

        sessionStats.start(telemetry.getOdometer());

        return true;
    }

    public TelemetryData readTelemetry() {

        byte[] data = reader.readMemory();

        TelemetryData telemetry = parser.parse(data);

        sessionStats.update(
            telemetry.getOdometer(),
            telemetry.isMoving(),
            telemetry.getDisplaySpeedKmh());

        return telemetry;
    }

    public SessionStats getSessionStats() {
        return sessionStats;
    }

    public void stop() {
        reader.close();
    }
}
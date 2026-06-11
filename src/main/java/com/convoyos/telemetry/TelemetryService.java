package com.convoyos.telemetry;

import com.convoyos.memory.SharedMemoryReader;
import com.convoyos.memory.TelemetryParser;
import com.convoyos.model.TelemetryData;

public class TelemetryService {

    private final SharedMemoryReader reader;
    private final TelemetryParser parser;

    public TelemetryService() {

        this.reader = new SharedMemoryReader();
        this.parser = new TelemetryParser();
    }

    public boolean start() {

        if (!reader.connect()) {
            return false;
        }

        return reader.mapMemory();
    }

    public TelemetryData readTelemetry() {

        byte[] data = reader.readMemory();

        return parser.parse(data);
    }

    public void stop() {

        reader.close();
    }
}
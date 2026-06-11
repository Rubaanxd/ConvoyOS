package com.convoyos.memory;

public final class TelemetryOffsets {

    private TelemetryOffsets() {
    }

    /*
     * ==========================================
     * GAME
     * ==========================================
     */
    public static final int GAME_ID = 52;

    /*
    * ==========================================
    * TRUCK
    * ==========================================
    */

    public static final int SPEED = 948;
    public static final int ENGINE_RPM = 952;
    public static final int FUEL = 1000;
    public static final int ODOMETER = 1056;
    public static final int SPEED_LIMIT = 1068;
    public static final int TRUCK_BRAND = 2364;
    public static final int TRUCK_NAME = 2492;
    public static final int FUEL_CAPACITY = 704;
    public static final int FUEL_AVG_CONSUMPTION = 1004;
    public static final int FUEL_RANGE = 1008;

    /*
     * ==========================================
     * JOB
     * ==========================================
     */
    public static final int CARGO = 2620;
    public static final int CITY_DST = 2748;
    public static final int CITY_SRC = 3004;

    public static final int JOB_INCOME = 4000;

    public static final int ON_JOB = 4300;
    public static final int JOB_FINISHED = 4301;
    public static final int JOB_CANCELLED = 4302;
    public static final int JOB_DELIVERED = 4303;

}
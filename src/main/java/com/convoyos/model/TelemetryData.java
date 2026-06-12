package com.convoyos.model;

public class TelemetryData {

    /**
     * ------------------------------------------------
     * ---------------- Game ----------------
     * ------------------------------------------------
     */
    private int gameId;

    /**
     * ------------------------------------------------
     * ---------------- Truck ----------------
     * ------------------------------------------------
     */
    private String truckBrand;
    private String truckName;

    private float speed;
    private float odometer;

    private float fuel;
    private float fuelCapacity;
    private float fuelAvgConsumption;
    private float fuelRange;

    private float engineRpm;
    private float speedLimit;

    /**
     * ------------------------------------------------
     * ---------------- Job ----------------
     * ------------------------------------------------
     */
    private boolean onJob;

    private String cargo;
    private String citySrc;
    private String cityDst;

    private long jobIncome;

    /**
     * ------------------------------------------------
     * ---------------- Getters & Setters ----------------
     * ------------------------------------------------
     */
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTruckBrand() {
        return truckBrand;
    }

    public void setTruckBrand(String truckBrand) {
        this.truckBrand = truckBrand;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getFuelAvgConsumption() {
        return fuelAvgConsumption;
    }

    public void setFuelAvgConsumption(float fuelAvgConsumption) {
        this.fuelAvgConsumption = fuelAvgConsumption;
    }

    public float getFuelRange() {
        return fuelRange;
    }

    public void setFuelRange(float fuelRange) {
        this.fuelRange = fuelRange;
    }

    public float getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(float engineRpm) {
        this.engineRpm = engineRpm;
    }

    public float getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(float speedLimit) {
        this.speedLimit = speedLimit;
    }

    public boolean isOnJob() {
        return onJob;
    }

    public void setOnJob(boolean onJob) {
        this.onJob = onJob;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCitySrc() {
        return citySrc;
    }

    public void setCitySrc(String citySrc) {
        this.citySrc = citySrc;
    }

    public String getCityDst() {
        return cityDst;
    }

    public void setCityDst(String cityDst) {
        this.cityDst = cityDst;
    }

    public long getJobIncome() {
        return jobIncome;
    }

    public void setJobIncome(long jobIncome) {
        this.jobIncome = jobIncome;
    }

    /**
     * ------------------------------------------------
     * ---------------- Métodos Calculados ----------------
     * ------------------------------------------------
     */

    public String getGameName() {

        switch (gameId) {

            case 1:
                return "ETS2";

            case 2:
                return "ATS";

            default:
                return "Desconocido";
        }
    }

    /**
     * Velocidad real en km/h
     * Mantiene signo (+/-)
     */
    public float getSpeedKmh() {
        return speed * 3.6f;
    }

    /**
     * Velocidad para mostrar en UI
     * Siempre positiva
     */
    public float getDisplaySpeedKmh() {
        return Math.abs(getSpeedKmh());
    }

    public int getSpeedKmhRounded() {
        return Math.round(getDisplaySpeedKmh());
    }

    public boolean isMoving() {
        return getDisplaySpeedKmh() > 1.0f;
    }

    public boolean isStopped() {
        return !isMoving();
    }

    public int getEngineRpmRounded() {
        return Math.round(engineRpm);
    }

    public float getFuelPercent() {

        if (fuelCapacity <= 0) {
            return 0;
        }

        return (fuel / fuelCapacity) * 100f;
    }

    public boolean hasSpeedLimit() {
        return speedLimit > 0;
    }

    public int getSpeedLimitKmh() {
        return Math.round(speedLimit * 3.6f);
    }

    /**
     * ------------------------------------------------
     * ---------------- Métodos Formateados ----------------
     * ------------------------------------------------
     */

    public String getTruckFullName() {
        return truckBrand + " " + truckName;
    }

    public String getFuelFormatted() {

        return String.format(
                "%.1f L",
                fuel);
    }

    public String getFuelCapacityFormatted() {

        return String.format(
                "%.1f L",
                fuelCapacity);
    }

    public String getFuelRangeFormatted() {

        return String.format(
                "%,.0f km",
                fuelRange);
    }

    public String getFuelStatus() {

        return String.format(
                "%.1f / %.1f L (%.1f%%)",
                fuel,
                fuelCapacity,
                getFuelPercent());
    }

    public String getOdometerFormatted() {

        return String.format(
                "%,.0f km",
                odometer);
    }

    public String getJobStatus() {

        return onJob
                ? "En ruta"
                : "Sin trabajo";
    }

    public String getCurrencySymbol() {

        switch (gameId) {

            case 1:
                return "€";

            case 2:
                return "$";

            default:
                return "";
        }
    }

    public String getJobIncomeFormatted() {

        return getCurrencySymbol()
                + String.format("%.2f",
                        jobIncome / 100.0);
    }
    
    
}
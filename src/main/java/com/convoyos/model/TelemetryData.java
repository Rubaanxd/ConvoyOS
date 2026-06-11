package com.convoyos.model;

public class TelemetryData {

    // Game
    private int gameId;

    // Truck
    private String truckBrand;
    private String truckName;
    private float speed;
    private float odometer;
    private float fuel;
    private float engineRpm;

    // Job
    private boolean onJob;
    private String cargo;
    private String citySrc;
    private String cityDst;
    private long jobIncome;
   



    /**
     * ------------------------------------------------
     * ---------------- Setter & getters ----------------
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

    public float getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(float engineRpm) {
        this.engineRpm = engineRpm;
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
     * ---------------- Metodos Extras ----------------
     * ------------------------------------------------
     */
    public float getSpeedKmh() {
        return Math.abs(speed * 3.6f);
    }
    
    public boolean isMoving() {
        return getSpeedKmh() > 1.0f;
    }
}
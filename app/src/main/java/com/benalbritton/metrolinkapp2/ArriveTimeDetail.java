package com.benalbritton.metrolinkapp2;


public class ArriveTimeDetail {

    private double timeAsDouble;
    private String timeAsString;
    private String routeColorDirection;

    public void setTimeAsDouble(double time) {
        timeAsDouble = time;
    }

    public void setTimeAsString(String time) {
        timeAsString = time;
    }

    public void setRouteColorDirection(String colorDirection) {
        routeColorDirection = colorDirection;
    }

    public double getTimeAsDouble() {
        return timeAsDouble;
    }
}

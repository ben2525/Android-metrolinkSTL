package com.benalbritton.metrolinkapp2;


public class Station {
    private String id;
    private String name;
    private double lat;
    private double lon;

    public Station(String id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return  name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getId() {
        return id;
    }
}

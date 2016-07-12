package com.benalbritton.metrolinkapp2;


import java.util.Comparator;

public class Station {
    private String id;
    private String name;
    private double lat;
    private double lon;
    private double distToUser;

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

    /*
    public double getDistToUser() {
        return distToUser;
    }
    */

    public void setDistToUser (double dist) {
        distToUser = dist;
    }

    public static Comparator<Station> DistanceComparator = new Comparator<Station>() {
        @Override
        public int compare(Station lhs, Station rhs) {
            if (lhs.distToUser < rhs.distToUser) return -1;
            if (lhs.distToUser > rhs.distToUser) return 1;
            return 0;
        }
    };

}

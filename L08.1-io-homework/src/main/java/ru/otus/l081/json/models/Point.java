package ru.otus.l081.json.models;


public class Point {
    private final double latitude;
    private final double longitude;

    public Point() {
        this.latitude = 12.3;
        this.longitude = 45.6;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point that = (Point) obj;

        if (that.latitude != this.latitude || that.longitude != this.longitude) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Longitude " + String.valueOf(longitude)
                + " Latitude " + String.valueOf(latitude);
    }
}

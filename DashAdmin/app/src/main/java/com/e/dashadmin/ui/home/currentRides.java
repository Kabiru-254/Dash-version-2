package com.e.dashadmin.ui.home;

public class currentRides {
    String DriverFirstName, DriverSecondName, PassengerFirstName, PassengerSecondName, Destination;

    public currentRides() {
    }

    public currentRides(String driverFirstName, String driverSecondName, String passengerFirstName, String passengerSecondName, String destination) {
        DriverFirstName = driverFirstName;
        DriverSecondName = driverSecondName;
        PassengerFirstName = passengerFirstName;
        PassengerSecondName = passengerSecondName;
        Destination = destination;
    }

    public String getDriverFirstName() {
        return DriverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        DriverFirstName = driverFirstName;
    }

    public String getDriverSecondName() {
        return DriverSecondName;
    }

    public void setDriverSecondName(String driverSecondName) {
        DriverSecondName = driverSecondName;
    }

    public String getPassengerFirstName() {
        return PassengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        PassengerFirstName = passengerFirstName;
    }

    public String getPassengerSecondName() {
        return PassengerSecondName;
    }

    public void setPassengerSecondName(String passengerSecondName) {
        PassengerSecondName = passengerSecondName;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }
}

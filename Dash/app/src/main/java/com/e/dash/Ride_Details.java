package com.e.dash;

public class Ride_Details {

    String CarNumberPlate, Departure_Time, Destination, Fare, Number_of_Seats, DriverID;

    public Ride_Details() {
    }

    public Ride_Details(String carNumberPlate, String departure_Time, String destination, String fare, String number_of_Seats, String driverID) {
        CarNumberPlate = carNumberPlate;
        Departure_Time = departure_Time;
        Destination = destination;
        Fare = fare;
        Number_of_Seats = number_of_Seats;
        DriverID = driverID;
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }

    public String getCarNumberPlate() {
        return CarNumberPlate;
    }

    public void setCarNumberPlate(String carNumberPlate) {
        CarNumberPlate = carNumberPlate;
    }

    public String getDeparture_Time() {
        return Departure_Time;
    }

    public void setDeparture_Time(String departure_Time) {
        Departure_Time = departure_Time;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getNumber_of_Seats() {
        return Number_of_Seats;
    }

    public void setNumber_of_Seats(String number_of_Seats) {
        Number_of_Seats = number_of_Seats;
    }
}

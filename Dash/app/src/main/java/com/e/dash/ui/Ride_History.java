package com.e.dash.ui;

public class Ride_History {

    String PassengerFirstName, PassengerSecondName, DriverFirstName, DriverSecondName, Destination, Fare, Rating, Comment;

    public Ride_History() {
    }

    public Ride_History(String passengerFirstName, String passengerSecondName, String driverFirstName, String driverSecondName, String destination, String fare, String rating, String comment) {
        PassengerFirstName = passengerFirstName;
        PassengerSecondName = passengerSecondName;
        DriverFirstName = driverFirstName;
        DriverSecondName = driverSecondName;
        Destination = destination;
        Fare = fare;
        Rating = rating;
        Comment = comment;
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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}

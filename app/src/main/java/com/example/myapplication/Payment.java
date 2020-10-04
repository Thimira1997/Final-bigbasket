package com.example.myapplication;

public class Payment {
    String pin;
    String destination;
    String km;

    public Payment() {
    }

    public Payment(String pin, String destination, String km) {
        this.pin = pin;
        this.destination = destination;
        this.km = km;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

}

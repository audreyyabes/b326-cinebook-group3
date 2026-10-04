package com.joysistvi.cinebookapp.model;

public class Seat {

    private final int id;
    private final int theaterId;
    private final String seatCode;
    private final String seatRow;
    private final int seatNumber;

    public Seat(int id, int theaterId, String seatCode, String seatRow, int seatNumber) {
        this.id = id;
        this.theaterId = theaterId;
        this.seatCode = seatCode;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public int getId() {
        return id;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
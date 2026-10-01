package com.joysistvi.cinebookapp.model;

public class Seat {

    private int id;
    private int theaterId;
    private String seatCode;
    private String seatRow;
    private int seatNumber;

    public Seat() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
package com.joysistvi.cinebookapp.model;

import java.time.LocalDateTime;

// Flattened showtime + theater name, just for the "SHOWTIMES FOR: <movie>" table.
public class ShowtimeSchedule {

    private int id;
    private String theaterName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double ticketPrice;

    public ShowtimeSchedule(int id, String theaterName, LocalDateTime startTime, LocalDateTime endTime,
                             double ticketPrice) {
        this.id = id;
        this.theaterName = theaterName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPrice = ticketPrice;
    }

    public int getId() {
        return id;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}

package com.joysistvi.cinebookapp.model;

import java.time.LocalDateTime;

public class Showtime {

    private int id;
    private int movieId;
    private int theaterId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double ticketPrice;
    private String status;

    public Showtime(int id, int movieId, int theaterId, LocalDateTime startTime, LocalDateTime endTime,
                     double ticketPrice, String status) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

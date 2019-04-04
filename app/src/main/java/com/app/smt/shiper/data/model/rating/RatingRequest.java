package com.app.smt.shiper.data.model.rating;

public class RatingRequest {

    private int rating;

    private String description;

    public RatingRequest(int rate, String des) {
        this.rating = rate;
        this.description = des;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

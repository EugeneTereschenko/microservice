package com.example.ec.microservice.web;

import com.example.ec.microservice.domain.TourRating;
import com.sun.istack.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    public RatingDto() {
    }

    public RatingDto(@Min(0) @Max(5) Integer score, @Size(max = 255) String comment, Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }



    public RatingDto(TourRating tourRating) {
        this.score = tourRating.getScore();
        this.comment = tourRating.getComment();
        this.customerId = tourRating.getPk().getCustomerId();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "RatingDto{" +
                "score=" + score +
                ", comment='" + comment + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}

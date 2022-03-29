package com.example.ec.microservice.web;

import com.example.ec.microservice.domain.Tour;
import com.example.ec.microservice.domain.TourRating;
import com.example.ec.microservice.domain.TourRatingPk;
import com.example.ec.microservice.repo.TourRatingRepository;
import com.example.ec.microservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId,
                                 @RequestBody @Validated RatingDto ratingDto){
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    private Tour verifyTour(String tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() -> new NoSuchElementException("Tour does not exists" + tourRepository.findById(tourId)));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }

    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") String tourId,
                                                Pageable pageable) {
        verifyTour(tourId);

        Page<TourRating> ratings = tourRatingRepository.findByTourId(tourId, pageable);
        return new PageImpl<>(
                ratings.get()
                .map(rating -> {
                    RatingDto ratingDto = new RatingDto();
                    ratingDto.setComment(rating.getComment());
                    ratingDto.setCustomerId(rating.getPk().getCustomerId());
                    ratingDto.setScore(rating.getScore());
                    return ratingDto;
                }).collect(Collectors.toList()),
                pageable,
                ratings.getTotalElements()
        );
    }

    @GetMapping(path="/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        Map<String, Double> map = new HashMap<String, Double>(){
            {
                put("average", tourRatingRepository.findByTourId(tourId).stream()
                        .mapToInt(TourRating::getScore).average()
                        .orElseThrow(() ->
                                new NoSuchElementException("Tour has no Ratings")));
            }
        };

        return map;
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request(" + tourId + " for customer" + customerId));
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") String tourId,
                                   @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                     @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if (ratingDto.getScore() != null) {
            rating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            rating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
                       @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

}

package com.example.ec.microservice.service;

import com.example.ec.microservice.domain.Difficulty;
import com.example.ec.microservice.domain.Region;
import com.example.ec.microservice.domain.Tour;
import com.example.ec.microservice.domain.TourPackage;
import com.example.ec.microservice.repo.TourPackageRepository;
import com.example.ec.microservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String description, String blurb,
                           Integer price, String duration, String bullets,
                           String keywords, String tourPackageName,
                           Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does mot exists" + tourPackageName));

        return tourRepository.save(new Tour(title, description, blurb,
                price, duration, bullets,
                keywords, tourPackage, difficulty, region));
    }

    public long total() { return tourRepository.count(); }
}

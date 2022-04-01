package com.example.ec.microservice.service;

import com.example.ec.microservice.domain.Tour;
import com.example.ec.microservice.domain.TourPackage;
import com.example.ec.microservice.repo.TourPackageRepository;
import com.example.ec.microservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String tourPackageName,
                           Map<String, String> details) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("Tour package does mot exists" + tourPackageName));

        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    public long total() { return tourRepository.count(); }
}

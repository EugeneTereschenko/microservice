package com.example.ec.microservice;

import com.example.ec.microservice.domain.Difficulty;
import com.example.ec.microservice.domain.Region;
import com.example.ec.microservice.service.TourPackageService;
import com.example.ec.microservice.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class MicroserviceApplication implements CommandLineRunner {

    @Autowired
    private TourPackageService tourPackageService;

    @Autowired
    private TourService tourService;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        long numOfPackages = tourPackageService.total();

        createTours("ExploreCalifornia.json");
        long numOfTours = tourService.total();
    }


    private void createTourPackages(){
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }


    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
                tourService.createTour(importedTour.getTitle(),
                        importedTour.getDescription(),
                        importedTour.getBlurb(),
                        importedTour.getPrice(),
                        importedTour.getDuration(),
                        importedTour.getBullets(),
                        importedTour.getKeywords(),
                        importedTour.getPackageType(),
                        importedTour.getDifficulty(),
                        importedTour.getRegion())
        );
    }

    private static class TourFromFile {
        private String title, description, blurb, price, duration,
                bullets, keywords, packageType, difficulty, region;

        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY)
                    .readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {
                    });
        }

        protected TourFromFile() {}


        String getTitle() { return title; }
        String getDescription() { return description; }
        String getBlurb() { return blurb; }
        Integer getPrice() { return Integer.valueOf(price); }
        String getDuration() { return duration; }
        String getBullets() { return bullets; }
        String getKeywords() { return keywords; }
        String getPackageType() { return packageType; }
        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }
        Region getRegion() { return Region.valueOf(region); }
    }
}

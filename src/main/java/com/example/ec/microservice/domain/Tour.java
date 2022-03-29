package com.example.ec.microservice.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

@Entity
@Document
public class Tour {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @Indexed
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String blurb;

    @Column
    private Integer price;

    @Column
    private String duration;
    @Indexed
    private String tourPackageCode;

    @Column(length = 2000)
    private String bullets;
    private String tourPackageName;

    @Column
    private String keywords;
    private Map<String, String> details;

    @ManyToOne
    private TourPackage tourPackage;

    @Column
    @Enumerated
    private Difficulty diffuculty;

    @Column
    @Enumerated
    private Region region;

    public Tour() {
    }

    public Tour(String title,
                String description,
                String blurb,
                Integer price,
                String duration,
                String bullets,
                String keywords,
                TourPackage tourPackage,
                Difficulty diffuculty,
                Region region) {
        this.title = title;
        this.description = description;
        this.blurb = blurb;
        this.price = price;
        this.duration = duration;
        this.bullets = bullets;
        this.keywords = keywords;
        this.tourPackage = tourPackage;
        this.diffuculty = diffuculty;
        this.region = region;
    }

    public Tour(String title,
                TourPackage tourPackage,
                Map<String, String> details) {
        this.title = title;
        this.description = description;
        this.blurb = blurb;
        this.price = price;
        this.duration = duration;
        this.bullets = bullets;
        this.keywords = keywords;
        this.tourPackage = tourPackage;
        this.details = details;
        this.diffuculty = diffuculty;
        this.region = region;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBullets() {
        return bullets;
    }

    public void setBullets(String bullets) {
        this.bullets = bullets;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public TourPackage getTourPackage() {
        return tourPackage;
    }

    public void setTourPackage(TourPackage tourPackage) {
        this.tourPackage = tourPackage;
    }

    public Difficulty getDiffuculty() {
        return diffuculty;
    }

    public void setDiffuculty(Difficulty diffuculty) {
        this.diffuculty = diffuculty;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public void setTourPackageCode(String tourPackageCode) {
        this.tourPackageCode = tourPackageCode;
    }

    public String getTourPackageName() {
        return tourPackageName;
    }

    public void setTourPackageName(String tourPackageName) {
        this.tourPackageName = tourPackageName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", blurb='" + blurb + '\'' +
                ", price=" + price +
                ", duration='" + duration + '\'' +
                ", bullets='" + bullets + '\'' +
                ", keywords='" + keywords + '\'' +
                ", tourPackage=" + tourPackage +
                ", diffuculty=" + diffuculty +
                ", region=" + region +
                "id='" + id + '\'' +
                ", details=" + details +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(id, tour.id) &&
                Objects.equals(title, tour.title) &&
                Objects.equals(description, tour.description) &&
                Objects.equals(blurb, tour.blurb) &&
                Objects.equals(price, tour.price) &&
                Objects.equals(duration, tour.duration) &&
                Objects.equals(tourPackageCode, tour.tourPackageCode) &&
                Objects.equals(bullets, tour.bullets) &&
                Objects.equals(tourPackageName, tour.tourPackageName) &&
                Objects.equals(keywords, tour.keywords) &&
                Objects.equals(details, tour.details) &&
                Objects.equals(tourPackage, tour.tourPackage) &&
                diffuculty == tour.diffuculty && region == tour.region;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                title,
                description,
                blurb,
                price,
                duration,
                tourPackageCode,
                bullets,
                tourPackageName,
                keywords,
                details,
                tourPackage,
                diffuculty,
                region);
    }
}

package org.jyu.ties4520.data.rdf.entity;

import org.jyu.ties4520.views.ListItemContentProvider;

import java.time.LocalDate;

public class RdfBookingRequest extends AbstractMapBasedRdfEntity<RdfBookingRequest> implements ListItemContentProvider<RdfBooking> {

    public enum PID {
        bookerName
        , numberOfPlaces
        , numberOfBedrooms
        , metersToTheLake
        , nearestCityName
        , kmToTheNearestCity
        , shift
        , numberOfDays
        , startDay
    }

    public String getBookerName() {
        return getOrDefault(PID.bookerName, "").toString();
    }

    public void setBookerName(String bookerName) {
        put(PID.bookerName, bookerName);
    }

    public Integer getNumberOfPlaces() {
        return Integer.valueOf(getOrDefault(PID.numberOfPlaces, 1).toString());
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        put(PID.numberOfPlaces, numberOfPlaces);
    }

    public Integer getNumberOfBedrooms() {
        return Integer.valueOf(getOrDefault(PID.numberOfBedrooms, 1).toString());
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        put(PID.numberOfBedrooms, numberOfBedrooms);
    }

    public Integer getMetersToTheLake() {
        return Integer.valueOf(getOrDefault(PID.metersToTheLake, 100).toString());
    }

    public void setMetersToTheLake(Integer metersToTheLake) {
        put(PID.metersToTheLake, metersToTheLake);
    }

    public String getNearestCityName() {
        return getOrDefault(PID.nearestCityName, "").toString();
    }

    public void setNearestCityName(String nearestCityName) {
        put(PID.nearestCityName, nearestCityName);
    }

    public Double getKmToTheNearestCity() {
        return Double.valueOf(getOrDefault(PID.kmToTheNearestCity, Double.valueOf(1)).toString());
    }

    public void setKmToTheNearestCity(Double kmToTheNearestCity) {
        put(PID.kmToTheNearestCity, kmToTheNearestCity);
    }

    public Integer getNumberOfDays() {
        return Integer.valueOf(getOrDefault(PID.numberOfDays, 1).toString());
    }

    public void setNumberOfDays(Integer numberOfDays) {
        put(PID.numberOfDays, numberOfDays);
    }

    public LocalDate getStartDay() {
        return (LocalDate) getOrDefault(PID.startDay, LocalDate.now());
    }

    public void setStartDay(LocalDate startDay) {
        put(PID.startDay, startDay);
    }

    public Integer getShift() {
        return Integer.valueOf(getOrDefault(PID.shift, 0).toString());
    }

    public void setShift(Integer shift) {
        put(PID.shift, shift);
    }

}

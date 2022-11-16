package org.jyu.ties4520.data.rdf.entity;

import org.apache.jena.iri.IRI;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.algebra.Op;
import org.jyu.ties4520.views.ListItemContentProvider;

import java.util.Optional;
import java.util.function.Supplier;

public class RdfCottage extends AbstractMapBasedRdfEntity<RdfCottage> implements ListItemContentProvider<RdfCottage> {

    public enum PID {
        cottage, cottageName, imageUrl, numberOfPlaces, numberOfBedrooms, metersToTheLake, nearestCityName, kmToTheNearestCity, address
    }

    public Optional<String> uri() {
        Object oIri = get(PID.cottage.name());
        if (oIri instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) oIri;
            if (rdfNode.isResource()) {
                return Optional.ofNullable(rdfNode.asResource().getURI());
            }
        }
        return Optional.empty();
    }

    public String getCottageName() {
        return getOrDefault(PID.cottageName, "").toString();
    }

    public void setCottageName(String cottageName) {
        put(PID.cottageName, cottageName);
    }

    public String getAddress() {
        return getOrDefault(PID.address, "").toString();
    }

    public void setAddress(String address) {
        put(PID.address, address);
    }

    public String getImageUrl() {
        return getOrDefault(PID.imageUrl, "").toString();
    }

    public void setImageUrl(String imageUrl) {
        put(PID.imageUrl, imageUrl);
    }

    public Integer getNumberOfPlaces() {
        return parseAsInt(getOrDefault(PID.numberOfPlaces, 0));
    }

    public void setNumberOfPlaces(Integer numberOfPlaces) {
        put(PID.numberOfPlaces, numberOfPlaces);
    }

    public Integer getNumberOfBedrooms() {
        return parseAsInt(getOrDefault(PID.numberOfBedrooms, 0));
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        put(PID.numberOfBedrooms, numberOfBedrooms);
    }

    public Double getMetersToTheLake() {
        return parseAsDouble(getOrDefault(PID.metersToTheLake, 0));
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
        return parseAsDouble(getOrDefault(PID.kmToTheNearestCity, 0));
    }

    public void setKmToTheNearestCity(Double kmToTheNearestCity) {
        put(PID.kmToTheNearestCity, kmToTheNearestCity);
    }

    @Override
    public String toString() {
        return getCottageName() != null ? getCottageName() : "";
    }

    @Override
    public Optional<Supplier<String>> headerText() {
        return Optional.ofNullable(() -> getCottageName());
    }

    @Override
    public Optional<Supplier<String>> imageAlt() {
        return headerText();
    }

    @Override
    public Optional<Supplier<String>> imageUrl() {
        return Optional.ofNullable(() -> getImageUrl());
    }

    @Override
    public Optional<Supplier<String>> contentHtmlText() {
        String content = "Number of beds: " + getNumberOfPlaces() + "<br>" +
                "Number of bedrooms: " + getNumberOfBedrooms() + "<br>" +
                "Meters to the lake: " + getMetersToTheLake();
        return Optional.of(() -> content);
    }
}

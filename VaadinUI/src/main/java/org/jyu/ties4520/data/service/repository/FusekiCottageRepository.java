package org.jyu.ties4520.data.service.repository;

import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.rdf.entity.RdfCottage;

import java.util.LinkedList;
import java.util.List;

public class FusekiCottageRepository extends FusekiConnection<RdfCottage> {

    @Override
    public RdfCottage create() {
        return new RdfCottage();
    }

    public List<RdfCottage> findAll() {
        try {
            String query = FusekiHelper.QueryFiles.LIST_COTTAGES.getFileContent();
            return convertPropertyMapsToListOfEntities(resultSet(FUSEKI_ENDPOINT, query));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            return new LinkedList<>();
        }
    }
}
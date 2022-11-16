package org.jyu.ties4520.data.service.repository;

import org.jyu.ties4520.data.rdf.entity.RdfBooking;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FusekiBookingRepository extends FusekiConnection<RdfBooking> {

    @Override
    public RdfBooking create() {
        return new RdfBooking();
    }

    public List<RdfBooking> findAll() {
        try {
            String query = FusekiHelper.QueryFiles.LIST_BOOKINGS.getFileContent();
            return convertPropertyMapsToListOfEntities(resultSet(FUSEKI_ENDPOINT, query));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            return new LinkedList<>();
        }
    }
}
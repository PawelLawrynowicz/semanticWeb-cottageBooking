package org.jyu.ties4520.data.service.repository;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

public class FusekiHelper {

    public static final String QUERIES_BASEURL = "/org/jyu/ties4520/data/service/repository";

    public enum QueryFiles {
        LIST_BOOKINGS(QUERIES_BASEURL + "/booking/bookings.sparql")
        , LIST_COTTAGES(QUERIES_BASEURL + "/cottage/cottages.sparql")
        , NEW_COTTAGE(QUERIES_BASEURL + "/cottage/newCottage.sparql")
        , NEW_BOOKING(QUERIES_BASEURL + "/bookingRequest/newBooking.sparql")
        , BOOKING_PROPOSALS(QUERIES_BASEURL + "/bookingRequest/bookingProposals.sparql")
        , START_DATE_SHIFT(QUERIES_BASEURL + "/bookingRequest/unionForShift.sparql");

        private final String path;

        QueryFiles(String path) {
            this.path = path;
        }

        public ClassPathResource getResource() {
            return new ClassPathResource(path);
        }

        public String getFileContent() throws IOException {
            return IOUtils.toString(getResource().getInputStream(), Charset.forName("UTF8"));
        }
    }


}

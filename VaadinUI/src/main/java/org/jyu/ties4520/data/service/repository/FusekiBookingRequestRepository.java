package org.jyu.ties4520.data.service.repository;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.jena.iri.IRI;
import org.apache.jena.rdf.model.RDFNode;
import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.rdf.entity.RdfBookingRequest;
import org.jyu.ties4520.data.rdf.entity.RdfCottage;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

public class FusekiBookingRequestRepository extends FusekiConnection<RdfBooking> {

    private final FusekiCottageRepository cottageRepository = new FusekiCottageRepository();

    @Override
    public RdfBooking create() {
        return new RdfBooking();
    }

    public List<RdfBooking> getProposals(RdfBookingRequest request) {
        List<Range<ChronoLocalDate>> intervalsToTest = new LinkedList<>();
        LocalDate startDate = request.getStartDay();
        int duration = request.getNumberOfDays();
        LocalDate endDate = startDate.plusDays(duration);
        intervalsToTest.add(Range.between(startDate, endDate));
        boolean useUnion = request.getShift().intValue() > 0;
        if (useUnion) {
            for (Integer shift = 1; shift < request.getShift()+1; shift++) {
                LocalDate startDatePlus = startDate.plusDays(shift);
                LocalDate endDatePlus = startDate.plusDays(duration);
                intervalsToTest.add(Range.between(startDatePlus, endDatePlus));
                LocalDate startDateMinus = startDate.plusDays(-shift);
                LocalDate endDateMinus = startDate.plusDays(duration);
                intervalsToTest.add(Range.between(startDateMinus, endDateMinus));
            }
        }
        return findAll(request, intervalsToTest);
    }

    public List<RdfBooking> findAll(RdfBookingRequest request, List<Range<ChronoLocalDate>> intervals) {
        try {
            if (!intervals.isEmpty()) {
                StrSubstitutor substitutor = new StrSubstitutor();
                String query = FusekiHelper.QueryFiles.BOOKING_PROPOSALS.getFileContent();
                boolean hasUnion = intervals.size() > 1;
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("startInfo", intervals.get(0).getMinimum().toString());
                params.put("endInfo", intervals.get(0).getMaximum().toString());
                String unionForShift = "";
                if (hasUnion) {
                    int shifts = intervals.size();
                    String unionTemplate = FusekiHelper.QueryFiles.START_DATE_SHIFT.getFileContent();
                    for (int i = 1; i < shifts; i++) {
                        Map<String, Object> unionParams = new LinkedHashMap<>();
                        unionParams.put("startInfo", intervals.get(i).getMinimum().toString());
                        unionParams.put("endInfo", intervals.get(i).getMaximum().toString());
                        unionForShift = unionForShift + substitutor.replace(unionTemplate, unionParams, "${", "}");
                    }
                }
                params.put("unionForShift", unionForShift);
                query = substitutor.replace(query, params, "${", "}");
                return convertPropertyMapsToListOfEntities(resultSet(FUSEKI_ENDPOINT, query));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public List<RdfBooking> convertPropertyMapsToListOfEntities(List<Map<String, RDFNode>> rdfNodeMaps) {
        Map<String, RdfCottage> cottagesByUri = new LinkedHashMap<>();
        cottageRepository.findAll().forEach(c -> {
            if(c.uri().isPresent()){
                cottagesByUri.put(c.uri().get(), c);
            }
        });
        List<RdfBooking> bookings = super.convertPropertyMapsToListOfEntities(rdfNodeMaps);
        for (RdfBooking booking : bookings) {
            Optional<RDFNode> cottage =booking.getRdfNode(RdfBooking.PID.cottage.name());
            if(cottage.isPresent() && cottage.get().isResource()){
                String cottageUri = cottage.get().asResource().getURI();
                booking.put(RdfBooking.PID.cottage.name(), cottagesByUri.get(cottageUri));
            }
        }
        return bookings;
    }
}
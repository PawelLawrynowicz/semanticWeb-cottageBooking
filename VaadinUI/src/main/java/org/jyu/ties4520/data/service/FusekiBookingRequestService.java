package org.jyu.ties4520.data.service;

import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.rdf.entity.RdfBookingRequest;
import org.jyu.ties4520.data.service.repository.FusekiBookingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FusekiBookingRequestService {

    private final FusekiBookingRequestRepository requestRepository = new FusekiBookingRequestRepository();

    public List<RdfBooking> search(RdfBookingRequest request) {
        return requestRepository.getProposals(request);
    }

}

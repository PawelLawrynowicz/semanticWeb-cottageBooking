package org.jyu.ties4520.data.service;

import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.service.repository.FusekiBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FusekiBookingService{

    private final FusekiBookingRepository repository = new FusekiBookingRepository();

    public Optional<RdfBooking> get(Integer id) {
        return repository.findById(id);
    }

    public RdfBooking update(RdfBooking entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<RdfBooking> list() {
        return repository.findAll();
    }

    public int count() {
        return repository.count();
    }

}

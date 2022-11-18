package org.jyu.ties4520.data.service;

import org.jyu.ties4520.data.rdf.entity.RdfCottage;
import org.jyu.ties4520.data.service.repository.FusekiCottageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FusekiCottageService {

    private final FusekiCottageRepository repository = new FusekiCottageRepository();

    public Optional<RdfCottage> get(Integer id) {
        return repository.findById(id);
    }

    public RdfCottage update(RdfCottage entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<RdfCottage> list() {
        return repository.findAll();
    }

    public int count() {
        return repository.count();
    }

}

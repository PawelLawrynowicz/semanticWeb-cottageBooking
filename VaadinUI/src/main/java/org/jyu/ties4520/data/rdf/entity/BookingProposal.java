package org.jyu.ties4520.data.rdf.entity;

import java.util.Optional;
import java.util.function.Supplier;

public class BookingProposal extends RdfBooking {

    RdfBookingRequest request;
    RdfCottage cottage;

    public BookingProposal(RdfBookingRequest request, RdfCottage cottage) {
        this.cottage = cottage;
        this.request = request;
    }

    public Optional<RdfCottage> rdfCottage() {
        return Optional.ofNullable(cottage);
    }

    @Override
    public Optional<Supplier<String>> imageUrl() {
        if (rdfCottage().isPresent() && rdfCottage().get().imageUrl().isPresent()) {
            return rdfCottage().get().imageUrl();
        }
        return Optional.empty();
    }

    public Optional<Supplier<String>> headerText() {
        if (rdfCottage().isPresent() && rdfCottage().get().headerText().isPresent()) {
            return rdfCottage().get().headerText();
        }
        return Optional.empty();
    }


    @Override
    public Optional<Supplier<String>> contentHtmlText() {
        return Optional.of(() -> null);
    }
}

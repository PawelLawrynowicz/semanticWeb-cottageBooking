package org.jyu.ties4520.data.rdf.entity;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.jyu.ties4520.views.ListItemContentProvider;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class RdfBooking extends AbstractMapBasedRdfEntity<RdfBooking> implements ListItemContentProvider<RdfBooking> {

    public enum PID {
        id, booking, bookerName, cottage, startDate, endDate
    }

    public Optional<String> uri() {
        Object oIri = get(PID.booking.name());
        if (oIri instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) oIri;
            if (rdfNode.isResource()) {
                return Optional.ofNullable(rdfNode.asResource().getURI());
            }
        }
        return Optional.empty();
    }

    public Integer getId() {
        Object o = getOrDefault(PID.id, 0);
        return parseAsInt(o);
    }

    public void setId(Integer bookingId) {
        put(PID.id, bookingId);
    }

    public String getBookerName() {
        return getOrDefault(RdfBookingRequest.PID.bookerName, "").toString();
    }

    public void setBookerName(String bookerName) {
        put(RdfBookingRequest.PID.bookerName, bookerName);
    }

    public RdfCottage getCottage() {
        Object oCottage = get(PID.cottage.name());
        if (oCottage instanceof RdfCottage) {
            return (RdfCottage) oCottage;
        }
        RdfCottage cottage = new RdfCottage();
        Optional<Resource> resource = parseAsResource(getOrDefault(PID.cottage, new RdfCottage()));
        resource.ifPresent(value -> cottage.setCottageName(value.getLocalName()));
        return cottage;
    }


    public void setCottage(RdfCottage cottage) {
        put(PID.cottage, cottage);
    }

    public LocalDate getStartDate() {
        Object o = getOrDefault(PID.startDate, LocalDate.now());
        return parseAsLocalDate(o);
    }

    public void setStartDate(LocalDate startDay) {
        put(PID.startDate, startDay);
    }

    public LocalDate getEndDate() {
        Object o = getOrDefault(PID.endDate, LocalDate.now());
        return parseAsLocalDate(o);
    }

    public void setEndDate(LocalDate startDay) {
        put(PID.endDate, startDay);
    }

    @Override
    public Optional<Supplier<String>> headerText() {
        return getCottage().headerText().isPresent() ? Optional.ofNullable(getCottage().headerText().get()) : Optional.empty();
    }

    @Override
    public Optional<Supplier<String>> imageAlt() {
        return getCottage().imageAlt().isPresent() ? Optional.ofNullable(getCottage().imageAlt().get()) : Optional.empty();
    }

    @Override
    public Optional<Supplier<String>> imageUrl() {
        return getCottage().imageUrl().isPresent() ? Optional.ofNullable(getCottage().imageUrl().get()) : Optional.empty();
    }

    public String getDateInterval() {
        return "Between " + getStartDate().toString() + " and " + getEndDate().toString() + ".";
    }

    @Override
    public Optional<Supplier<String>> contentHtmlText() {
        Optional<Supplier<String>> cottageContentHtmlText = getCottage().contentHtmlText();
        return Optional.of(() -> getDateInterval() + "<br>" + (getCottage().contentHtmlText().isPresent() ? cottageContentHtmlText.get().get() : ""));
    }
}

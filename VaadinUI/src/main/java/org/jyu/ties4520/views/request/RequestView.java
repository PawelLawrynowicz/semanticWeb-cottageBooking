package org.jyu.ties4520.views.request;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.rdf.entity.RdfBookingRequest;
import org.jyu.ties4520.data.service.FusekiBookingRequestService;
import org.jyu.ties4520.views.ListComponent;
import org.jyu.ties4520.views.MainLayout;

import java.util.List;

@PageTitle("Search for cottages to book")
@Route(value = "", layout = MainLayout.class)
public class RequestView extends Div {

    private final TextField bookerName = new TextField("Booker");
    private final DatePicker startDate = new DatePicker("Start date");
    private final IntegerField numberOfDays = new IntegerField("Days");
    private final IntegerField numberOfPlaces = new IntegerField("Places");
    private final IntegerField numberOfBedrooms = new IntegerField("Bedrooms");
    private final IntegerField metersToTheLake = new IntegerField("Meters to lake");
    private final TextField nearestCityName = new TextField("Nearest city");
    private final NumberField kmToTheNearestCity = new NumberField("Km from city");
    private final IntegerField shift = new IntegerField("Shift (+/- days)");
    private final Button search = new Button("Search");

    private final Binder<RdfBookingRequest> binder = new Binder<>(RdfBookingRequest.class);

    ListComponent<RdfBooking> results = new ListComponent<>();

    public RequestView(FusekiBookingRequestService requestService) {
        addClassName("request-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        add(results);

        binder.bindInstanceFields(this);

        clearForm();

        search.addClickListener(e -> {
            RdfBookingRequest request = binder.getBean();
            List<RdfBooking> proposals = requestService.search(request);
            Notification.show(proposals.size() + " amount of Booking proposals found.");
            updateResultList(request, proposals);
        });
    }

    private void updateResultList(RdfBookingRequest request, List<RdfBooking> proposals) {
        results.configure("Booking proposals", "Available and suitable cottages.");
        results.refreshList(proposals);
    }

    private Component createTitle() {
        return new H3("Cottage booking request");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        // Bind fields. This is where you'd define e.g. validation rules
        metersToTheLake.setStep(100);
        kmToTheNearestCity.setStep(0.5);
        numberOfDays.setMaxWidth(10, Unit.EM);
        numberOfPlaces.setMaxWidth(10, Unit.EM);
        numberOfBedrooms.setMaxWidth(10, Unit.EM);
        shift.setMaxWidth(10, Unit.EM);
        metersToTheLake.setMaxWidth(10, Unit.EM);
        kmToTheNearestCity.setMaxWidth(10, Unit.EM);
        shift.setMin(0);
        shift.setMax(30);
        shift.setHasControls(true);
        numberOfDays.setHasControls(true);
        numberOfPlaces.setHasControls(true);
        numberOfBedrooms.setHasControls(true);
        metersToTheLake.setHasControls(true);
        kmToTheNearestCity.setHasControls(true);
        metersToTheLake.setMin(0);
        numberOfDays.setMin(0);
        numberOfPlaces.setMin(0);
        numberOfBedrooms.setMin(0);
        metersToTheLake.setMin(0);
        kmToTheNearestCity.setMin(0);

        binder.forField(numberOfPlaces)
                .bind(RdfBookingRequest.PID.numberOfPlaces.name());
        binder.forField(numberOfBedrooms)
                .bind(RdfBookingRequest.PID.numberOfBedrooms.name());

        binder.forField(metersToTheLake)
                .bind(RdfBookingRequest.PID.metersToTheLake.name());

        binder.forField(nearestCityName)
                .bind(RdfBookingRequest.PID.nearestCityName.name());
        binder.forField(kmToTheNearestCity)
                .bind(RdfBookingRequest.PID.kmToTheNearestCity.name());

        binder.forField(numberOfDays)
                .bind(RdfBookingRequest.PID.numberOfDays.name());
        binder.forField(startDate)
                .bind(RdfBookingRequest.PID.startDay.name());
        binder.forField(shift)
                .bind(RdfBookingRequest.PID.shift.name());

        binder.bindInstanceFields(this);

        formLayout.add(bookerName);
        formLayout.add(
                new HorizontalLayout(startDate, shift, numberOfDays)
                , new HorizontalLayout(numberOfPlaces, numberOfBedrooms)
                , new HorizontalLayout(nearestCityName, kmToTheNearestCity)
                , metersToTheLake);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(search);
        return buttonLayout;
    }

    private void clearForm() {
        this.binder.setBean(new RdfBookingRequest());
    }

}

package org.jyu.ties4520.views.bookings;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.*;
import org.jyu.ties4520.data.rdf.entity.RdfBooking;
import org.jyu.ties4520.data.service.FusekiBookingService;
import org.jyu.ties4520.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Bookings")
@Route(value = "bookings/:id?/:action?(edit)", layout = MainLayout.class)
public class BookingsView extends Div implements BeforeEnterObserver {

    private final String ID = "id";
    private final String BOOKING_EDIT_ROUTE_TEMPLATE = "bookings/%s/edit";

    private final Grid<RdfBooking> grid = new Grid<>(RdfBooking.class, false);

    private final TextField id = new TextField("Booking id");
    private final TextField bookerName = new TextField("Booker name");
    private final DatePicker startDate = new DatePicker("Starting date");
    private final DatePicker endDate = new DatePicker("Ending date");
    private final TextField cottage = new TextField("Cottage");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<RdfBooking> binder;

    private RdfBooking booking;

    private final FusekiBookingService bookingService;

    @Autowired
    public BookingsView(FusekiBookingService bookingService) {
        this.bookingService = bookingService;
        addClassNames("bookings-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn(RdfBooking.PID.id.name()).setAutoWidth(true);
        grid.addColumn(RdfBooking.PID.bookerName.name()).setAutoWidth(true);
        grid.addColumn(RdfBooking.PID.cottage.name()).setAutoWidth(true);
        grid.addColumn(RdfBooking.PID.startDate.name()).setAutoWidth(true);
        grid.addColumn(RdfBooking.PID.endDate.name()).setAutoWidth(true);
        grid.setItems(bookingService.list());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(BOOKING_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(BookingsView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(RdfBooking.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new Converter<>() {
                                   @Override
                                   public Result<Object> convertToModel(String value, ValueContext context) {
                                       return null;
                                   }

                                   @Override
                                   public String convertToPresentation(Object value, ValueContext context) {
                                       return value != null ? value.toString() : "";
                                   }
                               }
                )
                .bindReadOnly(RdfBooking.PID.cottage.name());

        binder.forField(id)
                .withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind(RdfBooking.PID.id.name());

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.booking == null) {
                    this.booking = new RdfBooking();
                }
                binder.writeBean(this.booking);
                bookingService.update(this.booking);
                clearForm();
                refreshGrid();
                Notification.show("Booking2 details stored.");
                UI.getCurrent().navigate(BookingsView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the booking2 details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> bookingId = event.getRouteParameters().get(ID).map(Integer::valueOf);
        if (bookingId.isPresent()) {
            Optional<RdfBooking> booking2FromBackend = bookingService.get(bookingId.get());
            if (booking2FromBackend.isPresent()) {
                populateForm(booking2FromBackend.get());
            } else {
                Notification.show(String.format("The requested booking was not found, ID = %s", bookingId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(BookingsView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        formLayout.add(id, bookerName, cottage, startDate, endDate);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

//        splitLayout.addToSecondary(editorLayoutDiv); //TODO
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(RdfBooking value) {
        this.booking = value;
        binder.readBean(this.booking);

    }
}

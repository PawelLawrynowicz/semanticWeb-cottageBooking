package org.jyu.ties4520.views.cottages;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jyu.ties4520.data.rdf.entity.RdfCottage;
import org.jyu.ties4520.data.service.FusekiCottageService;
import org.jyu.ties4520.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Cottages")
@Route(value = "cottages", layout = MainLayout.class)
public class CottagesView extends Main implements HasComponents, HasStyle, BeforeEnterObserver {

    public final String COTTAGE_ID = "cottageID";

    private OrderedList imageContainer = new OrderedList();
    private TextField cottageId = new TextField("ID");
    private TextField cottageName = new TextField("Name");
    private TextField nearestCity = new TextField("Nearest City");
    private TextField nearestLake = new TextField("Nearest Lake");
    private NumberField metersToLake = new NumberField("Meter to lake");
    private NumberField kmsFromCity = new NumberField("Kilometers from city");
    FormLayout formLayout = new FormLayout(cottageId, cottageName, nearestCity, nearestLake, metersToLake, kmsFromCity);
    Div editorDiv = new Div(formLayout);

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    HorizontalLayout buttonLayout = new HorizontalLayout(save, cancel);
    Div editorLayoutDiv = new Div(editorDiv, buttonLayout);

    private RdfCottage cottage;
    private final FusekiCottageService cottageService ;

    private final BeanValidationBinder<RdfCottage> binder;

    @Autowired
    public CottagesView(FusekiCottageService cottageService) {
        this.cottageService = cottageService;
        this.binder = new BeanValidationBinder<>(RdfCottage.class);
        constructUI();
        configureForm();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> cottageId = event.getRouteParameters().get(COTTAGE_ID).map(Integer::valueOf);
        if (cottageId.isPresent()) {
            Optional<RdfCottage> cottageFromBackend = cottageService.get(cottageId.get());
            if (cottageFromBackend.isPresent()) {
                populateForm(cottageFromBackend.get());
            } else {
                Notification.show(String.format("The requested cottage was not found, ID = %s", cottageId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshList();
            }
        } else {
            refreshList();
        }
    }

    private void configureForm() {

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(metersToLake)
                .bind(RdfCottage.PID.metersToTheLake.name());
        binder.forField(kmsFromCity)
                .bind(RdfCottage.PID.kmToTheNearestCity.name());

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshList();
        });

        save.addClickListener(e -> {
            try {
                if (this.cottage == null) {
                    this.cottage = new RdfCottage();
                }
                binder.writeBean(this.cottage);
                cottageService.update(this.cottage);
                clearForm();
                refreshList();
                Notification.show("Cottage details stored.");
                UI.getCurrent().navigate(CottagesView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the cottage details.");
            }
        });
    }

    private void constructUI() {
        addClassNames("cottages-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        SplitLayout splitLayout = new SplitLayout();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("items-center", "justify-between");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Cottages");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph description = new Paragraph("Available cottages");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        headerContainer.add(header, description);

//        Select<String> sortBy = new Select<>();
//        sortBy.setLabel("Sort by");
//        sortBy.setItems("Popularity", "Newest first", "Oldest first");
//        sortBy.setValue("Popularity");

        imageContainer.removeAll();
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");

//        splitLayout.addToPrimary(container);
//        createEditorLayout(splitLayout);
        container.add(header/*, sortBy*/);
        add(container, imageContainer);

    }

    private void configureEditorLayout(SplitLayout splitLayout) {
        editorLayoutDiv.setClassName("editor-layout");
        editorDiv.setClassName("editor");
        configureButtonLayout(editorLayoutDiv);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void configureButtonLayout(Div editorLayoutDiv) {
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void refreshList() {
        imageContainer.removeAll();
        for (RdfCottage rdfCottage : cottageService.list()) {
            imageContainer.add(new CottagesViewCard(rdfCottage));
        }
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(RdfCottage value) {
        this.cottage = value;
        binder.readBean(this.cottage);
    }
}
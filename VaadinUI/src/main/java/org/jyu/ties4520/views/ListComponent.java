package org.jyu.ties4520.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.jyu.ties4520.data.rdf.entity.AbstractMapBasedRdfEntity;

import java.util.List;

public class ListComponent<T extends AbstractMapBasedRdfEntity<T> & ListItemContentProvider<T>> extends Main{

    public final OrderedList imageContainer = new OrderedList();

    public final H2 header = new H2();
    public final Paragraph description = new Paragraph();
    public final VerticalLayout headerContainer = new VerticalLayout(header, description);
    public final HorizontalLayout container = new HorizontalLayout(headerContainer);

    public ListComponent(){
        addClassNames("cottages-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");
        container.addClassNames("items-center", "justify-between");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        description.addClassNames("mb-xl", "mt-0", "text-secondary");
        imageContainer.addClassNames("gap-m", "grid", "list-none", "m-0", "p-0");
        add(container, imageContainer);
    }

    public void configure(String headerText, String descriptionText) {
        header.setText(headerText);
        description.setText(descriptionText);
    }

    public void refreshList(List<T> list) {
        imageContainer.removeAll();
        for (T listItem : list) {
            imageContainer.add(listItem.asListItemComponent());
        }
    }
}

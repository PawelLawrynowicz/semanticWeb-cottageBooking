package org.jyu.ties4520.views;

import com.vaadin.flow.component.html.*;

public class ListItemComponent extends ListItem {

    public ListItemComponent(String imageUrl
            , String imageAlt
            , String headerText
            , String subtitleText
            , String contentHtmlText
            , String badgeText) {
        addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");

        Div div = new Div();
        div.addClassNames("bg-contrast"
                , "flex items-center"
                , "justify-center"
                , "mb-m"
                , "overflow-hidden"
                , "rounded-m w-full");
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(imageUrl);
        image.setAlt(imageAlt);

        div.add(image);

        Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(headerText);

        Span subtitle = new Span();
        subtitle.addClassNames("text-s", "text-secondary");
        subtitle.setText(subtitleText);

        Paragraph description = new Paragraph();
        description.getElement().setProperty("innerHTML", contentHtmlText);
        description.addClassName("my-m");

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText(badgeText);

        add(div, header, subtitle, description, badge);

    }
}
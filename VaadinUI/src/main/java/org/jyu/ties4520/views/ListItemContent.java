package org.jyu.ties4520.views;

import org.jyu.ties4520.data.rdf.entity.AbstractMapBasedRdfEntity;

import java.util.function.Supplier;

public class ListItemContent<T extends AbstractMapBasedRdfEntity<T>> implements ListItemContentProvider<T>{

    Supplier<String> imageUrl;
    Supplier<String> imageAlt;
    Supplier<String> headerText;
    Supplier<String> subtitleText;
    Supplier<String> contentHtmlText;
    Supplier<String> badgeText;

    public ListItemContent(Supplier<String> imageUrl
            , Supplier<String> imageAlt
            , Supplier<String> headerText
            , Supplier<String> subtitleText
            , Supplier<String> contentHtmlText
            , Supplier<String> badgeText) {
        this.imageUrl = imageUrl;
        this.imageAlt = imageAlt;
        this.headerText = headerText;
        this.subtitleText = subtitleText;
        this.contentHtmlText = contentHtmlText;
        this.badgeText = badgeText;
    }

    public ListItemComponent asListItemComponent(){
        return new ListItemComponent(
                text(imageUrl)
                , text(imageAlt)
                , text(headerText)
                , text(subtitleText)
                , text(contentHtmlText)
                , text(badgeText)
        );
    }
}

package org.jyu.ties4520.views;

import org.jyu.ties4520.data.rdf.entity.AbstractMapBasedRdfEntity;

import java.util.Optional;
import java.util.function.Supplier;

public interface ListItemContentProvider<T extends AbstractMapBasedRdfEntity<T>> {

    default Optional<Supplier<String>> imageUrl(){
        return Optional.empty();
    }

    default Optional<Supplier<String>> imageAlt(){
        return Optional.empty();
    }

    default Optional<Supplier<String>> headerText(){
        return Optional.empty();
    }

    default Optional<Supplier<String>> subtitleText(){
        return Optional.empty();
    }

    default Optional<Supplier<String>> contentHtmlText(){
        return Optional.empty();
    }

    default Optional<Supplier<String>> badgeText(){
        return Optional.empty();
    }

    default ListItemComponent asListItemComponent(){
        return new ListItemComponent(
                text(imageUrl().orElse(() -> ""))
                , text(imageUrl().orElse(() -> ""))
                , text(headerText().orElse(() -> ""))
                , text(subtitleText().orElse(() -> ""))
                , text(contentHtmlText().orElse(() -> ""))
                , text(badgeText().orElse(() -> ""))
        );
    }

    default ListItemComponent asListItemComponent(Supplier<String> imageUrl
            , Supplier<String> imageAlt
            , Supplier<String> headerText
            , Supplier<String> subtitleText
            , Supplier<String> contentHtmlText
            , Supplier<String> badgeText){
        return new ListItemComponent(
                text(imageUrl)
                , text(imageAlt)
                , text(headerText)
                , text(subtitleText)
                , text(contentHtmlText)
                , text(badgeText)
        );
    }

    default String text(Supplier<String> s){
        return s==null?"":s.get();
    }


}

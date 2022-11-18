package org.jyu.ties4520.views.cottages;

import org.jyu.ties4520.data.rdf.entity.RdfCottage;
import org.jyu.ties4520.views.ListItemComponent;

public class CottagesViewCard extends ListItemComponent {

    public CottagesViewCard(RdfCottage cottage) {
        super(cottage.getImageUrl(), cottage.getCottageName(), cottage.getCottageName(), cottage.getAddress(), getCottageHtmlContent(cottage), cottage.getNearestCityName());
    }

    public static String getCottageHtmlContent(RdfCottage cottage){
        String content =
        "Number of beds: " + cottage.getNumberOfPlaces() + "<br>" +
                "Number of bedrooms: " + cottage.getNumberOfBedrooms() + "<br>" +
                "Meters to the lake: " + cottage.getMetersToTheLake();
        return content;
    }
}

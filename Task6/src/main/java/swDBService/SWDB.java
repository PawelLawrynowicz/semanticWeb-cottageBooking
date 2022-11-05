package swDBService;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

public class SWDB {

    private String queryResult = "";

    public void searchForResult(String pathDB,
                                String p1, String p2, String p3,
                                String p4, String p5, String p6,
                                String p7, String p8, String p9) {
        String maxIntSPAQRL = "2147483647";
        if (p2 == "")
            p2 = "0";
        if (p3 == "")
            p3 = "0";
        if (p4 == "")
            p4 = maxIntSPAQRL;
        if (p6 == "")
            p6 = maxIntSPAQRL;
        if (p7 == "")
            p7 = "0";
        if (p8 == "")
            p8 = "2022-01-01";
        if (p9 == "")
            p9 = maxIntSPAQRL;

        System.out.println("Do query...");

        Model model = RDFDataMgr.loadModel(pathDB);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
        OntModel ontModel = ModelFactory.createOntologyModel(ontModelSpec, model);

        String queryString =
                "PREFIX : <http://localhost:8081/task6/BookingDB.ttl#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT  ?bookerName ?bookingNumber ?cottageAddress\n" +
                "        ?cottageImage ?cottagePlaces ?cottageBedrooms\n" +
                "        ?cottageDistanceFromLake ?cottageClosestCity\n" +
                "        ?cottageDistanceToCity ?bookingStartDate\n" +
                "        ?bookingPeriod\n" +
                "WHERE {\n" +
                "    ?booking rdf:type :Booking .\n" +
                "    ?cottage rdf:type :Cottage .\n" +
                "    ?booking :hasCottage ?cottage .\n" +
                "    ?cottage :hasName ?cottageName.\n" +
                "    ?booking :hasBookerName ?bookerName .\n" +
                "    ?booking :hasId ?bookingNumber .\n" +
                "    ?cottage :hasAddress ?cottageAddress .\n" +
                "    ?cottage :hasImage ?cottageImage .\n" +
                "    ?cottage :hasPlaces ?cottagePlaces .\n" +
                "    ?cottage :hasBedrooms ?cottageBedrooms .\n" +
                "    ?cottage :hasDistanceFromTheLake ?cottageDistanceFromLake .\n" +
                "    ?cottage :hasClosestCity ?cottageClosestCity .\n" +
                "    ?cottage :hasDistanceToCity ?cottageDistanceToCity .\n" +
                "    ?booking :hasStartDate ?bookingStartDate .\n" +
                "    ?booking :hasBookingDuration ?bookingPeriod .\n" +
                "    ?booking :startDayMaxShift ?bookingShift .\n" +
                "    FILTER REGEX(?bookerName, '" + p1 + "', 'i') .\n" +
                "    FILTER (?cottagePlaces > " + p2 + ") .\n" +
                "    FILTER (?cottageBedrooms > " + p3 + ") .\n" +
                "    FILTER (?cottageDistanceFromLake < " + p4 + ") .\n" +
                "    FILTER REGEX(?cottageClosestCity, '" + p5 + "', 'i') .\n" +
                "    FILTER (?cottageDistanceToCity < " + p6 + ") .\n" +
                "    FILTER (?bookingPeriod > " + p7 + ") .\n" +
                //"    FILTER (?bookingStartDate > \"2022-01-01\"^^xsd:date) .\n" +
                "    FILTER (?bookingShift < " + p9 + ") . \n" +
                "}";

        Dataset dataset = DatasetFactory.create(ontModel);
        Query q = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(q, dataset);
        ResultSet resultSet = exec.execSelect();

        queryResult += "Results: ---";
        while (resultSet.hasNext()) {
            QuerySolution row = resultSet.next();
            row.varNames().forEachRemaining(s -> {
                        queryResult += s + ": " + (row.get(s).asLiteral().getString() + "\n");
                    }
            );
            queryResult += ("\n");
        }
        queryResult += "------------";
    }

    public String getResult() {
        queryResult += "\nDone!";
        return queryResult;
    }
}

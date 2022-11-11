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
                "PREFIX accomodation: <https://semantiweb-cottagebooking.azurewebsites.net/task6/ontology.owl#> " +
                        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                        "PREFIX : <http://localhost:8081/task6/BookingDB.ttl#>\n" +
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "SELECT  \n" +
                        "\t\t?bookerName \n" +
                        "\t\t?bookingNumber \n" +
                        "\t\t?cottageAddress\n" +
                        "\t\t?cottageImage \n" +
                        "\t\t?cottagePlaces \n" +
                        "\t\t?cottageBedrooms\n" +
                        "\t\t?cottageDistanceFromLake \n" +
                        "\t\t?cottageClosestCity\n" +
                        "\t\t?cottageDistanceToCity \n" +
                        "\t\t?bookingStartDate\n" +
                        "\t\t?bookingPeriod\n" +
                        "WHERE {\n" +
                        "\t\t?booking rdf:type accomodation:Booking .\n" +
                        "\t\t?cottage rdf:type accomodation:Cottage .\n" +
                        "\t\t?booking accomodation:hasCottage ?cottage .\n" +
                        "\t\t?cottage accomodation:hasName ?cottageName.\n" +
                        "\t\t?booking accomodation:hasBookerName ?bookerName .\n" +
                        "\t\t?booking accomodation:hasId ?bookingNumber .\n" +
                        "\t\t?cottage accomodation:hasAddress ?cottageAddress .\n" +
                        "\t\t?cottage accomodation:hasImage ?cottageImage .\n" +
                        "\t\t?cottage accomodation:hasPlaces ?cottagePlaces .\n" +
                        "\t\t?cottage accomodation:hasBedrooms ?cottageBedrooms .\n" +
                        "\t\t?cottage accomodation:hasDistanceFromTheLake ?cottageDistanceFromLake .\n" +
                        "\t\t?cottage accomodation:hasClosestCity ?cottageClosestCity .\n" +
                        "\t\t?cottage accomodation:hasDistanceToCity ?cottageDistanceToCity .\n" +
                        "\t\t?booking accomodation:hasStartDate ?bookingStartDate .\n" +
                        "\t\t?booking accomodation:hasBookingDuration ?bookingPeriod .\n" +
                        "\t\t?booking accomodation:startDayMaxShift ?bookingShift .\n" +
                        "\t\tFILTER REGEX(?bookerName, '" + p1 + "', 'i') .\n" +
                        "\t\tFILTER (?cottagePlaces > " + p2 + ") .\n" +
                        "\t\tFILTER (?cottageBedrooms > " + p3 + ") .\n" +
                        "\t\tFILTER (?cottageDistanceFromLake < " + p4 + ") .\n" +
                        "\t\tFILTER REGEX(?cottageClosestCity, '" + p5 + "', 'i') .\n" +
                        "\t\tFILTER (?cottageDistanceToCity < " + p6 + ") .\n" +
                        "\t\tFILTER (?bookingPeriod > " + p7 + ") .\n" +
                        "\t\tFILTER (xsd:date(?bookingStartDate) > xsd:date(\"" + p8 + "\")) .\n" +
                        "\t\tFILTER (?bookingShift < " + p9 + ") . \n" +
                        "}";

        Dataset dataset = DatasetFactory.create(ontModel);
        Query q = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(q, dataset);
        ResultSet resultSet = exec.execSelect();

        queryResult += "Results:\n------------\n";
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

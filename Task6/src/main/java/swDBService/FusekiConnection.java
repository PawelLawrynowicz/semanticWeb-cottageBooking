package swDBService;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FusekiConnection {

    public static List<Map<String, RDFNode>> resultSet(String fusekiEndpoint, String sparql){
        List<Map<String, RDFNode>> resultList = new LinkedList<>();
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.newBuilder();
        try (RDFConnection conn = builder.destination(fusekiEndpoint).build()) {
            conn.queryResultSet(sparql, (resultSet) -> {
                while (resultSet.hasNext()){
                    Map<String, RDFNode> row = new LinkedHashMap<>();
                    QuerySolution qs = resultSet.next();
                    qs.varNames().forEachRemaining(varName -> row.put(varName, qs.get(varName)));
                    resultList.add(row);
                }
            });
        }
        return resultList;
    }

    public static void main(String[] args) {
        String sparql = "PREFIX : <http://localhost:8081/task6/BookingDB.ttl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT  ?bookerName ?bookingNumber ?cottageAddress\n" +
                "        ?cottageImage ?cottagePlaces ?cottageBedrooms\n" +
                "        ?cottageDistanceFromLake ?cottageClosestCity\n" +
                "        ?cottageDistanceToCity ?bookingStartDate\n" +
                "        ?bookingPeriod\n" +
                "WHERE {\n" +
                "    #internal\n" +
                "    ?booking rdf:type :Booking .\n" +
                "    ?cottage rdf:type :Cottage .\n" +
                "    ?booking :hasCottage ?cottage .\n" +
                "    ?cottage :hasName ?cottageName.\n" +
                "    #external\n" +
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
                "  \t?booking :startDayMaxShift ?bookingShift .\n" +
                "    #Joona\n" +
                "    FILTER REGEX(?bookerName, 'Joona', 'i') .\n" +
                "   \tFILTER (?cottagePlaces > 0) .\n" +
                "    FILTER (?cottageBedrooms > 0) .\n" +
                "\tFILTER (?cottageDistanceFromLake > 0) .\n" +
                "  \tFILTER REGEX(?cottageClosestCity, '', 'i') .\n" +
                "  \tFILTER (?cottageDistanceToCity > 0) .\n" +
                "  \tFILTER (?bookingPeriod > 0) .\n" +
                "  \tFILTER (?bookingStartDate > \"2022-01-01\"^^xsd:date) .\n" +
                " \tFILTER (?bookingShift > 0) .\n" +
                "}";
        for (Map<String, RDFNode> stringRDFNodeMap : FusekiConnection.resultSet("http://localhost:8080/fuseki/Task6/sparql", sparql)) {
            stringRDFNodeMap.forEach((s, rdfNode) -> {
                System.out.println(s + " - " + rdfNode.asLiteral().getString());
            });
        }
    }
}

package org.jyu.ties4520.data.service.repository;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.jyu.ties4520.data.rdf.entity.AbstractMapBasedRdfEntity;

import java.util.*;

public abstract class FusekiConnection<ENTITY extends AbstractMapBasedRdfEntity<ENTITY>> {

    public static final String FUSEKI_ENDPOINT = "https://semantiweb-cottagebooking.azurewebsites.net/fuseki/CottageBooking/sparql";
    public abstract ENTITY create();

    public List<ENTITY> convertPropertyMapsToListOfEntities(List<Map<String, RDFNode>> rdfNodeMaps){
        List<ENTITY> entities = new LinkedList<>();
        for (Map<String, RDFNode> rdfNodeMap : rdfNodeMaps) {
            entities.add(create().put(rdfNodeMap));
        }
        return entities;
    }

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

    public int count() {
        return 100; //TODO
    }

    public List<ENTITY> findAll() {
        return new LinkedList<>(); //TODO
    }

    public void deleteById(Integer id) {
        //TODO
    }

    public ENTITY save(ENTITY entity) {
        //TODO
        return entity;
    }

    public Optional<ENTITY> findById(Integer id) {
        return Optional.empty(); //TODO
    }
}

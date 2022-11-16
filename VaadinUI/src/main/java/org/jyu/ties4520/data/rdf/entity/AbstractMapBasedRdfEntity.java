package org.jyu.ties4520.data.rdf.entity;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A map-based object holding data get from a result set.
 *
 * @param <IMPLEMENTATION>
 */
public abstract class AbstractMapBasedRdfEntity<IMPLEMENTATION extends AbstractMapBasedRdfEntity<IMPLEMENTATION>> extends LinkedHashMap<String, Object> {

    public Object get(String key) {
        return super.get(key.toLowerCase());
    }

    public Optional<RDFNode> getRdfNode(String key) {
        Object ret = super.get(key.toLowerCase());
        if(ret instanceof RDFNode){
            return Optional.ofNullable((RDFNode) ret);
        }
        return Optional.empty();
    }


    public Object getOrDefault(String key, Object defaultValueWhenNull) {
        return super.getOrDefault(key.toLowerCase(), defaultValueWhenNull);
    }

    public Object getOrDefault(Enum key, Object defaultValueWhenNull) {
        return super.getOrDefault(key.name().toLowerCase(), defaultValueWhenNull);
    }

    public Object put(String key, Object value) {
        return super.put(key.toLowerCase(), value);
    }

    public Object put(Enum key, Object value) {
        return super.put(key.name().toLowerCase(), value);
    }

    public IMPLEMENTATION put(Map<? extends String, ?> map) {
        map.forEach((k, o) -> put(k.toLowerCase(), o));
        return (IMPLEMENTATION) this;
    }

    protected Optional<Resource> parseAsResource(Object o) {
        if (o != null && o instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) o;
            if (rdfNode.isResource()) {
                return Optional.of(rdfNode.asResource());
            }
        }
        return Optional.empty();
    }

    protected LocalDate parseAsLocalDate(Object o) {
        if (o != null && o instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) o;
            Literal literal = rdfNode.asLiteral();
            if (literal != null && literal.getValue() != null) {
                String value = literal.getValue().toString();
                return LocalDate.parse(value);
            }
        }
        return LocalDate.now();
    }

    protected Integer parseAsInt(Object o) {
        if (o != null && o instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) o;
            Literal literal = rdfNode.asLiteral();
            if (literal != null && literal.getValue() != null) {
                return Integer.valueOf(literal.getValue().toString());
            }
        }
        return 0;
    }

    protected Double parseAsDouble(Object o) {
        if (o != null && o instanceof RDFNode) {
            RDFNode rdfNode = (RDFNode) o;
            Literal literal = rdfNode.asLiteral();
            if (literal != null && literal.getValue() != null) {
                return Double.valueOf(literal.getValue().toString());
            }
        }
        return 0.0;
    }
}

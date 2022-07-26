import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.attribute.Geo;
import org.janusgraph.core.attribute.Geoshape;
import org.janusgraph.example.GraphOfTheGodsFactory;


import java.util.List;
import java.util.Map;

public class connect {

    public static void main(String[] args) throws Exception {
        JanusGraph graph = JanusGraphFactory.open("src/conf/remote.properties");
        System.out.println(graph.toString());
        GraphTraversalSource g = graph.traversal();
        if (g.V().count().next() == 0) {
            // load the schema and graph data
            GraphOfTheGodsFactory.load(graph);
        }
        Vertex saturnProps = g.V().has("name", "saturn").next();
        saturnProps.properties().forEachRemaining(prop -> System.out.println(prop.toString()));
        List<Edge> places = g.E().has("place", Geo.geoWithin(Geoshape.circle(37.97, 23.72, 50))).toList();
        System.out.println(places.toString());
        System.exit(0);
        graph.close();
    }
}


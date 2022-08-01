import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;



import java.util.List;

public class connect {
    public static void main(String[] args) throws Exception {
        JanusGraph graph = JanusGraphFactory.open("src/conf/remote.properties");
        System.out.println(graph.toString());
        JanusGraphVertex V1 = graph.addVertex();
        V1.property("name", "V1");
        JanusGraphVertex V2 = graph.addVertex();
        V2.property("name", "V2");
        JanusGraphVertex V3 = graph.addVertex();
        V3.property("name", "V3");

        JanusGraphVertex V4 = graph.addVertex();
        V4.property("name", "V4");


        V1.addEdge("simple edge",V2);
        V2.addEdge("simple edge",V3);
        V3.addEdge("simple edge",V4);

        graph.tx().commit();

        GraphTraversalSource G = graph.traversal();
        Vertex v = G.V().has("name", "V1").next();

        System.out.println(v.property("name").toString());
        graph.close();
    }
}


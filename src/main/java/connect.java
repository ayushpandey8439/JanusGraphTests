import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.apache.tinkerpop.gremlin.groovy.jsr223.dsl.credential.__.*;


public class connect {
    public static void main(String[] args) throws Exception {
        JanusGraph graph = JanusGraphFactory.open("src/conf/remote.properties");
        try (graph) {
            graphDefinition G = readFile("datasets/simpleSanityTests/simpleGraph");
            GraphTraversalSource TS = graph.traversal();
            HashSet<Integer> vertices = new HashSet<>();
            for (int source : G.edges.keySet()) {
                for (int target : G.edges.get(source)) {
                    if (!vertices.contains(source)) {
                        graph.addVertex("name", Integer.toString(source));
                        vertices.add(source);
                    }
                    if (!vertices.contains(target)) {
                        graph.addVertex("name", Integer.toString(target));
                        vertices.add(target);
                    }
                    try {
                        Vertex V1 = TS.V().has("name", Integer.toString(source)).next();
                        Vertex V2 = TS.V().has("name", Integer.toString(target)).next();
                        V1.addEdge("simple edge", V2);
                    } catch (Exception e) {
                        System.out.println("Create Edge failed for " + source + " -> " + target + " Message: " + e);
                    }
                }
            }


            List<Path> paths = TS.V().has("name", "0").repeat(out()).until(has("name", "2")).path().toList();
            for (Path p : paths) {
                System.out.println(p);
            }

            System.out.println("");
            graph.tx().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static graphDefinition readFile(String filePath) throws IOException {
        testUtils testInstance = new testUtils();
        graphDefinition gdef = testInstance.createEdgeMap(filePath, ",");
        return gdef;
    }
}


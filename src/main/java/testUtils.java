import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class testUtils {

    public graphDefinition createEdgeMap(String filepath, String separator) {
        HashMap<Integer, int[]> edgeMap = new HashMap<>();
        int root = -2;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine();
            while (line != null) {
                if (line.charAt(0) != 'R') {
                    String[] edge = line.split(separator);
                    int source = Integer.parseInt(edge[0]);
                    int target = Integer.parseInt(edge[1]);
                    int[] edgeSet = edgeMap.get(source);
                    if (edgeSet == null) {
                        edgeMap.put(source, new int[]{target});
                    } else {
                        edgeMap.put(source, ArrayUtils.addAll(edgeSet, target));
                    }
                } else if (line.charAt(0) == 'R') {
                    root = Integer.parseInt(line.split(separator)[1]);
                }
                line = br.readLine();
            }


        } catch (Exception e) {
            System.out.println("Could not add line to the edgemap");
        }
        return new graphDefinition(edgeMap, root);
    }
}
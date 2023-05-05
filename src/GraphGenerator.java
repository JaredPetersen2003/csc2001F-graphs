// Create a Random Graph Using
// Random Edge Generation in Java
import java.util.*;
import java.io.*;

public class GraphGenerator {

    Graph graph;
    ArrayList<String> edges;
    File output;
    private final int numEdges;
    private final int numVertices;
 
    // Creating the constructor
    public GraphGenerator(int numVertices, int numEdges)
    {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        int edgesGenerated = 0;
        graph = new Graph();
        edges = new ArrayList<>();
        Random random = new Random();
        File output = null;       

        // List of nodes
        ArrayList<Vertex> nodeList = new ArrayList<>(numVertices);

        // Add nodes to list
        for (int i = 0; i < numVertices; i++){
            String nodeName = "node" + String.format("%03d", i);
            nodeList.add(new Vertex(nodeName));    
        }

        ArrayList<Vertex> nodeCopy = (ArrayList<Vertex>) nodeList.clone();
        Collections.shuffle(nodeCopy);

        for (int i = 0; i < nodeCopy.size() - 1; i++) {
            Vertex vertexA = nodeCopy.get(i);
            Vertex vertexB = nodeCopy.get(i + 1);

            if (checkValidEdge(vertexA, vertexB)){
                Double weight = (double)random.nextInt(10) + 1;

                graph.addEdge(vertexA.name, vertexB.name, weight);
                String edge = vertexA.name + " " + vertexB.name  + " " + weight;
                edges.add(edge);
                edgesGenerated++;
                System.out.println(edgesGenerated);
            }

        }



        //Generate random edges
        while (edgesGenerated < this.numEdges) {

            // Select two random vertices
            Vertex vertexA = graph.getVertex(nodeList.get(random.nextInt(numVertices)).name);
            Vertex vertexB = graph.getVertex(nodeList.get(random.nextInt(numVertices)).name);
   

            // If the vertices are not the same then check if an edge exists between them

            if (checkValidEdge(vertexA, vertexB)){
                Double weight = (double)random.nextInt(10) + 1;

                graph.addEdge(vertexA.name, vertexB.name, weight);
                String edge = vertexA.name + " " + vertexB.name  + " " + weight; 
                edges.add(edge);
                edgesGenerated++;
                System.out.println(edgesGenerated);
            }
        }
    }



    
    /**
     * The checkValidEdge function checks to see if the edge between two vertices is valid.
     * It does this by checking to see if the two vertices are equal, and then it checks
     * through all of a's adjacent edges to make sure that b isn't already connected. If 
     * either of these conditions are true, then we return false because there is no need 
     * for an edge between them. Otherwise, we return true because there should be an edge 
     * connecting them in our graph. This function will be used when adding edges from our file input.
     *
     * @param a Check if the vertex is already in the graph
     * @param b Check if the vertex is already in the graph
     *
     * @return True if the edge is valid, and false otherwise
     */
    Boolean checkValidEdge(Vertex a, Vertex b){
        int avgEdgeCount = numEdges / numVertices;
        //if (a.adj.size() > avgEdgeCount || b.adj.size() > avgEdgeCount)
         //   return false;

        if (Objects.equals(a.name, b.name))
            return false;

        for (Edge edge : graph.getVertex(a.name).adj) {
            if (Objects.equals(edge.dest.name, b.name))
                return false;
        }
        return true;
    }
    
    /**
     * The writeFile function takes the edges array and writes it to a file.
     * 
     */
    void writeFile(String ID){
        try {
            output = new File("data/data" + ID + ".txt");
            FileWriter fileWriter = new FileWriter(output);
            for (String edge : edges) {
                fileWriter.write(edge + "\n");                
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int getNumVertices() {
        return numVertices;
    }
}
   



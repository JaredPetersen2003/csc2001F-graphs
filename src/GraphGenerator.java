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
    Random random;
    int edgesGenerated;
    
    /**
     * The GraphGenerator generates a random graph with the specified number of vertices and edges.
     * The function takes two parameters: numVertices, which is an integer representing the number of vertices in the graph; 
     * and numEdges, which is an integer representing the number of edges in the graph. 
     
     *
     * @param int numVertices Determine the number of nodes in the graph
     * @param int numEdges Determine the number of edges to be generated
     */
    public GraphGenerator(int numVertices, int numEdges)
    {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        edgesGenerated = 0;
        graph = new Graph();
        edges = new ArrayList<>();
        random = new Random();
        File output = null;       

        // List of nodes
        ArrayList<Vertex> nodeList = new ArrayList<>(numVertices);

        // Add nodes to list
        for (int i = 0; i < numVertices; i++){
            String nodeName = "node" + String.format("%03d", i);
            nodeList.add(new Vertex(nodeName));    
        }

        // Make a connected graph
        Collections.shuffle(nodeList);
        for (int i = 0; i < nodeList.size() - 1; i++) {
            if (edgesGenerated == numEdges)
                break;
            Vertex vertexA = nodeList.get(i);
            Vertex vertexB = nodeList.get(i + 1);

            addEdge(vertexA, vertexB);

        }

        // Add random edges until the graph has the require number of edges
        while (edgesGenerated < this.numEdges && edgesGenerated < computeMaxEdges(numVertices)) {
            // Select two random vertices
            Vertex vertexA = graph.getVertex(nodeList.get(random.nextInt(numVertices)).name);
            Vertex vertexB = graph.getVertex(nodeList.get(random.nextInt(numVertices)).name);
            addEdge(vertexA, vertexB);
            
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
        if (Objects.equals(a.name, b.name))
            return false;

        for (Edge edge : graph.getVertex(a.name).adj) {
            if (Objects.equals(edge.dest.name, b.name))
                return false;
        }
        return true;
    }

        // Method to compute the maximum number of possible
    // edges for a given number of vertices
    static int computeMaxEdges(int numOfVertices)
    {
        // As it is an undirected graph
        // So, for a given number of vertices V
        // there can be at-most V*(V-1)/2 number of edges
        return numOfVertices * ((numOfVertices - 1) / 2);
    }
    
    
    /**
     * The addEdge function adds an edge between two vertices.
     * 
     *
     * @param Vertex vertexA Specify the first vertex of the edge
     * @param Vertex vertexB Specify the second vertex of the edge
     *
     * @return Void
     */
    void addEdge(Vertex vertexA, Vertex vertexB){
        if (checkValidEdge(vertexA, vertexB)){
            Double weight = (double)random.nextInt(10) + 1;

            graph.addEdge(vertexA.name, vertexB.name, weight);
            String edge = vertexA.name + " " + vertexB.name  + " " + weight; 
            edges.add(edge);
            edgesGenerated++;
        }
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
   



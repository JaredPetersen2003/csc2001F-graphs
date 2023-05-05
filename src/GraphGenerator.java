// Create a Random Graph Using
// Random Edge Generation in Java
import java.util.*;
import java.io.*;
import com.opencsv.*;

public class GraphGenerator {
    final int MIN = 5;
    final int MAX = 20;
    Graph graph;
    ArrayList<String> edges;
    File output;
    private int numEdges;
    private int numVertices;
 
    // Creating the constructor
    public GraphGenerator(int numVertices, int numEdges)
    {
        this.numVertices = numVertices;
        graph = new Graph();
        edges = new ArrayList<>();
        Random random = new Random();
        File output = null;       

        // Generate random number of nodes
        if (numEdges == -1)
            this.numEdges = random.nextInt(computeMaxEdges(numVertices)) + 1;
        else 
            this.numEdges = numEdges;


        // List of nodes
        ArrayList<Vertex> nodeList = new ArrayList<>(numVertices);

        // Add nodes to list
        for (int i = 0; i < numVertices; i++){
            String nodeName = "node" + String.format("%03d", i);
            nodeList.add(new Vertex(nodeName));    
        }

        int edgesGenerated = 0;
        //Generate random edges
        while ((edgesGenerated < this.numEdges) & (edgesGenerated < numVertices)) {
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
            }
        }
            
    
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
     * The checkValidEdge function checks to see if the edge between two vertices is valid.
     * It does this by checking to see if the two vertices are equal, and then it checks
     * through all of a's adjacent edges to make sure that b isn't already connected. If 
     * either of these conditions are true, then we return false because there is no need 
     * for an edge between them. Otherwise, we return true because there should be an edge 
     * connecting them in our graph. This function will be used when adding edges from our file input.
     *
     * @param Vertex a Check if the vertex is already in the graph
     * @param Vertex b Check if the edge is already in the graph
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
            // TODO: handle exception
        }       
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int getNumVertices() {
        return numVertices;
    }
}
   

	

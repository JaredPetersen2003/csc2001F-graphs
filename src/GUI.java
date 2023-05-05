import javax.swing.*;
import com.opencsv.CSVWriter;
import java.awt.*;
import java.io.*;

public class GUI {
    
    static JFrame frame;
    JMenuBar menuBar;
    JTextArea textArea;
    JButton singleTest;
    JButton multiTest;
    private final int[] numVertices = {30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
    
    
    /**
     * The GUI function creates a GUI for the user to interact with.
     * The GUI has two buttons, one for single tests and one for multiple tests.
     * When the user clicks on either button, it will run the corresponding function
     */
    public GUI(){
        frame = new JFrame("Graphs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Text Area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scroll);

        // Button panel
        JPanel panel = new JPanel();
        singleTest = new JButton("Single Test");
        multiTest = new JButton("Multiple Tests");
        
        singleTest.addActionListener(e -> singleTestOnClick());

        multiTest.addActionListener(e -> {
            try {
                multiTestOnClick();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
    
        // Add buttons
        panel.add(singleTest);
        panel.add(multiTest);
        
        frame.add(BorderLayout.SOUTH, panel);
        frame.setVisible(true);   

    }

    
    /**
     * The singleTestOnClick function is a function that runs the Dijkstra's algorithm on a randomly generated graph.
     * It then prints out the number of nodes and edges seen by the algorithm to the textArea in the GUI.
     *
     * @return The number of nodes and edges seen
     */
    void singleTestOnClick(){
        GraphGenerator g = new GraphGenerator(10, 20);
        g.writeFile("singleTest");
        executeDijkstra(g);
        textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\nPQ operations:" + g.graph.getOpcountPQ() + "\n");
    }

    
    /**
     * The multiTestOnClick function is used to run multiple tests on the Dijkstra's algorithm.
     * It takes in a number of vertices and edges, and runs the algorithm with those parameters.
     * The results are then written to a csv file for later analysis.
     *
     */
    void multiTestOnClick() throws IOException{
        //int numTests = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many tests to run?","Number of tests", JOptionPane.INFORMATION_MESSAGE));

        CSVWriter writer = new CSVWriter(new FileWriter("data/output.csv"));
        String[] head = {"V", "E", "Vcount", "Ecount", "PQCount", "ElogV", "Operations"};
        writer.writeNext(head);


        for (int i = 0; i < numVertices.length; i++) {
            for (int j = 1; j < 20; j++) {
                int edges = j * 15;
                GraphGenerator g = new GraphGenerator(numVertices[i], edges);
                String ID = String.valueOf(numVertices[i]) +"." + String.valueOf(edges); 
                g.writeFile(ID);
                executeDijkstra(g);

                textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\nPQ operations:" + g.graph.getOpcountPQ() + "\n");
                int totalOperations = g.graph.getNodesSeen() + g.graph.getopcountE() + g.graph.getOpcountPQ();
                Double eLogV = g.getNumEdges() * (Math.log(g.getNumVertices()) / Math.log(2));
                String[] testResult = {String.valueOf(g.getNumVertices()), String.valueOf(g.getNumEdges()), String.valueOf(g.graph.getNodesSeen()),
                        String.valueOf(g.graph.getopcountE()), String.valueOf(g.graph.getOpcountPQ()), eLogV.toString(), String.valueOf(totalOperations)};
                //textArea.append(testResult.toString());
                //Writing data to the csv file
                writer.writeNext(testResult);
            }
        }
        //Flushing data from writer to file
        writer.flush();
    }

    /**The executeDijkstra function finds the first edge in the list
     * and performs Dijkstra's algorithm using the first node in the edge as the start node
     *
     * @param g GraphGenerator supplies randomly generated graph
     */
    private void executeDijkstra(GraphGenerator g) {
        BufferedReader br1;
        String startNode = "";
        try {
            // find the first edge in randomly generated graph
            br1 = new BufferedReader(new FileReader(g.output));
            startNode = br1.readLine().split(" ")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.graph.dijkstra(startNode);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }
    
}

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
    private final int[] numEdges = {20, 35, 50, 65, 80};
    private final int[] numVertices = {10, 20, 30, 40, 50};
    
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
        GraphGenerator g = new GraphGenerator(10, 30);
        g.writeFile("singleTest");
        executeDijkstra(g);
        textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\n");
    }

    void multiTestOnClick() throws IOException{
        //int numTests = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many tests to run?","Number of tests", JOptionPane.INFORMATION_MESSAGE));

        CSVWriter writer = new CSVWriter(new FileWriter("data/output.csv"));
        String[] head = {"V", "E", "Vcount", "Ecount", "PQCount", "Operations"};
        writer.writeNext(head);
        GraphGenerator g;

        for (int i = 0; i < numVertices.length; i++) {
            for (int numEdge : numEdges) {
                g = new GraphGenerator(numVertices[i], numEdge);
                g.writeFile(String.valueOf(i));
                executeDijkstra(g);

                textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\nPQ operations:" + g.graph.getOpcountPQ() + "\n");
                int totalOperations = g.graph.getNodesSeen() + g.graph.getopcountE() + g.graph.getOpcountPQ();

                String[] testResult = {String.valueOf(g.getNumVertices()), String.valueOf(g.getNumEdges()), String.valueOf(g.graph.getNodesSeen()),
                        String.valueOf(g.graph.getopcountE()), String.valueOf(g.graph.getOpcountPQ()), String.valueOf(totalOperations)};
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

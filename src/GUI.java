import javax.swing.*;

import com.opencsv.CSVWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;


public class GUI {
    
    static JFrame frame;
    JMenuBar menuBar;
    JTextArea textArea;

    JButton singleTest;
    JButton multiTest;
    
    public GUI(){
        frame = new JFrame("Graphs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        
        //JMenu set up
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);


        frame.add(BorderLayout.NORTH, menuBar);

        // Text Area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.add(scroll);


        // Button panel
        JPanel panel = new JPanel();
        singleTest = new JButton("Single Test");
        multiTest = new JButton("Multiple Tests");
        
        singleTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                singleTestOnClick();}
        });

        multiTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    multiTestOnClick();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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
     * It then prints out the number of nodes and edges seen by the algorithm to the textArea in our GUI.
     
     *
     *
     * @return The number of nodes and edges seen
     */
    void singleTestOnClick(){
        GraphGenerator g = new GraphGenerator(10, 30);
        g.writeFile("singleTest");
        BufferedReader br1;
        String startNode = "";
        try {

            br1 = new BufferedReader(new FileReader(g.output));
            startNode = br1.readLine().split(" ")[0];
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.graph.dijkstra(startNode);

        textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\n");
    }

    void multiTestOnClick() throws IOException{
        int numTests = Integer.parseInt(JOptionPane.showInputDialog(frame, "How many tests to run?","Number of tests", JOptionPane.INFORMATION_MESSAGE));

        CSVWriter writer = new CSVWriter(new FileWriter("data/output.csv"));
        String[] head = {"V", "E", "Vcount", "Ecount", "PQCount"};
        writer.writeNext(head);
        
        for (int i = 0; i < numTests; i++) {
            
            GraphGenerator g = new GraphGenerator((i+ 1) * 10, (i+ 1) * 50);
            g.writeFile(String.valueOf(i));
            
            BufferedReader br1;
            String startNode = "";
            try {
                br1 = new BufferedReader(new FileReader(g.output));
                startNode = br1.readLine().split(" ")[0];
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            g.graph.dijkstra(startNode);

            textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getopcountE() + "\nPQ operations:" + g.graph.getOpcountPQ()+ "\n");
            String[] testResult = {String.valueOf(g.getNumVertices()), String.valueOf(g.getNumEdges()), String.valueOf( g.graph.getNodesSeen()), 
                String.valueOf(g.graph.getopcountE()), String.valueOf(g.graph.getOpcountPQ())};
                System.out.println(testResult[1]);
            //Writing data to the csv file
            writer.writeNext(testResult);
    
    
    
        }
        //Flushing data from writer to file
        writer.flush();
    }
 
    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    

    
}

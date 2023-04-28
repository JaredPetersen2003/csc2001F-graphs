import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.io.*;
import java.text.Format;

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
        JMenuItem loadUsers = new JMenuItem("Load Dataset");

        // Manually load a file
        loadUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                loadDataSet();
            }
        });
        fileMenu.add(loadUsers);

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
        
        GraphGenerator g = new GraphGenerator(10);
        
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

        textArea.append("Nodes seen: " + g.graph.getNodesSeen() + "\nEdges seen: " + g.graph.getEdgesSeen());
    }
 

    private static void loadDataSet(){
        try {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String line;
                
                while ((line = br.readLine()) != null){
    
                   //TODO: load dataset
                }
                br.close();
                JOptionPane.showMessageDialog(frame, "File loaded successfully");
            }
            else
                JOptionPane.showMessageDialog(frame, "Cancelled");

            
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }

    

    
}

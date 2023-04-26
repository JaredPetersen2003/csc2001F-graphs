import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GUI {
    
    static JFrame frame;
    
    public GUI(){
        frame = new JFrame("Graphs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        
        //JMenu set up
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem loadUsers = new JMenuItem("Load Dataset");

        // Manually load a file
        loadUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        loadDataSet(fileChooser.getSelectedFile());
                        JOptionPane.showMessageDialog(frame, "File loaded successfully");
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "Cancelled");

                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        fileMenu.add(loadUsers);

        frame.add(BorderLayout.NORTH, menuBar);

        // Text Area
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.add(scroll);


        // Button panel
        JPanel panel = new JPanel();
        JButton singleTest = new JButton("Single Test");
        JButton multiTest = new JButton("Multiple Tests");
      

    
        // Add buttons
        panel.add(singleTest);
        panel.add(multiTest);
        
        frame.add(BorderLayout.SOUTH, panel);

        frame.setVisible(true);   

    }
 

    private static void loadDataSet(File file) throws IOException{
        // Load
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = br.readLine()) != null){

               //TODO: load dataset
            }
            br.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: Dataset not found");
        }

    }

    
}

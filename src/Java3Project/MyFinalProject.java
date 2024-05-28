package Java3Project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Java3Project.Resource.Matricielle;

public class MyFinalProject {
    private JFrame frame;
    private Resource resource;
    private Matricielle matricielle;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFinalProject window = new MyFinalProject();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MyFinalProject() {
        resource = new Resource();
        matricielle = resource.new Matricielle();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("OMEGA Company");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu menuFile = new JMenu("Home");
        JMenu menuProject = new JMenu("Projects");
        JMenu menuTask = new JMenu("Tasks");
        JMenu menuProcess = new JMenu("Processes");
        JMenu menuResource = new JMenu("Resources");
        JMenuItem ViewResource = new JMenuItem("Matiere Resource");
        JMenuItem addResource = new JMenuItem("Add Resource");
        JMenuItem getResource = new JMenuItem("View Resources");
        menuResource.add(ViewResource);
        menuResource.add(addResource);
        menuResource.add(getResource);

        // Add menus to the menu bar
        menuBar.add(menuFile);
        menuBar.add(menuProject);
        menuBar.add(menuTask);
        menuBar.add(menuProcess);
        menuBar.add(menuResource);

        ViewResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame
                JFrame newFrame = new JFrame("Matiere Resource");

                newFrame.setSize(300, 200);

                // Add a label to the new frame
                JLabel newLabel = new JLabel("Thank you for creating a new one", SwingConstants.CENTER);
                newFrame.getContentPane().add(newLabel);

                // Make the new frame visible
                newFrame.setVisible(true);
            }
        });

        addResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame
                JFrame addFrame = new JFrame("Add Matricielle Resource");
                addFrame.setSize(400, 300);

                // Create panel for input fields
                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel lblIdentifier = new JLabel("Identifier:");
                lblIdentifier.setBounds(10, 10, 80, 25);
                panel.add(lblIdentifier);

                JTextField txtIdentifier = new JTextField();
                txtIdentifier.setBounds(100, 10, 160, 25);
                panel.add(txtIdentifier);

                JLabel lblName = new JLabel("Name:");
                lblName.setBounds(10, 40, 80, 25);
                panel.add(lblName);

                JTextField txtName = new JTextField();
                txtName.setBounds(100, 40, 160, 25);
                panel.add(txtName);

                JLabel lblCostPerUnit = new JLabel("Cost per Unit:");
                lblCostPerUnit.setBounds(10, 70, 80, 25);
                panel.add(lblCostPerUnit);

                JTextField txtCostPerUnit = new JTextField();
                txtCostPerUnit.setBounds(100, 70, 160, 25);
                panel.add(txtCostPerUnit);

                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(100, 100, 80, 25);
                panel.add(btnAdd);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int identifier = Integer.parseInt(txtIdentifier.getText());
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        matricielle.addMatriciele(identifier, name, costPerUnit);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });

        getResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new frame to display resources
                JFrame viewFrame = new JFrame("View Matricielle Resources");
                viewFrame.setSize(500, 400);

                // Create a table to display the resources
                String[] columnNames = {"Identifier", "Name", "Cost per Unit"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                // Populate the table with data
                List<String> entries = getMatriciele();
                for (String entry : entries) {
                	
                    entry = entry.substring(1, entry.length() - 1);
                    
                    // Remove curly braces
                    String[] data = entry.split(",");
                    tableModel.addRow(data);
                    System.out.print(data);        
                    for (int i=0 ; i<data.length-1;i++) {
                    	System.out.print(data[i]);if (data[i]=="}") {
                			String thebug=data[i];
                			thebug=" ";
                		}
                    }
                    }

                // Add the table to a scroll pane
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // Make the new frame visible
                viewFrame.setVisible(true);
            }
        });

        // Set the menu bar to the frame
        frame.setJMenuBar(menuBar);
    }

    public List<String> getMatriciele() {
        List<String> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Resources.txt"))) {
            String line;
            StringBuilder entry = new StringBuilder();
            boolean insideEntry = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if ((line.startsWith("{") || line.startsWith(",")) &&  !insideEntry) {
                	for (int i=0 ; i<entry.length()-1;i++) {
                		if (entry.charAt(i)=='}') {
                			char thebug=entry.charAt(i);
                			thebug=' ';
                		}
                	};
                    entry.append(line);
                    
                    insideEntry = true;
                  
                } else if (insideEntry) {
                    entry.append(line);
                    if (line.endsWith("}")) {
                        entries.add(entry.toString());
                        entry.setLength(0);
                        insideEntry = false;
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
}

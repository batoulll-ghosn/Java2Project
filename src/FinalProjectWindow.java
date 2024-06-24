import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.*;
public class FinalProjectWindow {
    private JFrame frame;
    private Process process;
    private Resource.Matrielle matricielle;
    private Resource.HumanRes human;
    private Resource.Logistics log;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	FinalProjectWindow window = new FinalProjectWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FinalProjectWindow() {
    	//////the reference points to null
        matricielle = new Resource.Matrielle("", 0.0);
        log = new Resource.Logistics(" ",0.0);
        human=new Resource.HumanRes("",0.0);
        initialize();
    }
    private void initialize() {
        frame= new JFrame("OMEGA Company");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Home");
        JMenu menuProject = new JMenu("Projects");
        JMenu menuTask = new JMenu("Tasks");
        JMenu menuProcess = new JMenu("Processes");
        JMenu menuResource = new JMenu("Resources");

        JMenuItem addlogistic = new JMenuItem("Add Logistic Resource");
        JMenuItem addResource = new JMenuItem("Add Matiere Resource");
        JMenuItem addHuman = new JMenuItem("Add Human Resource");
        JMenuItem getHuman = new JMenuItem("View Human Resources");
        JMenuItem getResource = new JMenuItem("View Matiere Resources");
        JMenuItem getlogistic = new JMenuItem("View Logistics Resources");
        menuResource.add(addResource);
        menuResource.add(addHuman);
        menuResource.add(addlogistic);
        menuResource.add(getResource);
        menuResource.add(getHuman);
        menuResource.add(getlogistic);
        JMenuItem getProcess = new JMenuItem("View Process");
        JMenuItem addProcess = new JMenuItem("Add Process");
        menuProcess.add(addProcess);
        menuProcess.add(getProcess);
        menuBar.add(menuFile);
        menuBar.add(menuProject);
        menuBar.add(menuTask);
        menuBar.add(menuProcess);
        menuBar.add(menuResource);

        frame.setJMenuBar(menuBar);
        //Human Resources 
        
        addHuman.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Human Resources");
                addFrame.setSize(400, 300);
                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel lblName = new JLabel("Name:");
                lblName.setBounds(10, 40, 80, 25);
                panel.add(lblName);

                JTextField txtName = new JTextField();
                txtName.setBounds(100, 40, 160, 25);
                panel.add(txtName);

                JLabel lblCostPerUnit = new JLabel("Cost per Hour:");
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
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        human.addHumanR(name, costPerUnit);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });
        getHuman.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Human Resources");
                viewFrame.setSize(800, 600);

                String[] columnNames = {"Name", "Cost"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                Set<String> entries = human.getHumans();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    tableModel.addRow(new Object[]{data[1], data[2]});
                }

                TableColumnModel columnModel = table.getColumnModel();
              
               
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                viewFrame.setVisible(true);
            }
        });
        //Logistics 
        //1-get Logistics
              getlogistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Logistics Resources");
                viewFrame.setSize(800, 600);

                String[] columnNames = {"Name", "Cost"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                Set<String> entries = log.getLogistics();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    tableModel.addRow(new Object[]{data[1], data[2]});
                }

                TableColumnModel columnModel = table.getColumnModel();
                
               
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                viewFrame.setVisible(true);
            }
        });
              //2-Add Logistics
        addlogistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Logistics Resource");
                addFrame.setSize(400, 300);
                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel lblName = new JLabel("Name:");
                lblName.setBounds(10, 40, 80, 25);
                panel.add(lblName);

                JTextField txtName = new JTextField();
                txtName.setBounds(100, 40, 160, 25);
                panel.add(txtName);

                JLabel lblCostPerUnit = new JLabel("Cost:");
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
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        log.addLog(name, costPerUnit);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Resource Addition (Matiere Resource)    
        addResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Matricielle Resource");
                addFrame.setSize(400, 300);
                JPanel panel = new JPanel();
                panel.setLayout(null);

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
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        matricielle.addMat(name, costPerUnit);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });
        getResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Matricielle Resources");
                viewFrame.setSize(800, 600);

                String[] columnNames = {"Name", "Cost per Unit"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                  
                };
                JTable table = new JTable(tableModel);

                Set<String> entries = matricielle.getMat();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    tableModel.addRow(new Object[]{data[1], data[2]});
                }

                TableColumnModel columnModel = table.getColumnModel();
              
                               JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                viewFrame.setVisible(true);
            }
        });

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
     
    //////////////////////////////////////////////////////////////////////////////////////
    ///Process Addition 
        addProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Process");
                addFrame.setSize(1000, 900);
                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel lblName = new JLabel("Name:");
                lblName.setBounds(10, 10, 80, 25);
                panel.add(lblName);

                JTextField txtName = new JTextField();
                txtName.setBounds(100, 10, 160, 25);
                panel.add(txtName);

                JLabel lblState = new JLabel("State:");
                lblState.setBounds(10, 50, 80, 25);
                panel.add(lblState);

                JTextField txtState = new JTextField();
                txtState.setBounds(100, 50, 160, 25);
                panel.add(txtState);

                JLabel lblDuration = new JLabel("Duration:");
                lblDuration.setBounds(10, 90, 80, 25);
                panel.add(lblDuration);

                JTextField txtDuration = new JTextField();
                txtDuration.setBounds(100, 90, 160, 25);
                panel.add(txtDuration);
                JLabel lblMtResources = new JLabel("Resources Matricielle:");
                lblMtResources.setBounds(10, 140, 140, 40);
                panel.add(lblMtResources);

                List<JCheckBox> resourceCheckBoxes = new ArrayList<>();
                Set<String> resourceEntries = matricielle.getMat();
                int yPositionn = 180; // Starting Y position for checkboxes
                int yOffsettt = 40; // Vertical offset between checkboxes
                HashSet<TreeSet<String>> mtSet = new HashSet<TreeSet<String>>();
                TreeSet<String> inmtSet = new TreeSet<>();
                for (String entry : resourceEntries) {
                    String[] data = entry.split(",");
                    JCheckBox checkBox = new JCheckBox(data[1] + " (Cost per unit: " + data[2] + ")");
                    JTextField quantity = new JTextField();
                    quantity.setBounds(210, yPositionn, 60, 25);
                    panel.add(quantity);
                    checkBox.setBounds(10, yPositionn, 200, 25);
                    panel.add(checkBox);
                    resourceCheckBoxes.add(checkBox);
                    yPositionn += yOffsettt; // Update Y position for the next checkbox

                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	String d=quantity.getText();
                            try {
                                if (checkBox.isSelected() && d!=" ") {
                                  inmtSet.add(data[1]);  
                                 inmtSet.add(d);
                                 mtSet.add(inmtSet);

                                 System.out.println("ADDED! " + inmtSet);
                                 System.out.println("ADDED! " + mtSet);
                               
                                 int size =inmtSet.size();
                                 if(size == 2) {
                                	 TreeSet<String> inmtSet = new TreeSet<>();
                                 }
                                } else {
                                	inmtSet.remove(data[1]);  
                                    inmtSet.remove(d);
                                    mtSet.remove(inmtSet);
                                    System.out.println("REMOVED!");
                                }
                            }
                            
                            catch (NumberFormatException ex) {
                                System.out.print("Invalid quantity entered. Please enter a valid number.");
                            }catch (ClassCastException cce) {}
                            
                        }
                    });
                }

                /////////////////////////////////
                JLabel lblhrResources = new JLabel("Resources Human:");
                lblhrResources.setBounds(300, 140, 140, 40);
                panel.add(lblhrResources);

                List<JCheckBox> resourcehrCheckBoxes = new ArrayList<>();
                Set<String> resourcehrEntries = human.getHumans();

                int yPosition = 180; // Starting Y position for checkboxes
                int yOffset = 40; // Vertical offset between checkboxes
                HashSet<TreeSet<String>> humans = new HashSet<TreeSet<String>>();
                TreeSet<String> humSet = new TreeSet<>();
                JTextField rt = new JTextField();
                rt.setBounds(210, yPosition, 60, 25);
                panel.add(rt);
                for (String entry : resourcehrEntries) {
                    String[] data = entry.split(",");
                    JCheckBox checkBox = new JCheckBox(data[1] + " (Rate: " + data[2] + ")");
                    JTextField quantity = new JTextField();
                    quantity.setBounds(480, yPosition, 60, 25);
                    panel.add(quantity);
                    checkBox.setBounds(310, yPosition, 130, 25);
                    panel.add(checkBox);
                    resourcehrCheckBoxes.add(checkBox);
                    yPosition += yOffset; // Update Y position for the next checkbox

                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	String d=quantity.getText();
                            try {
                                if (checkBox.isSelected() && d!=" ") {
                                	humSet.add(data[1]);  
                                  humSet.add(d);
                                 humans.add(humSet);

                                 System.out.println("ADDED! " + humSet);
                                 System.out.println("ADDED! " + humans);
                               
                                 int size =humSet.size();
                                 if(size == 2) {
                                	 TreeSet<String> humSet = new TreeSet<>();
                                 }
                                } else {
                                	humSet.remove(data[1]);  
                                	humSet.remove(d);
                                	humans.remove(humSet);
                                    System.out.println("REMOVED!");
                                }
                            }
                            
                            catch (NumberFormatException ex) {
                                System.out.print("Invalid quantity entered. Please enter a valid number.");
                            }catch (ClassCastException cce) {}
                            
                        }
                    });
                }

                //////////////////////////////////////
               JLabel lbllgResources = new JLabel("Resources Logistics:");
               lbllgResources.setBounds(600, 140, 140, 40);
                panel.add(lbllgResources);

                List<JCheckBox> resourcelgCheckBoxes = new ArrayList<>();
                Set<String> resourcelgEntries = log.getLogistics();
                int yPositionlg = 180; // Starting Y position for checkboxes
                int yOffsetlg = 40; // Vertical offset between checkboxes
                TreeSet<String> logistics = new TreeSet<>();

                for (String entry : resourcelgEntries) {
                    String[] data = entry.split(",");
                    JCheckBox checkBox = new JCheckBox(data[1] + " (Cost per unit: " + data[2] + ")");
                    checkBox.setBounds(600, yPositionlg, 300, 35);
                    panel.add(checkBox);
                    resourcelgCheckBoxes.add(checkBox);
                    yPositionlg += yOffsetlg; // Update Y position for the next checkbox

                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (checkBox.isSelected()) {
                                logistics.add(data[1]);
                                System.out.print("ADDED!");
                            } else {
                                logistics.remove(data[1]);
                                System.out.print("REMOVED!");
                            }
                        }
                    });
                }

             
                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(450, 650, 80, 25);
                panel.add(btnAdd);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = txtName.getText();
                        String state = txtState.getText();
                        int duration = Integer.parseInt(txtDuration.getText());
                        TreeMap <String,TreeSet<Resource>> rsc=new TreeMap<String,TreeSet<Resource>>();
                        double cost=0.0;
                        Process process = new Process(name,rsc,cost,state,duration);
                        process.addProcess(process);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });

        getProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Processes");
                viewFrame.setSize(800, 600);

                Set<Process> processes = Process.getProcess();

                String[] columnNames ={"Process Name","State", "Duration"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Process process : processes) {
                    StringBuilder resourcesStr = new StringBuilder();
                  
                    Object[] rowData = {process.getName(),process.getState(), process.getDuration()};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.add(scrollPane, BorderLayout.CENTER);

                viewFrame.setVisible(true);
            }
        });
    

}
    
}
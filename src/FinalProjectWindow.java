import java.awt.BorderLayout;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.util.*;
public class FinalProjectWindow extends JFrame {
    private JFrame frame;
    private Process process;
    private Project project;
    private Tasks task;
    private double sumlog;
    private double sumTask;
    private double sumMat;
    private double sumHum;
    private Resource r;
    private Process p;
    private Resource.Matrielle matricielle;
    private Resource.HumanRes human;
    private Resource.Logistics log;
    double CostPr=0.0;
    long durationPr=12345678910L;
    public static void main(String[] args) {
            try {
            	FinalProjectWindow window = new FinalProjectWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public FinalProjectWindow() {
    	//////the reference points to null
    	// double sumlog = 0.0;
    	 r= new Resource("",0.0);
        matricielle = new Resource.Matrielle("", 0.0,1);
        log = new Resource.Logistics(" ",0.0);
        human=new Resource.HumanRes("",0.0,"","",1,0);
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
        JMenu menuEx = new JMenu("Execute");
        JMenuItem addlogistic = new JMenuItem("Add Logistic Resource");
        JMenuItem addResource = new JMenuItem("Add Matiere Resource");
        JMenuItem addHuman = new JMenuItem("Add Human Resource");
        JMenuItem getHuman = new JMenuItem("View Human Resources");
        JMenuItem getResource = new JMenuItem("View Matiere Resources");
        JMenuItem getlogistic = new JMenuItem("View Logistics Resources");
 
        menuBar.add(menuFile);
        menuBar.add(menuProject);
        menuBar.add(menuTask);
        menuBar.add(menuProcess);
        menuBar.add(menuResource);
        menuBar.add(menuEx);
   
        menuResource.add(addResource);
        menuResource.add(addHuman);
        menuResource.add(addlogistic);
        menuResource.add(getResource);
        menuResource.add(getHuman);
        menuResource.add(getlogistic);
        JMenuItem getProcess = new JMenuItem("View Processes");
        JMenuItem addProcess = new JMenuItem("Add Process");
        menuProcess.add(addProcess);
        menuProcess.add(getProcess);
        JMenuItem getTasks = new JMenuItem("View Tasks");
        JMenuItem addTasks = new JMenuItem("Add Task");
        menuTask.add(addTasks);
        menuTask.add(getTasks);
        JMenuItem getProject = new JMenuItem("View Projects");
        JMenuItem addProject = new JMenuItem("Add Project");
        menuProject.add(addProject);
        menuProject.add(getProject);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().setLayout(new FlowLayout());
        TableRowSorter<DefaultTableModel> sorter;
        JMenuItem executeProjectItem = new JMenuItem("Execute Project");

        menuEx.add(executeProjectItem);
        menuBar.add(menuEx);
        frame.setJMenuBar(menuBar);
       

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////// ComboBoxes for the main frame 
        Set<Project> projectsSET = Project.getProject();
        
        DefaultComboBoxModel<Project> projectComboBoxModel = new DefaultComboBoxModel<>();
        projectComboBoxModel.addElement(null);
        for (Project project : projectsSET) {
            projectComboBoxModel.addElement(project);
        }
        JComboBox<Project> projectComboBox = new JComboBox<>(projectComboBoxModel);

        // ComboBox of tasks
        DefaultComboBoxModel<Tasks> tasksComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<Tasks> tasksComboBox = new JComboBox<>(tasksComboBoxModel);
        executeProjectItem.addActionListener(e -> {
      
            Project selectedProject = (Project) projectComboBox.getSelectedItem();
		       if(selectedProject==null){ 
		    	   System.out.print("You need to select a project to execute it");
			   }
            else {
            JFrame taskFrame = new JFrame("Executing Project");
            taskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            taskFrame.setSize(400, 300);

            JPanel taskPanel = new JPanel();
            taskPanel.setLayout((LayoutManager) new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
            taskFrame.setContentPane(taskPanel);

            JLabel executingLabel = new JLabel("Executing...");
            taskPanel.add(executingLabel);

         
            for (Tasks task : selectedProject.getTasks()) {
                JLabel taskLabel = new JLabel(task.toString());
                taskPanel.add(taskLabel);

                for (Process process : task.getProcesses()) {
                    JLabel processLabel = new JLabel("    " + process.toString());
                    taskPanel.add(processLabel);
                }

               
            }

            JLabel totalCostLabel = new JLabel("Total Cost: " + selectedProject.getCost());
            taskPanel.add(totalCostLabel);

            taskFrame.setVisible(true);
          }
        });

        DefaultComboBoxModel<Process> processComboBoxModel = new DefaultComboBoxModel<>();
        JComboBox<Process> processComboBox = new JComboBox<>(processComboBoxModel);
        // Add an ActionListener to the projectComboBox
        
        
        projectComboBox.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
                Project selectedProject = (Project) projectComboBox.getSelectedItem();
                CostPr=selectedProject.getCost();
                JLabel lblName = new JLabel("Cost Of Project : "+CostPr);
                lblName.setBounds(10, 375, 200, 25);
                frame.getContentPane().add(lblName, BorderLayout.SOUTH);
                tasksComboBoxModel.removeAllElements();
                tasksComboBoxModel.addElement(null); 
                if (selectedProject != null) {
                    for (Tasks task : selectedProject.getTasks()) { 
                        tasksComboBoxModel.addElement(task);
                    }
                }
                tasksComboBox.setSelectedItem(null); 
            }
        });

        tasksComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tasks selectedTask = (Tasks) tasksComboBox.getSelectedItem();
                processComboBoxModel.removeAllElements();
                processComboBoxModel.addElement(null); // Add null as the first element
                if (selectedTask != null) {
                    for (Process process : selectedTask.getProcesses()) { // Assuming getProcesses() returns a Set<Process>
                        processComboBoxModel.addElement(process);
                    }
                }
                processComboBox.setSelectedItem(null); // Set selected item to null
            }
        });
        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Type of Resource", "Resource Taken"}, 0);
        table.setModel(tableModel);

        processComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (processComboBox.getSelectedItem() != null) {
                    Process cmbProcess = (Process) processComboBox.getSelectedItem();
                    TreeMap<String, TreeSet<?>> rcsp = cmbProcess.getResources();
                    tableModel.setRowCount(0);  // Clear previous rows

                    for (String key : rcsp.keySet()) {
                        Set<?> values = rcsp.get(key);
                        
                        // Iterate through the set to get the names of the resources
                        StringBuilder names = new StringBuilder();
                        StringBuilder costs = new StringBuilder();
                        for (Object value : values) {
                            if (value instanceof Resource) {
                                Resource resource = (Resource) value;
                                names.append(resource.getName()).append(", ");
                                costs.append(resource.getUnitCost()).append(", ");
                            }
                        }
                        
                        // Remove the trailing comma and space
                        if (names.length() > 0) {
                            names.setLength(names.length() - 2);
                        }
                        String g=names.toString()+ " " +costs.toString();
                        tableModel.addRow(new Object[]{key, g});
                    }

                }
            }
        });
      
        frame.getContentPane().add(projectComboBox);
        frame.getContentPane().add(tasksComboBox);
        frame.getContentPane().add(processComboBox);
        JScrollPane scrollPane_1 = new JScrollPane(table);
        frame.getContentPane().add(scrollPane_1, "Center");
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

                JLabel sp = new JLabel("Speciality:");
                sp.setBounds(10, 100, 80, 25);
                panel.add(sp);

                JTextField sptxt = new JTextField();
                sptxt.setBounds(100, 100, 160, 25);
                panel.add(sptxt);

                JLabel fn = new JLabel("Function:");
                fn.setBounds(10, 130, 80, 25);
                panel.add(fn);

                JTextField fntxt = new JTextField();
                fntxt.setBounds(100, 130, 160, 25);
                panel.add(fntxt);
                JLabel wh = new JLabel("Working Hours:");
                wh.setBounds(10, 160, 80, 25);
                panel.add(wh);

                JTextField whtxt = new JTextField();
                whtxt.setBounds(100, 160, 160, 25);
                panel.add(whtxt);
                JLabel lblNote = new JLabel("NOTE: ");
                lblNote.setBounds(300, 100, 250, 25);
                panel.add(lblNote);
                lblNote.setText("The Working Hours Of Full-Time Employee is 180hrs");
                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(100, 230, 80, 25);
                panel.add(btnAdd);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        double wh = Double.parseDouble(whtxt.getText());
                        String function = fntxt.getText();
                        String speciality = sptxt.getText();

                        if (wh > 180) {
                            JOptionPane.showMessageDialog(addFrame, "Working hours cannot be greater than 180.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            human.addHumanR(name, costPerUnit, speciality, function, wh);
                            addFrame.dispose();
                        }
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
                TableRowSorter<DefaultTableModel> sorter;
        		
        		JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);

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
                scrollPane.setBounds(10, 103, 712, 260);
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });
                viewFrame.getContentPane().add(scrollPane);
                viewFrame.setVisible(true);
            }
        });
        //Logistics 
        //1-get Logistics
        getlogistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Logistics Resources");
                viewFrame.setSize(800, 600);
                TableRowSorter<DefaultTableModel> sorter;
                
                JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);

                String[] columnNames = {"Name", "Cost"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                Set<String> entries = log.getLogistics();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    if (data.length >= 3) {
                        tableModel.addRow(new Object[]{data[1], data[2]});
                    } else {
                        System.err.println("Invalid entry: " + entry);
                    }
                }

                JScrollPane scrollPane = new JScrollPane(table);
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });
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
                JLabel lblStockPerMonth = new JLabel("Stock Per Month:");
                lblStockPerMonth.setBounds(10, 130, 80, 25);
                panel.add(lblStockPerMonth);

                JTextField txtStockPerMonth = new JTextField();
                txtStockPerMonth.setBounds(100, 130, 160, 25);
                panel.add(txtStockPerMonth);
                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(100, 100, 80, 25);
                panel.add(btnAdd);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = txtName.getText();
                        double costPerUnit = Double.parseDouble(txtCostPerUnit.getText());
                        int stock=Integer.parseInt(txtStockPerMonth.getText());
                        matricielle.addMat(name, costPerUnit,stock);
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
                TableRowSorter<DefaultTableModel> sorter;
                
                JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);

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
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });
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
                lblState.setBounds(300, 10, 80, 25);
                panel.add(lblState);

                JTextField txtState = new JTextField();
                txtState.setBounds(390, 10, 160, 25);
                panel.add(txtState);

                JLabel lblSTRdate = new JLabel("Start Date:");
                lblSTRdate.setBounds(10, 50, 80, 25);
                panel.add(lblSTRdate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormatter dateFormatter = new DateFormatter(dateFormat);

                // Get the current date
                Date currentDate = new Date();

                JFormattedTextField txtSTRdate = new JFormattedTextField(dateFormatter);
                txtSTRdate.setValue(currentDate); // Set the Date object directly
                txtSTRdate.setBounds(100, 50, 160, 25);
                panel.add(txtSTRdate);

                JLabel lblENDdate = new JLabel("End Date:");
                lblENDdate.setBounds(300, 50, 80, 25);
                panel.add(lblENDdate);

                JFormattedTextField txtENDdate = new JFormattedTextField(dateFormatter);
                txtENDdate.setBounds(390, 50, 160, 25);
                panel.add(txtENDdate);

                JButton btnCalculate = new JButton("Calculate Duration");
                btnCalculate.setBounds(10, 100, 250, 25);
                panel.add(btnCalculate);

                JLabel lblDuration = new JLabel("Duration: ");
                lblDuration.setBounds(300, 100, 250, 25);
                panel.add(lblDuration);

                btnCalculate.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Date startDate = dateFormat.parse(txtSTRdate.getText());
                            Date endDate = dateFormat.parse(txtENDdate.getText());

                            if (startDate != null && endDate != null && endDate.compareTo(startDate) > 0) {
                                durationPr = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
                                lblDuration.setText("Duration: " + durationPr + " days");
                            } else {
                                lblDuration.setText("Please enter valid dates.");
                            }
                        } catch (ParseException ex) {
                            lblDuration.setText("Please enter valid dates.");
                        }
                    }
                });
                
                JLabel lblMtResources = new JLabel("Resources Matricielle:");
                lblMtResources.setBounds(10, 140, 140, 40);
                panel.add(lblMtResources);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////
                ///Declare the map 
                TreeMap<String, TreeSet<?>> resourceMap = new TreeMap<>();
               
                List<JCheckBox> resourceCheckBoxes = new ArrayList<>();
                Set<String> resourceEntries = matricielle.getMat();
                int yPositionn = 180; // Starting Y position for checkboxes
                int yOffsettt = 40; // Vertical offset between checkboxes
               
                TreeSet<Resource.Matrielle> inmtSet = new TreeSet<>();
                
                for (String entry : resourceEntries) {
                    String[] data = entry.split(",");
                    JCheckBox checkBox = new JCheckBox(data[1] + " (unit:" + data[2] + "$)");
                    JTextField quantity = new JTextField();
                    quantity.setBounds(210, yPositionn, 60, 25);
                    panel.add(quantity);
                    checkBox.setBounds(10, yPositionn, 200, 25);
                    panel.add(checkBox);
                    resourceCheckBoxes.add(checkBox);
                    yPositionn += yOffsettt; // Update Y position for the next checkbox
                    JLabel lblRateST = new JLabel("");
                    lblRateST.setBounds(10, yPositionn+70, 250, 25);
                    panel.add(lblRateST);
                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	String d=quantity.getText();
                        	
                        	int Quantity=Integer.parseInt(d);
                        	double cost=Double.parseDouble(data[2]);
                        	double stock=Double.parseDouble(data[3]);
                        	 double rate=((durationPr)/30)*stock;
                        	 int rateSt=(int)rate;
                            try {
                            	 if (d.trim().isEmpty()) {
                                     System.out.println("Quantity can't be empty");
                                     checkBox.setSelected(false);
                                     return;
                                 }
                                if (checkBox.isSelected() && d!=" ") {
                                	
                                 if (Quantity > rate) {
                                     System.out.println("Error: Working hours exceeded.");
                                     checkBox.setSelected(false);
                                     lblRateST.setText("Stock of "+data[1]+" is" + rateSt+" units");
                                 } else {
                                	 matricielle = new Resource.Matrielle(data[1], cost,Quantity);
                                     inmtSet.add(matricielle); 
                                    System.out.println("ADDED! " + inmtSet);
                                 }
                                } else {
                                	inmtSet.remove(matricielle);  
                                    System.out.println("REMOVED!");
                                }
                            }
                            catch (NumberFormatException ex) {
                                System.out.print("Invalid quantity entered. Please enter a valid number.");
                            }
                            catch (ClassCastException cce) {}
                            
                        }
                    }); resourceMap.put("ResourceMat", inmtSet);
                }

                /////////////////////////////////
                JLabel lblhrResources = new JLabel("Resources Human:");
                lblhrResources.setBounds(300, 140, 140, 40);
                panel.add(lblhrResources);

                List<JCheckBox> resourcehrCheckBoxes = new ArrayList<>();
                Set<String> resourcehrEntries = human.getHumans();

                int yPosition = 180; // Starting Y position for checkboxes
                int yOffset = 40; // Vertical offset between checkboxes

                TreeSet<Resource.HumanRes> humSet = new TreeSet<>();
                JTextField rt = new JTextField();
                rt.setBounds(210, yPosition, 60, 25);
                panel.add(rt);
                int duration = (int) durationPr;
                for (String entry : resourcehrEntries) {
                    String[] data = entry.split(",");
                    if (data.length < 5) {
                        System.out.println("Invalid data format: " + entry);
                        continue;
                    }
                    JCheckBox checkBox = new JCheckBox(data[1]);
                    JTextField quantity = new JTextField();
                    quantity.setBounds(480, yPosition, 60, 25);
                    panel.add(quantity);
                    checkBox.setBounds(310, yPosition, 130, 25);
                    panel.add(checkBox);
                    resourcehrCheckBoxes.add(checkBox);
                    yPosition += yOffset; // Update Y position for the next checkbox
                    JLabel lblRate = new JLabel("");
                    lblRate.setBounds(480, yPosition+30, 250, 25);
                    panel.add(lblRate);
                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String quantityText = quantity.getText();
                            try {
                                if (quantityText.trim().isEmpty()) {
                                    System.out.println("Hours cannot be empty.");
                                    checkBox.setSelected(false);
                                    return;
                                }
                                double cost = Double.parseDouble(data[2]);
                                int q = Integer.parseInt(quantityText);
                                double wh = Double.parseDouble(data[5]);
                               //nt identifier = Integer.parseInt(data[0]);
                                double rate=((durationPr*(wh/24))/30)*24;
                             // System.out.println("Identifier: " + identifier);
                                int ratew=(int)rate;
                                System.out.println(rate);
                                if (checkBox.isSelected()) {
                                    if (q > rate) {
                                        System.out.println("Error: Working hours exceeded.");
                                        checkBox.setSelected(false);
                                        lblRate.setText("Rate of"+data[1]+" is" + ratew+"hrs");
                                    } else {
                                        Resource.HumanRes newHuman = new Resource.HumanRes(data[1], cost, data[3], data[4], q, wh - q);
                                        humSet.add(newHuman);
                                        System.out.println("ADDED! " + newHuman);
                                    }
                                } else {
                                    Resource.HumanRes existingHuman = findHumanByName(humSet, data[1]);
                                    if (existingHuman != null) {
                                        humSet.remove(existingHuman);
                                        System.out.println("REMOVED!");
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Invalid quantity entered. Please enter a valid number.");
                                checkBox.setSelected(false);
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                System.out.println("Data array does not have the required elements.");
                                checkBox.setSelected(false);
                            } catch (Exception ex) {
                                System.out.println("An unexpected error occurred: " + ex.getMessage());
                                checkBox.setSelected(false);
                            }
                        }
                        private Resource.HumanRes findHumanByName(Set<Resource.HumanRes> humSet, String name) {
                            for (Resource.HumanRes human : humSet) {
                                if (human.getName().equals(name)) {
                                    return human;
                                }
                            }
                            return null;
                        }
                    });
                    resourceMap.put("ResourceHuman", humSet);
                }
                //////////////////////////////////////
               JLabel lbllgResources = new JLabel("Resources Logistics:");
               lbllgResources.setBounds(600, 140, 140, 40);
                panel.add(lbllgResources);
                List<JCheckBox> resourcelgCheckBoxes = new ArrayList<>();
                Set<String> resourcelgEntries = log.getLogistics();
                int yPositionlg = 180; // Starting Y position for checkboxes
                int yOffsetlg = 40; // Vertical offset between checkboxes
                TreeSet<Resource.Logistics> subsetlog= new TreeSet<>();
                for (String entry : resourcelgEntries) {
                    String[] data = entry.split(",");
                   if (data.length>1) {
	                    JCheckBox checkBox = new JCheckBox(data[1] + " (" + data[2] + ")");
	                    checkBox.setBounds(600, yPositionlg, 300, 35);
	                    panel.add(checkBox);
	                    resourcelgCheckBoxes.add(checkBox);
	                    yPositionlg += yOffsetlg; // Update Y position for the next checkbox
	                  
		                    checkBox.addActionListener(new ActionListener() {
		                        @Override
		                        public void actionPerformed(ActionEvent e) {
		                            if (checkBox.isSelected()) {
		                            	double d=Double.parseDouble(data[2]);
		                            	   log = new Resource.Logistics(data[1],d);
		                            	subsetlog.add(log);
		                                System.out.print("ADDED!");
		                            } else {
		                            	subsetlog.remove(log);
		                                System.out.print("REMOVED!");
		                            }
		                            System.out.print("last"+subsetlog);
		                        }
		                      
		                    }); 
		                    resourceMap.put("ResourceLog", subsetlog);
                   }  }

             
                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(450, 650, 80, 25);
                panel.add(btnAdd);
              
                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = txtName.getText();
                        String state = txtState.getText();
                        //int duration = Integer.parseInt(txtDuration.getText());
                        double logCost=0.0;
                        double MatCost=0.0;
                        
                        double HumanCost=0.0;
                        Set<String> role=resourceMap.keySet();
                        Iterator<String> it=role.iterator();
                        while (it.hasNext()) {
                            String st = it.next();
                            if (st.equals("ResourceLog")) {  
                                Set<?> resourceLog = resourceMap.get(st);
                                Iterator<?> it1 = resourceLog.iterator();
                                
                                while (it1.hasNext()) {
                                    Object data = it1.next();
                                    Resource r = (Resource) data;
                                    logCost = log.logisticsCost(r);
                                    sumlog += logCost; 
                                }
                                System.out.println("Total logistics cost: " + sumlog);
                               // System.out.println("Resource log: " + resourceLog);
                            }
                            if (st.equals("ResourceMat")) {  
                                Set<?> resourceMat = resourceMap.get(st);
                                Iterator<?> it2 = resourceMat.iterator();
                                
                                while (it2.hasNext()) {
                                    Object data = it2.next();
                                    Resource.Matrielle r = (Resource.Matrielle) data;
                                    MatCost = matricielle.matCost(r);
                                    double q=matricielle.getQuantity(r);
                                    System.out.println("Total q: " + q);
                                    System.out.println("Total cost: " + MatCost);
                                    sumMat += MatCost; 
                                }
                                System.out.println("Total mat cost: " + sumMat);
                               // System.out.println("Resource log: " + resourceLog);
                            }
                            if (st.equals("ResourceHuman")) {  
                                Set<?> resourceHuman = resourceMap.get(st);
                                Iterator<?> it1 = resourceHuman.iterator();
                                
                                while (it1.hasNext()) {
                                    Object data = it1.next();
                                    Resource.HumanRes rh = (Resource.HumanRes) data;
                                    HumanCost = rh.employeeCost();
                                    sumHum += HumanCost; 
                                }
                                System.out.println("Total human: " + sumHum);
                                System.out.println("Total human: " + st);
                               // System.out.println("Resource log: " + resourceLog);
                            }
                        }

                       
                     
                        double cost=r.totalResourceCost(sumlog,sumMat,sumHum);
                        Process process = new Process(name,resourceMap,cost,state,duration);
                        process.addProcess(process);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////End Of Process Addition 
        getProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Processes");
                viewFrame.setSize(800, 600);
                TableRowSorter<DefaultTableModel> sorter;
                
                JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);


                Set<Process> processes = Process.getProcess();

                String[] columnNames ={"Process Name","State", "Duration","Cost"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Process process : processes) {
                    StringBuilder resourcesStr = new StringBuilder();
                  
                    Object[] rowData = {process.getName(),process.getState(), process.getDuration(),process.getCost()};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });

                viewFrame.setVisible(true);
            }
        });
    ///////getting Process end 
        
        ////////Add Task 
        addTasks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Task");
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

   

                JLabel lblMtResources = new JLabel("Processes");
                lblMtResources.setBounds(10, 140, 140, 40);
                panel.add(lblMtResources);

                List<JCheckBox> resourceCheckBoxes = new ArrayList<>();
                Set<Process> processes = process.getProcess(); // Assuming this is not null
                int yPositionn = 180; // Starting Y position for checkboxes
                int yOffsettt = 40; // Vertical offset between checkboxes
                Set<Process> processesAdded = new TreeSet<>();
                for (Process entry : processes) {
                    String name = entry.getName();
                    TreeMap<String, TreeSet<?>> rscMap = entry.getResources();
                    double cost = entry.getCost();
                    String state = entry.getState();
                    int duration = entry.getDuration();

                    JCheckBox checkBox = new JCheckBox(name + " (Cost per Process: " + cost + ")");
                 
                    checkBox.setBounds(10, yPositionn, 200, 25);
                    panel.add(checkBox);
                    resourceCheckBoxes.add(checkBox);
                    yPositionn += yOffsettt; // Update Y position for the next checkbox

                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                            	
                                if ((checkBox.isSelected()) != false) {
                                    Process newProcess = new Process(name, rscMap, cost, state, duration);
                                    processesAdded.add(newProcess);
                                    System.out.println("ADDED! " + newProcess);
                                } else {
                                    // Remove the process if the checkbox is deselected
                                	processesAdded.removeIf(p -> p.getName().equals(name));
                                    System.out.println("REMOVED! " + name);
                                }
                            } catch (ClassCastException cce) {
                                cce.printStackTrace();
                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            } catch (NullPointerException npe) {
                                npe.printStackTrace();
                            }
                        }
                    });
                
}
                JButton addButton = new JButton("Add Task");
                addButton.setBounds(10, yPositionn + yOffsettt, 150, 25);
                panel.add(addButton);

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nameTask = txtName.getText();
                            String stateTask = txtState.getText();
                       
                        	if (nameTask.isEmpty() || stateTask.isEmpty() || processesAdded.isEmpty()) {
                                JOptionPane.showMessageDialog(addFrame, "All fields and at least one process must be selected.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            double sumTask = 0.0;
                            int durationTask = 0;

                            for (Process pr : processesAdded) { // Ensure Process has a getCost() method
                                sumTask += pr.getCost();
                                durationTask += pr.getDuration();
                            }

                            String d = Integer.toString(durationTask);
                            System.out.println("Total Task Cost: " + sumTask);

                            Tasks task = new Tasks(nameTask, processesAdded, sumTask, stateTask, d);

                            task.addTask(task);
                            
                            addFrame.dispose(); // Ensure addFrame is the correct frame to dispose
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        } catch (NullPointerException npe) {
                            npe.printStackTrace();
                        }
                    }
                });


                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });

        //// Get Task
        getTasks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TableRowSorter<DefaultTableModel> sorter;
                JFrame viewFrame = new JFrame("View Tasks");
                viewFrame.setSize(800, 600);
                
                JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);


                Set<Tasks> tasks = Tasks.getTask();

                String[] columnNames ={"Task Name","State", "Cost","Duration"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Tasks task : tasks) {
                    StringBuilder resourcesStr = new StringBuilder();
                  
                    Object[] rowData = {task.getName(),task.getState(), task.getCost(),task.getDuration()};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });
                viewFrame.setVisible(true);
            }
        });
     
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        //// Get Project
        getProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	TableRowSorter<DefaultTableModel> sorter;
                JFrame viewFrame = new JFrame("View Projects");
                viewFrame.setSize(800, 600);
                
                JTextField textField = new JTextField();
        		textField.setBounds(74, 450, 103, 20);
        		viewFrame.getContentPane().add(textField);
        		textField.setColumns(10);
        		
        		JButton btnNewButton = new JButton("Filtrage");
        		btnNewButton.setBounds(187, 450, 80, 23);
        		viewFrame.getContentPane().add(btnNewButton);
        		
                Set<Project> projects = Project.getProject();

                String[] columnNames ={"Project Name","State", "Cost","Duration"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Project project : projects) {
                    StringBuilder resourcesStr = new StringBuilder();
                  
                    Object[] rowData = {project.getName(),project.getState(), project.getCost(),project.getDuration()};
                    tableModel.addRow(rowData);
                }
                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                sorter = new TableRowSorter<>(tableModel);
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	   String filter = textField.getText();
                           //sorter = new TableRowSorter<>(model);
                           RowFilter<DefaultTableModel, Object> rf = null;
                           rf = RowFilter.regexFilter(filter);
                           sorter.setRowFilter(rf);
                           table.setRowSorter(sorter);
                    
                    		
                    		
                    }
                });
                viewFrame.setVisible(true);
            }
        });
        ////////Add Project
        addProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Project");
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


                JLabel lblMtResources = new JLabel("Tasks:");
                lblMtResources.setBounds(10, 140, 140, 40);
                panel.add(lblMtResources);

                List<JCheckBox> resourceCheckBoxes = new ArrayList<>();
                Set<Tasks> tasks = task.getTask(); // Assuming this is not null
                int yPositionn = 180; // Starting Y position for checkboxes
                int yOffsettt = 40; // Vertical offset between checkboxes
                Set<Tasks> tasksAdded = new TreeSet<>(); 
                for (Tasks entry : tasks) {
                    String name = entry.getName();
                    Set<Process> SetOfProcess = entry.getProcesses();
                    double cost = entry.getCost();
                    String statee = entry.getState();
                    String durationn = entry.getDuration();

                    JCheckBox checkBox = new JCheckBox(name + " (Cost per Process: " + cost + ")");
                 
                    checkBox.setBounds(10, yPositionn, 200, 25);
                    panel.add(checkBox);
                    resourceCheckBoxes.add(checkBox);
                    yPositionn += yOffsettt; // Update Y position for the next checkbox

                    checkBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                if (checkBox.isSelected()) {
                                    Tasks newTask = new Tasks(name, SetOfProcess, cost, statee, durationn);
                                    tasksAdded.add(newTask);
                                    System.out.println("ADDED! " + newTask);
                                }
                                else {
                                    // Remove the process if the checkbox is deselected
                                	tasksAdded.removeIf(p -> p.getName().equals(name));
                                    System.out.println("REMOVED! " + name);
                                }
                            } catch (ClassCastException cce) {
                                cce.printStackTrace();
                            } catch (NumberFormatException nfe) {
                                nfe.printStackTrace();
                            } catch (NullPointerException npe) {
                                npe.printStackTrace();
                            }
                        }
                    });
                }

                JButton addButton = new JButton("Add Project");
                addButton.setBounds(10, yPositionn + yOffsettt, 150, 25);
                panel.add(addButton);

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String nameTask = txtName.getText();
                            String stateTask = txtState.getText();
                        	if (nameTask.isEmpty() || stateTask.isEmpty() || tasksAdded.isEmpty()) {
                                JOptionPane.showMessageDialog(addFrame, "All fields  must be not empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            double sumTask = 0.0;
                            int Duration = 0;
                            for (Tasks pr : tasksAdded) {
                                sumTask += pr.getCost(); 
                               Duration+=Double.parseDouble(pr.getDuration());
                            }
                            System.out.println("Total Task Cost: " + sumTask);
                               String s=String.valueOf(sumTask);
                            // Create and add the task
                            Project project = new Project(nameTask, tasksAdded,stateTask, sumTask,Duration);
                            project.addProject(project);
                           
                            DefaultComboBoxModel<Project> model = (DefaultComboBoxModel<Project>) projectComboBox.getModel();
                           
                            model.addElement(project);

                            addFrame.dispose();
                        } catch (NumberFormatException nfe) {
                            nfe.printStackTrace();
                        } catch (NullPointerException npe) {
                            npe.printStackTrace();
                        }
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });

    }
    
}
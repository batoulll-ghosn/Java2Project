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
public class MyFinalProject {
    private JFrame frame;
    private Resource.Matricielle matricielle;
    private Resource.HumanResources human;
    private Resource.Logistics log;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MyFinalProject window = new MyFinalProject();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MyFinalProject() {
        matricielle = new Resource.Matricielle("default", 0.0);
       
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
        JMenuItem getHuman = new JMenuItem("View And Edit Human Resources");
        JMenuItem getResource = new JMenuItem("View And Edit Matiere Resources");
        JMenuItem getlogistic = new JMenuItem("View And Edit Logistics Resources");
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
        addlogistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JFrame frame=new JFrame();
            	    JTextField txtlog;
            	    JTextField txtcost;
            	     JButton btnNewButton;
            	       
            	        frame.setSize(400,400);
            	
            	        frame.getContentPane().setLayout(null);
            	        
            	        txtlog = new JTextField();
            	        txtlog.setBounds(80, 40, 119, 20);
            	        frame.getContentPane().add(txtlog);
            	        txtlog.setColumns(10);
            	        
            	        JLabel lblNewLabel = new JLabel("Logistic");
            	        lblNewLabel.setBounds(24, 43, 46, 14);
            	        frame.getContentPane().add(lblNewLabel);
            	        
            	        JLabel lblNewLabel_1 = new JLabel("Cost");
            	        lblNewLabel_1.setBounds(24, 86, 46, 14);
            	        frame.getContentPane().add(lblNewLabel_1);
            	        
            	        txtcost = new JTextField();
            	        txtcost.setBounds(80, 83, 119, 20);
            	        frame.getContentPane().add(txtcost);
            	        txtcost.setColumns(10);
            	        
            	        btnNewButton = new JButton("Add");
            	        btnNewButton.setBounds(95, 136, 89, 23);
            	        frame.getContentPane().add(btnNewButton);
            	        frame.setVisible(true);
            	        btnNewButton.addActionListener(new ActionListener() {
            	        	public void actionPerformed(ActionEvent e) {
            	        	           String name = txtlog.getText();    
            	        	           double costPerUnit = Double.parseDouble(txtcost.getText());
            	        	           log.addlog(name, costPerUnit);
            	        	           frame.dispose();
            	        	        	}
            	        	        });           	        		    
            }
        });
         
        getlogistic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View And Edit Logistics Resources");
                viewFrame.setSize(800, 600);

                String[] columnNames = {"Identifier", "Name", "Cost", "Update"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);

                List<String> entries = log.getLogistic();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    tableModel.addRow(new Object[]{data[0], data[1], data[2], "Update"});
                }

                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(3).setCellRenderer(new ButtonRenderer());
                columnModel.getColumn(3).setCellEditor(new ButtonEditor(new JTextField(), matricielle, tableModel));

                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                viewFrame.setVisible(true);
            }
        });
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
                        matricielle.addMatriciele(name, costPerUnit);
                        addFrame.dispose();
                    }
                });

                addFrame.getContentPane().add(panel);
                addFrame.setVisible(true);
            }
        });
        addProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new JFrame("Add Process");
                addFrame.setSize(400, 300);

                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel lblName = new JLabel("Process Name:");
                lblName.setBounds(10, 10, 100, 25);
                panel.add(lblName);

                JTextField txtName = new JTextField();
                txtName.setBounds(120, 10, 160, 25);
                panel.add(txtName);

                JLabel lblDuration = new JLabel("Duration:");
                lblDuration.setBounds(10, 40, 100, 25);
                panel.add(lblDuration);

                JTextField txtDuration = new JTextField();
                txtDuration.setBounds(120, 40, 160, 25);
                panel.add(txtDuration);

                JLabel lblResources = new JLabel("Resources:");
                lblResources.setBounds(10, 70, 100, 25);
                panel.add(lblResources);

                List<JCheckBox> resourceCheckBoxes = new ArrayList<>();
                List<String> resourceEntries = matricielle.getMatriciele();
                int y = 100;
                for (String entry : resourceEntries) {
                    String[] data = entry.split(",");
                    JCheckBox checkBox = new JCheckBox(data[1] + " (Cost per unit: " + data[2] + ")");
                    checkBox.setBounds(10, y, 300, 25);
                    panel.add(checkBox);
                    resourceCheckBoxes.add(checkBox);
                    y += 30;
                }
                JLabel lblTotalCost = new JLabel("Total Cost:");
                lblTotalCost.setBounds(10, 70, 100, 25);
                panel.add(lblTotalCost);
                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(120, y, 80, 25);
                panel.add(btnAdd);

          

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = txtName.getText();
                        int duration = Integer.parseInt(txtDuration.getText());
                        List<Resource> selectedResources = new ArrayList<>();
                        double totalCost = 0.0;
                      
                        for (int i = 0; i < resourceCheckBoxes.size(); i++) {
                          
                            if (resourceCheckBoxes.get(i).isSelected()) {
                                String[] data = resourceEntries.get(i).split(",");
                                String resourceName = data[1];
                                double costPerUnit = Double.parseDouble(data[2]);
                                selectedResources.add(new Resource(resourceName, costPerUnit));
                                totalCost += matricielle.calculCostMat(1, resourceName, costPerUnit);
                            }
                         
                        }
                        lblTotalCost.setText("Total Cost: " + totalCost);
                        Process newProcess = new Process(1, name, selectedResources, "Active", duration);
                        Process.addProcess(new ArrayList<>(), newProcess);

                        
                        addFrame.dispose();
                    }
                });

                addFrame.add(panel);
                addFrame.setVisible(true);
            }
        });

   

        getProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame viewFrame = new JFrame("View Processes");
                viewFrame.setSize(800, 600);

                List<Process> processes = Process.getProcess();

                String[] columnNames ={"Process Name","Resources", "Cost", "State", "Duration"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Process process : processes) {
                    StringBuilder resourcesStr = new StringBuilder();
                    for (Resource resource : process.getResources()) {
                        resourcesStr.append(resource.getName()).append(": ").append(resource.getCostPerUnit()).append("; ");
                    }
                    Object[] rowData = {process.getName(),resourcesStr.toString(), process.getCost(), process.getState(), process.getDuration()};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.add(scrollPane, BorderLayout.CENTER);

                viewFrame.setVisible(true);
            }
        });
    

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
                        matricielle.addMatriciele(name, costPerUnit);
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

                String[] columnNames = {"Identifier", "Name", "Cost per Unit", "Update"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return column != 0; // Make the identifier column non-editable
                    }
                };
                JTable table = new JTable(tableModel);

                List<String> entries = matricielle.getMatriciele();
                for (String entry : entries) {
                    String[] data = entry.split(",");
                    tableModel.addRow(new Object[]{data[0], data[1], data[2], "Update"});
                }

                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(3).setCellRenderer(new ButtonRenderer());
                columnModel.getColumn(3).setCellEditor(new ButtonEditor(new JTextField(), matricielle, tableModel));

                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                viewFrame.setVisible(true);
            }
        });

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    public void updateMatriciele(int identifier, String newName, double newCostPerUnit) {
        List<String> entries = matricielle.getMatriciele();
        List<String> updatedEntries = new ArrayList<>();
        String idStr = String.valueOf(identifier);
        boolean updated = false;

        for (String entry : entries) {
            String[] data = entry.split(",");
            if (data[0].equals(idStr)) {
                data[1] = newName;
                data[2] = String.valueOf(newCostPerUnit);
                entry = String.join(",", data);
                updated = true;
                System.out.println("Updated entry: " + entry);
            }
            updatedEntries.add(entry);
        }

        if (!updated) {
            System.out.println("Identifier not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.txt"))) {
            for (String updatedEntry : updatedEntries) {
                writer.write(updatedEntry);
                writer.newLine();
            }
            System.out.println("Update Successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
   
    

}
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setText("Update");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private Resource.Matricielle matricielle;
    private DefaultTableModel tableModel;
    private int row;

    public ButtonEditor(JTextField textField, Resource.Matricielle matricielle, DefaultTableModel tableModel) {
        super();
        this.matricielle = matricielle;
        this.tableModel = tableModel;
        button = new JButton("Update");
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Update";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int identifier = Integer.parseInt((String) tableModel.getValueAt(row, 0));
        String newName = (String) tableModel.getValueAt(row, 1);
        double newCostPerUnit = Double.parseDouble((String) tableModel.getValueAt(row, 2));
        matricielle.updateMatriciele(identifier, newName, newCostPerUnit);
        fireEditingStopped();
    }
}
//update human////////////////////////////////


class ButtonRenderer1 extends JButton implements TableCellRenderer {
    public ButtonRenderer1() {
        setText("Update");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
class ButtonEditor1 extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private Resource.HumanResources human;
    private DefaultTableModel tableModel;
    private int row;

    public ButtonEditor1(JTextField textField, Resource.HumanResources human, DefaultTableModel tableModel) {
        super();
        this.human = human;
        this.tableModel = tableModel;
        button = new JButton("Update");
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Update";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int identifier = Integer.parseInt((String) tableModel.getValueAt(row, 0));
        String newName = (String) tableModel.getValueAt(row, 1);
        double newduration = Double.parseDouble((String) tableModel.getValueAt(row, 2));
        double newcost = Double.parseDouble((String) tableModel.getValueAt(row, 3));
        human.updateHuman(identifier, newName,newduration,newcost );
        fireEditingStopped();
    }
}


}





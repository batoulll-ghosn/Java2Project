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

public class MyFinalProject {
    private JFrame frame;
    private Resource.Matricielle matricielle;

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
        frame = new JFrame("OMEGA Company");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Home");
        JMenu menuProject = new JMenu("Projects");
        JMenu menuTask = new JMenu("Tasks");
        JMenu menuProcess = new JMenu("Processes");
        JMenu menuResource = new JMenu("Resources");

        JMenuItem getResource = new JMenuItem("View And Edit Matricielle Resources");
        JMenuItem addResource = new JMenuItem("Add Matricielle Resource");
        menuResource.add(getResource);
        menuResource.add(addResource);
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

                JButton btnAdd = new JButton("Add");
                btnAdd.setBounds(120, y, 80, 25);
                panel.add(btnAdd);

                JLabel lblTotalCost = new JLabel("Total Cost: 0.0");
                lblTotalCost.setBounds(120, y + 30, 200, 25);
                panel.add(lblTotalCost);

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

                        Process newProcess = new Process(1, selectedResources, "Active", duration);
                        Process.addProcess(new ArrayList<>(), newProcess);

                        lblTotalCost.setText("Total Cost: " + totalCost);
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

                String[] columnNames = {"Identifier", "Resources", "Cost", "State", "Duration"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                for (Process process : processes) {
                    StringBuilder resourcesStr = new StringBuilder();
                    for (Resource resource : process.getResources()) {
                        resourcesStr.append(resource.getName()).append(": ").append(resource.getCostPerUnit()).append("; ");
                    }
                    Object[] rowData = {process.getIdentifier(), resourcesStr.toString(), process.getCost(), process.getState(), process.getDuration()};
                    tableModel.addRow(rowData);
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                viewFrame.add(scrollPane, BorderLayout.CENTER);

                viewFrame.setVisible(true);
            }
        });
    }

    public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
        private JTable table;
        private JButton renderButton;
        private JButton editButton;
        private String text;

        public ButtonColumn(JTable table, int column) {
            super();
            this.table = table;
            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                
            }
            renderButton.setText((value == null) ? "" : value.toString());
            return renderButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.setText(text);
            return editButton;
        }

        @Override
        public Object getCellEditorValue() {
            return text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            System.out.println(e.getActionCommand() + " : " + table.getSelectedRow());
        }
    }
}

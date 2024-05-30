import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class MyFinalProject {
    private JFrame frame;
    private Resource resource;
    private Resource.Matricielle matricielle;

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

    public MyFinalProject() {
        resource = new Resource();
        matricielle = resource.new Matricielle();
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

        JMenuItem getResource = new JMenuItem("View And Edit Matiere Resources");
        JMenuItem addResource = new JMenuItem("Add Matiere Resource");
        menuResource.add(getResource);
        menuResource.add(addResource);

        menuBar.add(menuFile);
        menuBar.add(menuProject);
        menuBar.add(menuTask);
        menuBar.add(menuProcess);
        menuBar.add(menuResource);

        frame.setJMenuBar(menuBar);

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
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
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

        frame.setVisible(true);
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
}

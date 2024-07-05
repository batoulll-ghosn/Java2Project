import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ProjectComboBoxObserver implements Observer {
    private JComboBox<Project> comboBox;

    public ProjectComboBoxObserver(JComboBox<Project> comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public void update(Observable o, Object arg) {
        Project project = (Project) arg;
        DefaultComboBoxModel<Project> model = (DefaultComboBoxModel<Project>) comboBox.getModel();
        model.removeAllElements();
        model.addElement(null); // Optional: Add null as the first element
        if (project != null) {
          
        }
        comboBox.setSelectedItem(null); // Set selected item to null
    }
}

// Similar classes for TasksComboBoxObserver and ProcessComboBoxObserver

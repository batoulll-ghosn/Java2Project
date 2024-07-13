import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

public class Project extends ObservableEntity implements Serializable, Comparable<Project> {
    private static int next = 1;
    private int identifier;
    private Set<Tasks> tasks;
    private String stateOfImp;
    private double cost;
    private int duration;
    private String name;
    
    // Constructor
    private static final String FILE_NAME = "project.dat";
    private static Set<Project> projectSet = new HashSet<>(); // Changed to LinkedHashSet
    
    public Project(String name, Set<Tasks> tasks, String stateOfImp, double cost, int duration) {
        this.identifier = next++;
        this.tasks = tasks;
        this.name = name;
        this.stateOfImp = stateOfImp;
        this.cost = cost;
        this.duration = duration;
        addProjectToSetAndNotify(this);
    }

    // Getter and Setter methods
    public int getIdentifier() {
        return identifier;
    }

    public Set<Tasks> getTasks() {
        return this.tasks;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    private void addProjectToSetAndNotify(Project project) {
        projectSet.add(project);
        notifyObservers(project);
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public String getStateOfImp() {
        return stateOfImp;
    }

    public void setStateOfImp(String stateOfImp) {
        this.stateOfImp = stateOfImp;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDuration() {
        return duration;
    }

    public String getState() {
        return this.stateOfImp;
    }

    public String getName() {
        return this.name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Set<Project> getProject() {
        try {
            File prFile = new File(FILE_NAME);
            if (prFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(prFile));
                projectSet = (Set<Project>) ois.readObject();
                ois.close();
            } else {
                projectSet = new HashSet<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return projectSet;
    }

    public static void addProject(Project pr) {
        projectSet = getProject();
        projectSet.add(pr);
        System.out.println("New process added: " + pr);
        System.out.println("Updated process set: " + projectSet);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(projectSet);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Set<Tasks> emptySet = new HashSet<>();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"));
            oos.writeObject(emptySet);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public double totalCost() {
        return cost;
    }

    // Method to update the status of the project
    public void updateStatus(String newState) {
        this.stateOfImp = newState;
    }

    // Method to update the project details
    public void updateProject(int identifier, Set<Tasks> tasks, String stateOfImp, double cost, int duration) {
        this.identifier = identifier;
        this.tasks = tasks;
        this.stateOfImp = stateOfImp;
        this.cost = cost;
        this.duration = duration;
    }

    @Override
    public int compareTo(Project o) {
        return Integer.compare(this.identifier, o.identifier);
    }

    @Override
    public String toString() {
        return "Project: " + name +
               ", stateOfImp=" + stateOfImp +
               ", cost=" + cost +
               ", duration=" + duration + " days";
    }

    public static void main(String[] args) {
        Set<Project> projects = getProject();
        for (Project project : projects) {
            System.out.println(project);
        }
    }
}

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class Project implements Serializable, Comparable<Project> {


    private int identifier;
    private Set<Tasks> tasks;
    private String stateOfImp;
    private double cost;
    private double duration;

    // Constructor
    public Project(int identifier, Set<Tasks> tasks, String stateOfImp, double cost, double duration) {
        this.identifier = identifier;
        this.tasks = tasks;
        this.stateOfImp = stateOfImp;
        this.cost = cost;
        this.duration = duration;
    }

    // Constructor with only identifier
    public Project(int identifier) {
        this.identifier = identifier;
        this.tasks = new TreeSet<>();
        this.stateOfImp = "";
        this.cost = 0.0;
        this.duration = 0.0;
    }

    // Getter and Setter methods
    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public Set<Tasks> getTasks() {
        return tasks;
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
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public static void addProject(Project project) {
        boolean fileExists = new File("project.txt").exists();

        try (ObjectOutputStream os = fileExists 
                ? new AppendableObjectOutputStream(new FileOutputStream("project.txt", true))
                : new ObjectOutputStream(new FileOutputStream("project.txt"))) {
            os.writeObject(project);
            System.out.println("Project added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Project> getProject() {
        Set<Project> projects = new TreeSet<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("project.txt"))) {
            while (true) {
                try {
                    Object x = is.readObject();
                    if (x instanceof Project) {
                        projects.add((Project) x);
                    }
                } catch (EOFException eof) {
                    break; // End of file reached
                }
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class Not Found Exception");
        } catch (FileNotFoundException nfne) {
            System.out.println("Your file is not found!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }

    // Method to calculate the total cost
    public double totalCost() {
        // Assuming cost calculation based on some logic; here it's simply returning the cost
        return cost;
    }

    // Method to update the status of the project
    public void updateStatus(String newState) {
        this.stateOfImp = newState;
    }

    // Method to update the project details
    public void updateProject(int identifier, Set<Tasks> tasks, String stateOfImp, double cost, double duration) {
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
        return "Project{" +
                "identifier=" + identifier +
                ", tasks=" + tasks +
                ", stateOfImp='" + stateOfImp + '\'' +
                ", cost=" + cost +
                ", duration=" + duration +
                '}';
    }

    public static void main(String[] args) {
        // Example usage:
        Project project = new Project(1, new TreeSet<>(), "Initial", 100.0, 3600000);
        addProject(project);
        project.updateStatus("In Progress");
        System.out.println(project);
    }
}



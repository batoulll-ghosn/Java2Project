import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Tasks extends ObservableEntity implements Serializable, Comparable<Tasks> {
    private int identifier;
    private Set<Process> processus;
    private double cost;
    private static int next = 1;
    private String state;
    private String name;
    private String duration; 
    private static Set<Tasks> taskSet = new HashSet<>(); // Changed to LinkedHashSet
    private static final String FILE_NAME = "tasks.dat";
    public Tasks(String name, Set<Process> p, double cost, String state, String duration) {
        this.identifier = next++;
        this.processus = p;
        this.name = name;
        this.cost = cost;
        this.state = state;
        this.duration = duration;
    }

    public Set<Process> getProcesses() {
        return this.processus;
    }
    public static void addTask(Tasks task) {
        Set<Tasks> taskSet = getTask();
        taskSet.add(task);
        System.out.println("New process added: " + task);
        System.out.println("Updated process set: " + taskSet);
        try {
            // Write the updated taskSet to the file
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(taskSet);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Set<Process> emptySet = new HashSet<>();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("process.dat"));
            oos.writeObject(emptySet);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Set<Tasks> getTask() {
        try {
            File processFile = new File(FILE_NAME);
            if (processFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(processFile));
                taskSet = (Set<Tasks>) ois.readObject();
                ois.close();
            } else {
            	taskSet = new HashSet<>(); 
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return taskSet;
    }
    
    public double calculCost(String name) {
        return this.cost;
    }

    public void updateStatus(String newState) {
        this.state = newState;
    }

    public int getIdentifier() {
        return identifier;
    }
    public String getName() {
        return name;
    }
    public double getCost() {
        return cost;
    }

    public String getState() {
        return state;
    }

    public String getDuration() {
        return duration;
    }

    

    @Override
    public String toString() {
        return "Task" +name+
                "cost=" + cost +
                ", state='" + state + '\'' +
                ", duration=" + duration 
                ;
    }
    @Override
    public int compareTo(Tasks other) {
        return Integer.compare(this.identifier, other.identifier);
    }
    public static void main(String[] args) {
     
    }
}

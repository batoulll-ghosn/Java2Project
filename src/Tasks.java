import java.io.EOFException;
import java.io.NotSerializableException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Tasks implements Serializable, Comparable<Tasks> {
    private int identifier;
    private List<String> processus;
    private double cost;
    private String state;
    private long duration; // Using long to represent time in milliseconds

    public Tasks(int identifier) {
        this.identifier = identifier;
        this.processus = new ArrayList<>();
        this.cost = 0.0;
        this.state = "initial";
        this.duration = 0;
    }

    public void addTask(Tasks task) {
        boolean fileExists = new File("task.txt").exists();
        try (ObjectOutputStream os = fileExists 
                ? new AppendableObjectOutputStream(new FileOutputStream("task.txt", true))
                : new ObjectOutputStream(new FileOutputStream("task.txt"))) {
            os.writeObject(task);
            System.out.println("Task added successfully!");
        }catch (NotSerializableException n) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String task) {
        processus.remove(task);
    }

    public Set<Tasks> getTask() {
    	Set<Tasks> myTasks = new TreeSet<>();
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("task.txt"))) {
            while (true) {
                try {
                    Object x = is.readObject();
                    if (x instanceof Tasks) {
                        myTasks.add((Tasks) x);
                    }
                } catch (EOFException eof) {
                    break; // End of file reached
                }
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class Not Found Exception");
        } catch (FileNotFoundException nfne) {
            System.out.println("YOUR FILE IS NOT FOUND!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myTasks;
    
    }

    public void calculCost(double costPerTask) {
        this.cost = processus.size() * costPerTask;
    }

    public void updateStatus(String newState) {
        this.state = newState;
    }

    public int getIdentifier() {
        return identifier;
    }

    public double getCost() {
        return cost;
    }

    public String getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Task{" +
                "identifier=" + identifier +
                ", processus=" + processus +
                ", cost=" + cost +
                ", state='" + state + '\'' +
                ", duration=" + duration +
                '}';
    }
    @Override
    public int compareTo(Tasks other) {
        return Integer.compare(this.identifier, other.identifier);
    }
    public static void main(String[] args) {
        // Example usage:
        Tasks task = new Tasks(1);
        task.addTask(task);
        task.addTask(task);
        task.calculCost(100.0);
        task.updateStatus("In Progress");
        task.setDuration(3600000);  

        System.out.println(task);
    }
}

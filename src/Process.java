import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Process extends ObservableEntity implements Serializable, Comparable<Process> {

    private static int next = 1;
    private int identifier;
    private String name;
    private TreeMap<String, TreeSet<?>> rscMap;
    private double cost;
    private String state;
    private int duration;
    private static final String FILE_NAME = "process.dat";
    private static Set<Process> processSet = new HashSet<>(); // Changed to LinkedHashSet

    public Process(String name, TreeMap<String, TreeSet<?>> rsc, double cost, String state, int duration) {
        this.identifier = next++;
        this.name = name;
        this.state = state;
        this.duration = duration;
        this.rscMap = rsc;
        this.cost = cost;
    }

    // Other getter methods remain unchanged...

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.identifier, other.identifier);
    }

    @Override
    public String toString() {
        return name + "," + cost + "," + state + "," + duration;
    }
    public int getIdentifier() {
        return identifier;
    }

    public double getCost() {
        return this.cost;
    }

    public String getState() {
        return state;
    }

    public TreeMap<String, TreeSet<?>> getResources() {
        return rscMap;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public TreeMap<String, TreeSet<?>> getResource() {
        return rscMap;
    }

    

    public static Set<Process> getProcess() {
        try {
            File processFile = new File(FILE_NAME);
            if (processFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(processFile));
                processSet = (Set<Process>) ois.readObject();
                ois.close();
            } else {
                processSet = new HashSet<>(); 
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return processSet;
    }

    public static void addProcess(Process process) {
        processSet = getProcess();
        processSet.add(process);
        System.out.println("New process added: " + process);
        System.out.println("Updated process set: " + processSet);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(processSet);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Add a process for testing
        TreeMap<String, TreeSet<?>> resources2 = new TreeMap<>();
        resources2.put("Resource2", new TreeSet<>(Arrays.asList("Item3", "Item4")));
        Process process2 = new Process("Process 8", resources2, 200.0, "inactive", 10);
        addProcess(process2);

        // Get and print all processes
        Set<Process> processes = getProcess();
        for (Process p : processes) {
            System.out.println(p);
        }
    }
}

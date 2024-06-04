import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Process {
    private int identifier;
    private String Name;
    private List<Resource> resources;
    private double cost;
    private String state;
    private int duration;

    public Process(int identifier, String Name, List<Resource> resources, String state, int duration) {
        this.identifier = identifier;
        this.resources = resources;
        this.cost = calculateCost(resources);
        this.state = state;
        this.Name = Name;
        this.duration = duration;
    }

    private double calculateCost(List<Resource> resources) {
        return resources.stream().mapToDouble(Resource::getCostPerUnit).sum();
    }

    public int getIdentifier() {
        return identifier;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public double getCost() {
        return cost;
    }

    public String getState() {
        return state;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        StringBuilder resourcesStr = new StringBuilder();
        for (Resource resource : resources) {
            resourcesStr.append(resource.getName()).append(":").append(resource.getCostPerUnit()).append(";");
        }
        return identifier + "," + Name + "," + resourcesStr.toString() + "," + cost + "," + state + "," + duration;
    }

    public static int getNextIdentifier() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("process.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    public static void addProcess(List<Process> processes, Process process) {
        int nextId = getNextIdentifier();
        process.identifier = nextId;
        processes.add(process);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("process.txt", true))) {
            String newEntry = process.toString();
            if (new java.io.File("process.txt").length() > 0) {
                writer.newLine();
            }
            writer.write(newEntry);
            System.out.println("Excellent Entering Data!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Process> getProcess() {
        List<Process> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("process.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String[] resourceParts = parts[2].split(";");
                    List<Resource> resources = new ArrayList<>();
                    for (String res : resourceParts) {
                        String[] resParts = res.split(":");
                        resources.add(new Resource(resParts[0], Double.parseDouble(resParts[1])));
                    }
                    double cost = Double.parseDouble(parts[3]);
                    String state = parts[4];
                    int duration = Integer.parseInt(parts[5]);
                    entries.add(new Process(id, name, resources, state, duration));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource("Resource3", 50.0));
        resources.add(new Resource("Resource4", 75.0));

        Process newProcess = new Process(0, "Project1", resources, "Active", 5); // Placeholder ID
        Process.addProcess(processes, newProcess);
    }
}

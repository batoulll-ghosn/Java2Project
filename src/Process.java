import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Process {
    private int identifier;
    private List<Resource> resources;
    private double cost;
    private String state;
    private int duration;  // Assuming duration is in some unit of time

    public Process(int identifier, List<Resource> resources, String state, int duration) {
        this.identifier = identifier;
        this.resources = resources;
        this.cost = calculateCost(resources);
        this.state = state;
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

    @Override
    public String toString() {
        StringBuilder resourcesStr = new StringBuilder();
        for (Resource resource : resources) {
            resourcesStr.append(resource.getName()).append(":").append(resource.getCostPerUnit()).append(";");
        }
        return identifier + "," + resourcesStr.toString() + "," + cost + "," + state + "," + duration;
    }

    public static void addProcess(List<Process> processes, Process process) {
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
                    String[] resourceParts = parts[1].split(";");
                    List<Resource> resources = new ArrayList<>();
                    for (String res : resourceParts) {
                        String[] resParts = res.split(":");
                        resources.add(new Resource(resParts[0], Double.parseDouble(resParts[1])));
                    }
                    double cost = Double.parseDouble(parts[2]);
                    String state = parts[3];
                    int duration = Integer.parseInt(parts[4]);
                    entries.add(new Process(id, resources, state, duration));
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

        Process newProcess = new Process(1, resources, "Active", 5);
        Process.addProcess(processes, newProcess);

        List<Process> allProcesses = Process.getProcess();
        for (Process process : allProcesses) {
            System.out.println(process);
        }
    }
}

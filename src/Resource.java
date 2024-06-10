import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
public class Resource {
    private int identifier;
    private String name;
    private double costPerUnit;
    private static final Path LOG_FILE_PATH = Paths.get("log.txt");

    public Resource(String name, double costPerUnit) {
        this.name = name;
        this.costPerUnit = costPerUnit;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public double totalResourceCost(double matCost, double humanCost, double logisticCost) {
        return matCost + humanCost + logisticCost;
    }

    public static class Logistics extends Resource {
        public Logistics(String name, double logisticCost) {
            super(name, logisticCost);
        }

        public double logisticsCost(String log) {
            if (log.equalsIgnoreCase("Fuel") || log.equalsIgnoreCase("Electricity")) {
                if (log.contains("Fuel") && log.contains("Electricity")) {
                    return 50.00; // Special cost for both "Fuel" and "Electricity"
                } else {
                    return 20.00; // Cost for "Fuel"
                }
            } else if (log.equalsIgnoreCase("Electricity")) {
                if (log.contains("Fuel") && log.contains("Electricity")) {
                    return 50.00; // Special cost for both "Fuel" and "Electricity"
                } else {
                    return 30.00; // Cost for "Electricity"
                }
            }
            return 0.00; // Default case
        }
    }
    public void addlog(String name, double cost) {
   	 int newIdentifier = 1;

        // Read all existing entries to find the highest identifier
    

        // Prepare the new entry
        String id = String.valueOf(newIdentifier);
        String costPerUnitStr = String.valueOf(cost);
       // String duration1=String.valueOf(duration);
        String[] array = {id, name, costPerUnitStr};
        String newEntry = String.join(",", array);

  
        try (Stream<String> lines = Files.lines(LOG_FILE_PATH)) {
            List<String> entries = lines.collect(Collectors.toList());
            for (String entry : entries) {
                String[] data = entry.split(",");
                int currentId = Integer.parseInt(data[0]);
                if (currentId >= newIdentifier) {
                    newIdentifier = currentId + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getLogistic() {
        List<String> entries1 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("log.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    entries1.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries1;
    }

    public static class HumanResources extends Resource {
        String specialty;
        String function;
        double costPerHour;

        public HumanResources(String name, double costPerHour) {
            super(name, costPerHour);
        }

        public double calculCostHum(double numberOfWorkingHours, String[] func) {
            double totalCost = 0.00;
            for (String f : func) {
                if (f.equalsIgnoreCase("engineer")) {
                    totalCost += 20.00 * numberOfWorkingHours;
                } else if (f.equalsIgnoreCase("Technician")) {
                    totalCost += 15.00 * numberOfWorkingHours;
                } else if (f.equalsIgnoreCase("Manager")) {
                    totalCost += 28.00 * numberOfWorkingHours;
                }
            }
            return totalCost;
        }
    }
    public void addHuman(String name, double duration ,double cost) {
        int newIdentifier = 1;

        // Read all existing entries to find the highest identifier
        List<String> entries = getHuman();
        for (String entry : entries) {
            String[] data = entry.split(",");
            int currentId = Integer.parseInt(data[0]);
            if (currentId >= newIdentifier) {
                newIdentifier = currentId + 1;
            }
        }
        // Prepare the new entry
        String id = String.valueOf(newIdentifier);
        String costPerUnitStr = String.valueOf(cost);
        String duration1=String.valueOf(duration);
        String[] array = {id, name, duration1, costPerUnitStr};
        String newEntry = String.join(",", array);

        // Write the new entry to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("human.txt", true))) {
            if (!entries.isEmpty()) {
                writer.newLine();
            }
            writer.write(newEntry);
            System.out.println("Excellent Entering Data!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getHuman() {
        List<String> entries1 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("human.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    entries1.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries1;
    }
    public void updateHuman(int identifier, String newName, double duration, double cost) {
        List<String> entries = getHuman();
        List<String> updatedEntries = new ArrayList<>();
        String idStr = String.valueOf(identifier);
        boolean updated = false;

        for (String entry : entries) {
            String[] data = entry.split(",");
            if (data.length == 3 && data[0].trim().equals(idStr)) {
                // Update the entry
                data[1] = newName;
                data[2] = String.valueOf(duration);
                data[3] = String.valueOf(cost);
                entry = String.join(",", data);
                updated = true;
                System.out.println("Updated entry: " + entry);
            }
            updatedEntries.add(entry);
        }

        if (!updated) {
            System.out.println("Identifier not found.");
            return;
        }

        // Write the updated entries back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("human.txt"))) {
            for (String updatedEntry : updatedEntries) {
                writer.write(updatedEntry);
                writer.newLine();
            }
            System.out.println("Update Successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
 

    public static class Matricielle extends Resource {
        public Matricielle(String name, double costPerUnit) {
            super(name, costPerUnit);
        }

        public double calculCostMat(double quantity, String name,double costPerUnit) {
            double totalCost = 0.00;
            totalCost=costPerUnit*quantity;
            return totalCost;
        }

        public void addMatriciele(String name, double costPerUnit) {
            int newIdentifier = 1;

            // Read all existing entries to find the highest identifier
            List<String> entries = getMatriciele();
            for (String entry : entries) {
                String[] data = entry.split(",");
                int currentId = Integer.parseInt(data[0]);
                if (currentId >= newIdentifier) {
                    newIdentifier = currentId + 1;
                }
            }

            // Prepare the new entry
            String id = String.valueOf(newIdentifier);
            String costPerUnitStr = String.valueOf(costPerUnit);
            String[] array = {id, name, costPerUnitStr};
            String newEntry = String.join(",", array);

            // Write the new entry to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.dat", true))) {
                if (!entries.isEmpty()) {
                    writer.newLine();
                }
                writer.write(newEntry);
                System.out.println("Excellent Entering Data!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateMatriciele(int identifier, String newName, double newCostPerUnit) {
            List<String> entries = getMatriciele();
            List<String> updatedEntries = new ArrayList<>();
            String idStr = String.valueOf(identifier);
            boolean updated = false;

            for (String entry : entries) {
                String[] data = entry.split(",");
                if (data.length == 3 && data[0].trim().equals(idStr)) {
                    // Update the entry
                    data[1] = newName;
                    data[2] = String.valueOf(newCostPerUnit);
                    entry = String.join(",", data);
                    updated = true;
                    System.out.println("Updated entry: " + entry);
                }
                updatedEntries.add(entry);
            }

            if (!updated) {
                System.out.println("Identifier not found.");
                return;
            }

            // Write the updated entries back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.dat"))) {
                for (String updatedEntry : updatedEntries) {
                    writer.write(updatedEntry);
                    writer.newLine();
                }
                System.out.println("Update Successful!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public List<String> getMatriciele() {
            List<String> entries = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("Resources.dat"))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        entries.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return entries;
        }
    }
}

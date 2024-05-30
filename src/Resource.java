import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Resource {
    private int identifier; 

    public double TotalResourceCost(double matCost, double humanCost, double logisticCost) {
        return matCost + humanCost + logisticCost;
    }

    public class Logistics extends Resource {
        String name;
        double logisticCost;

        public double LogisticsCost(String log) {
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

    public class HumanResources extends Resource {
        String specialty;
        String function;
        double costPerHour;
        String name;

        public double CalculCostHum(double numberOfWorkingHours, String[] func) {
            double costPerHour = 0.00;
            for (String f : func) {
                if (f.equalsIgnoreCase("engineer")) {
                    costPerHour += 20.00 * numberOfWorkingHours;
                } else if (f.equalsIgnoreCase("Technicien")) {
                    costPerHour += 15.00 * numberOfWorkingHours;
                } else if (f.equalsIgnoreCase("Manager")) {
                    costPerHour += 28.00 * numberOfWorkingHours;
                }
            }
            return costPerHour;
        }
    }

    public class Matricielle extends Resource {
        String name;
        double costPerUnit;

        public double CalculCostMat(double quantity, String name) {
            double totalCost = 0.00;
            if (name.equalsIgnoreCase("plastic")) {
                totalCost += 1.00 * quantity;
            } else if (name.equalsIgnoreCase("iron")) {
                totalCost += 2.00 * quantity;
            } else if (name.equalsIgnoreCase("Glass")) {
                totalCost += 4.00 * quantity;
            }
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
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.txt", true))) {
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
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.txt"))) {
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
            try (BufferedReader reader = new BufferedReader(new FileReader("Resources.txt"))) {
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

    public static void main(String[] args) {
        Resource resource = new Resource(); 
        Resource.HumanResources hr = resource.new HumanResources();
        Resource.Logistics lg = resource.new Logistics();
        Resource.Matricielle mt = resource.new Matricielle();

        mt.updateMatriciele(1, "mk", 5.0);
        System.out.print(mt);
    }
}

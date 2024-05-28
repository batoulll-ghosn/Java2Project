package Java3Project;
import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Resource {
    private int identifier; 

    public double TotalResourceCost(double matCost, double humanCost, double logisticCost) {
        double totalCostResource = matCost + humanCost + logisticCost;
        return totalCostResource;
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
            for (int i = 0; i < func.length; i++) { // Corrected array length check
                if (func[i].equalsIgnoreCase("engineer")) {
                    costPerHour += 20.00 * numberOfWorkingHours;
                }
                if (func[i].equalsIgnoreCase("Technicien")) {
                    costPerHour += 15.00 * numberOfWorkingHours;
                }
                if (func[i].equalsIgnoreCase("Manager")) {
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
            }
            if (name.equalsIgnoreCase("iron")) {
                totalCost += 2.00 * quantity;
            }
            if (name.equalsIgnoreCase("Glass")) {
                totalCost += 4.00 * quantity;
            }
            return totalCost;
        }
        public void addMatriciele(int identifier, String name, double costPerUnit) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Resources.txt", true))) {
                String id = String.valueOf(identifier);
                String costPerUnitStr = String.valueOf(costPerUnit);
                String[] array = {id, name, costPerUnitStr};

                // Prepare the entry in the format {dfff,frfrf,refr}
                StringBuilder entry = new StringBuilder("{");
                for (int i = 0; i < array.length; i++) {
                    entry.append(array[i]);
                    if (i < array.length - 1) {
                        entry.append(",");
                    }
                }
                entry.append("}");

                writer.write(entry.toString());

                // Add a comma and newline only if the file is not empty
                writer.newLine();
                writer.write(",");

                System.out.println("Excellent Entering Data!");
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static void main(String[] args) {
        Resource resource = new Resource(); 
        Resource.HumanResources hr = resource.new HumanResources();
        Resource.Logistics lg = resource.new Logistics();
        Resource.Matricielle mt = resource.new Matricielle();
    
        List<String> entries = mt.getMatriciele();
        for (String entry : entries) {
            System.out.println(entry);
        }
    
    }
}

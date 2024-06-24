import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Resource {
    static int next = 1;
    int d;
    private int identifier;
    private String name;
    private double costPerUnit;

    public Resource(String name, double costPerUnit) {
        identifier = next++;
        d=identifier;
        this.name = name;
        this.costPerUnit = costPerUnit;
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

        public Set<String> getLogistics() {
            File logisticsFile = new File("logistics.txt");
            Set<String> lines = new HashSet<>();
            try {
                if (logisticsFile.exists()) {
                    BufferedReader fileReader = new BufferedReader(new FileReader(logisticsFile));
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        lines.add(line);
                    }
                    fileReader.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            return lines;
        }

        public double logisticsCost(String name) {
            Set<String> lines = getLogistics();
            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[1].equals(name)) {
                    return Double.parseDouble(parts[2]);
                }
            }
            return 0.0;
        }

        public void addLog(String name, double cost) {
            File logisticsFile = new File("logistics.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logisticsFile, true))) {
                if (logisticsFile.exists() && logisticsFile.length() != 0) {
                    writer.newLine();
                }
                
                writer.write(d + ", " + name + ", " + cost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void updateLog(int identifier, String name, double cost) {
            Path path = Paths.get("logistics.txt");
            Set<String> lines = getLogistics();
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(String.valueOf(identifier))) {
                    parts[1] = name;
                    parts[2] = String.valueOf(cost);
                    found = true;
                }
                updatedContent.append(String.join(", ", parts)).append("\n");
            }

            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                    writer.write(updatedContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entry not found for updating.");
            }
        }
    }

    public static class Matrielle extends Resource {
        public Matrielle(String name, double unitCost) {
            super(name, unitCost);
        }

        public Set<String> getMat() {
            File matFile = new File("Mat.txt");
            Set<String> lines = new HashSet<>();
            try {
                if (matFile.exists()) {
                    BufferedReader fileReader = new BufferedReader(new FileReader(matFile));
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        lines.add(line);
                    }
                    fileReader.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            return lines;
        }

        public double matCost(String name, double quantity) {
            Set<String> lines = getMat();
            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[1].equals(name)) {
                    return Double.parseDouble(parts[2]) * quantity;
                }
            }
            return 0.0;
        }

        public void addMat(String name, double cost) {
            File matFile = new File("Mat.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(matFile, true))) {
                if (matFile.exists() && matFile.length() != 0) {
                    writer.newLine();
                }
                writer.write(d + ", " + name + ", " + cost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateMat(int identifier, String name, double cost) {
            Path path = Paths.get("Mat.txt");
            Set<String> lines = getMat();
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(String.valueOf(identifier))) {
                    parts[1] = name;
                    parts[2] = String.valueOf(cost);
                    found = true;
                }
                updatedContent.append(String.join(", ", parts)).append("\n");
            }

            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                    writer.write(updatedContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entry not found for updating.");
            }
        }
    }

    public static class HumanRes extends Resource {
        public HumanRes(String name, double cost) {
            super(name, cost);
        }

        public Set<String> getHumans() {
            File humansFile = new File("humans.txt");
            Set<String> lines = new HashSet<>();
            try {
                if (humansFile.exists()) {
                    BufferedReader fileReader = new BufferedReader(new FileReader(humansFile));
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        lines.add(line);
                    }
                    fileReader.close();
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
            return lines;
        }

        public double employeeCost(String name, double workingHours) {
            Set<String> lines = getHumans();
            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[1].equals(name)) {
                    return Double.parseDouble(parts[2]) * workingHours;
                }
            }
            return 0.0;
        }

        public void addHumanR(String name, double cost) {
            File humansFile = new File("humans.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(humansFile, true))) {
                if (humansFile.exists() && humansFile.length() != 0) {
                    writer.newLine();
                }
                writer.write(d+", " + name + ", " + cost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateHuman(int identifier, String name, double cost) {
            Path path = Paths.get("humans.txt");
            Set<String> lines = getHumans();
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;

            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(String.valueOf(identifier))) {
                    parts[1] = name;
                    parts[2] = String.valueOf(cost);
                    found = true;
                }
                updatedContent.append(String.join(", ", parts)).append("\n");
            }

            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                    writer.write(updatedContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entry not found for updating.");
            }
        }
    }

    public static void main(String[] args) {
        Logistics logistics = new Logistics("SampleItem", 10.0);
        logistics.addLog("SampleItem1", 15.0);
        logistics.addLog("SampleItem2", 25.0);

        logistics.updateLog(1, "UpdatedSampleItem", 20.0);

        double cost = logistics.logisticsCost("SampleItem2");
        System.out.println("Logistics cost for SampleItem2: " + cost);

        Set<String> logEntries = logistics.getLogistics();
        System.out.println("Logistics entries:");
        for (String entry : logEntries) {
            System.out.println(entry);
        }
    }

}

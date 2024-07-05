import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Resource extends ObservableEntity implements Serializable,Comparable<Resource>{
    static int next = 1;
    int d;
    private int identifier;
    private String name;
    protected double unitCost;

    public Resource(String name,double unitCost) {
        identifier = next++;
        d=identifier;
        this.name = name;
        this.unitCost = unitCost;
    }

    public String getName() {
        return this.name;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double totalResourceCost(double matCost, double humanCost, double logisticCost) {
        return matCost + humanCost + logisticCost;
    }
    
 
   public int compareTo(Resource r) {
	   return this.identifier-r.identifier;
   }
    public static class Logistics extends Resource {
        public Logistics(String name, double logisticCost) {
            super(name, logisticCost);
        }
       
        public Set<String> getLogistics() {
            File logisticsFile = new File("logistics.dat");
            Set<String> lines = new HashSet<>();
            try {
          	    if (logisticsFile.exists()) {
          	        DataInputStream fileReader = new DataInputStream(new FileInputStream(logisticsFile));
          	        int character;
          	        String line = "";
          	        while ((character = fileReader.read()) != -1) {
          	            if (character == '\n') {
          	                lines.add(line);
          	                line = "";
          	            } else {
          	                line += (char) character;
          	            }
          	        }
          	        // Add the last line if the file doesn't end with a new line
          	        if (!line.isEmpty()) {
          	            lines.add(line);
          	        }
          	        fileReader.close();
          	    }
          	} catch (IOException io) {
          	    io.printStackTrace();
          	}
          	return lines;
  	  }

        public double logisticsCost(Resource r) {
            return r.unitCost;
        }

        public void addLog(String name, double cost) {
       	// File matFile = new File("Logistics.txt");
            
            try {
            DataOutputStream writer = new DataOutputStream(new FileOutputStream("Logistics.dat",true)); 
                writer.writeBytes(d + ", " + name + ", " + cost+"\n");
                writer.flush();
                writer.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
   	    }
    
        public void updateLog(int identifier, String name, double cost) {
            Path path = Paths.get("logistics.dat");
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
    
        private double quantity;

        public Matrielle(String name, double unitCost, int q) {
            super(name, unitCost);
            this.quantity = q;
        }

        public double matCost(Resource.Matrielle r) {
            return unitCost * quantity; 
        }
        public Set<String> getMat() {
            File matFile = new File("Mat.dat");
            Set<String> lines = new HashSet<>();
            try {
                if (matFile.exists()) {
                	DataInputStream fileReader = new DataInputStream(new FileInputStream(matFile));
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

        
        public double getQuantity(Resource.Matrielle r) {
            return (r.quantity);
        }
        public void addMat(String name, double cost) {
            File matFile = new File("Mat.dat");
            try {  DataOutputStream writer = new DataOutputStream(new FileOutputStream("Mat.dat",true)); 
                    writer.writeBytes(d + ", " + name + ", " + cost+"\n");
                    writer.flush();
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateMat(int identifier, String name, double cost) {
            Path path = Paths.get("Mat.dat");
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
  
        private double quantity;
        String speciality;
        String function;
        public HumanRes(String name, double unitCost,String speciality,String function, int q) {
            super(name, unitCost);
            this.quantity = q;
            this.function=function;
            this.speciality=speciality;
        }

        public double employeeCost() {
            return unitCost * quantity;
        }

        public Set<String> getHumans() {
            File humansFile = new File("humans.dat");
            Set<String> lines = new HashSet<>();
            try {
                if (humansFile.exists()) {
                	DataInputStream fileReader = new DataInputStream(new FileInputStream(humansFile));
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

       

        public void addHumanR(String name, double cost,String speciality,String function) {
           // File humansFile = new File("humans.dat");
            try { DataOutputStream writer = new DataOutputStream(new FileOutputStream("humans.dat",true)); 
                    writer.writeBytes(d + ", " + name + ", " + cost+","+speciality+","+function+"\n");
                    writer.flush();
                    writer.close();
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

        double cost = logistics.logisticsCost(logistics);
        System.out.println("Logistics cost for SampleItem2: " + cost);

        Set<String> logEntries = logistics.getLogistics();
        System.out.println("Logistics entries:");
        for (String entry : logEntries) {
            System.out.println(entry);
        }
    }

}

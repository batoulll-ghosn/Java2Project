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
        public void addMat(String name, double cost,int stock) {
           // File matFile = new File("Mat.dat");
            try {  DataOutputStream writer = new DataOutputStream(new FileOutputStream("Mat.dat",true)); 
                    writer.writeBytes(d + ", " + name + ", " + cost+","+stock+"\n");
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
        private double wh;
        static int next = 1;
        int d;
        private int identifier;
        public HumanRes(String name, double unitCost,String speciality,String function, int q,double whh) {
            super(name, unitCost);
            this.identifier=next++;
            this.quantity = q;
            this.function=function;
            this.speciality=speciality;
            this.wh=whh;
          
        }

        public double employeeCost() {
            return unitCost * quantity;
        }
        public double workingHours() {
            return this.wh;
        }
        public String getFunction() {
            return this.function;
        }
        public String getSpeciality() {
            return this.speciality;
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

       

        public void addHumanR(String name, double cost,String speciality,String function,double workinghours) {
           // File humansFile = new File("humans.dat");
            try { DataOutputStream writer = new DataOutputStream(new FileOutputStream("humans.dat",true)); 
                    writer.writeBytes(identifier + ", " + name + ", " + cost+","+speciality+","+function+","+workinghours+"\n");
                    writer.flush();
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void setWorkingHours(double newWorkingHours) {
            this.wh = newWorkingHours;
        }
        private Resource.HumanRes findHumanByName(Set<Resource.HumanRes> humSet, String name) {
            for (Resource.HumanRes human : humSet) {
                if (human.getName().equals(name)) {
                    return human;
                }
            }
            return null;
        }
        public int getIdentifier(){
            return this.identifier;
        }
        public void updateResourceWorkingHours(Resource.HumanRes resource, double newWorkingHours) {
            resource.setWorkingHours(newWorkingHours);
        }
        public void updateHuman(int identifier, String name, double cost, String function, String speciality, double wh) {
            File humansFile = new File("humans.dat");
            if (!humansFile.exists()) {
                System.out.println("File not found.");
                return;
            }
        
            Set<String> lines = getHumans();
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;
        
            for (String line : lines) {
                String[] parts = line.split(", ");
                if (parts.length == 6 && Integer.parseInt(parts[0]) == identifier) {
                    parts[1] = name;
                    parts[2] = String.valueOf(cost);
                    parts[3] = function;
                    parts[4] = speciality;
                    parts[5] = String.valueOf(wh);
                    found = true;
                }
                updatedContent.append(String.join(", ", parts)).append("\n");
            }
        
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(humansFile))) {
                    writer.write(updatedContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Entry not found for updating.");
            }
        }
        

        public static void main(String[] args) {
            HumanRes human1 = new HumanRes("John Doe", 50.0, "Engineer", "Development", 5, 40.0);
            HumanRes human2 = new HumanRes("Jane Doe", 60.0, "Manager", "Operations", 3, 35.0);
        
            human1.addHumanR("John Doe", 50.0, "Engineer", "Development", 40.0);
            human2.addHumanR("Jane Doe", 60.0, "Manager", "Operations", 35.0);
        
            System.out.println("Before update:");
            Set<String> humans = human1.getHumans();
            for (String human : humans) {
                System.out.println(human);
            }
        
            human1.updateHuman(1, "John Smith", 55.0, "Senior Engineer", "R&D", 45.0);
        
            System.out.println("After update:");
            humans = human1.getHumans();
            for (String human : humans) {
                System.out.println(human);
            }
        }

    }}
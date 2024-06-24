import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
public class Res {
    private int identifier;
    private String name;
    private double costPerUnit;
    
    public Res(String name, double costPerUnit) {
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
    public static class Logistics extends Res {
    	  public Logistics(String name, double logisticCost) {
              super(name, logisticCost);
          }
      
    	  public Set<String> getLogistics() {
              File logisticsFile = new File("logistics.txt");
              Set<String> lines = new HashSet<>();
              try {
            	    if (logisticsFile.exists()) {
            	        FileReader fileReader = new FileReader(logisticsFile);
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

      public double logisticsCost(String name) {
          Set<String> lines = getLogistics();
          for (String line : lines) {
              String[] parts = line.split(", ");
              if (parts.length == 3 && parts[1].equals(name)) {
                  return Double.parseDouble(parts[2]);
              }
          }
          return 0.0; // or throw an exception if the name is not found
      }
      public void addlog(String name, double cost) {
    	 
    	   	 int newIdentifier = 1;
    	   	File logisticsFile = new File("logistics.txt");
    	    Path path = Paths.get("logistics.txt");
    	    try {
                if (Files.exists(path)) {
                	int k= (int)Files.lines(path).count();
                    newIdentifier = k+1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    	        try{
    	        	String A;
    	        	int k= (int)Files.lines(path).count();
    	        	if(k==0) {A="";}
    	        	else{A="\n";}
    	        	 String id = String.valueOf(newIdentifier);
    	    	        String costPerUnitStr = String.valueOf(cost);
    	    	       Writer logWrite=new FileWriter(logisticsFile,true);
    	     	        A+=id+", "+name+", "+costPerUnitStr;
    	               logWrite.write(A);
    	               logWrite.flush();
                       logWrite.close();
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
              try {
            	  String A="";
            	   	File logisticsFile = new File("logistics.txt");
            	   Writer logWrite=new FileWriter(logisticsFile,true);
            	   A+=updatedContent;
            	   logWrite.write(A);
	               logWrite.flush();
                   logWrite.close();
                   
                  
              } catch (IOException e) {
                  e.printStackTrace();
              }
          } else {
              System.out.println("Entry not found for updating.");
          }
      }
    }
    public static class Matrielle extends Res {
  	  public Matrielle(String name, double unitCost) {
            super(name, unitCost);
        }
    
  	  public Set<String> getMat() {
            File logisticsFile = new File("Mat.txt");
            Set<String> lines = new HashSet<>();
            try {
                if (logisticsFile.exists()) {
                    FileReader fileReader = new FileReader(logisticsFile);
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

    public double matCost(String name,double quantity) {
        Set<String> lines = getMat();
        for (String line : lines) {
            String[] parts = line.split(", ");
            if (parts.length == 3 && parts[1].equals(name)) {
            	double x=Double.parseDouble(parts[2]);
                return x*quantity;
            }
        }
        return 0.0; 
    }
    public void addMat(String name, double cost) {
  	 
  	   	 int newIdentifier = 1;
  	   	File MatFile = new File("Mat.txt");
  	    Path path = Paths.get("Mat.txt");
  	    try {
              if (Files.exists(path)) {
              	int k= (int)Files.lines(path).count();
                  newIdentifier = k+1;
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
  	        try{
  	        	String A;
  	        	int k= (int)Files.lines(path).count();
  	        	if(k==0) {A="";}
  	        	else{A="\n";}
  	        	 String id = String.valueOf(newIdentifier);
  	    	        String costPerUnitStr = String.valueOf(cost);
  	    	       Writer logWrite=new FileWriter(MatFile,true);
  	     	        A+=id+", "+name+", "+costPerUnitStr;
  	               logWrite.write(A);
  	               logWrite.flush();
                     logWrite.close();
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
            try {
          	  String A="";
          	   	File logisticsFile = new File("logistics.txt");
          	   Writer logWrite=new FileWriter(logisticsFile,true);
          	   A+=updatedContent;
          	   logWrite.write(A);
	               logWrite.flush();
                 logWrite.close();
                 
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Entry not found for updating.");
        }
    }
    }
    public static class HumanRes extends Res {
  	  public HumanRes(String name,String speciality,String function, double Cost) {
            super(name,Cost);
        }
    
  	  public Set<String> getHumans() {
            File logisticsFile = new File("humans.txt");
            Set<String> lines = new HashSet<>();
            try {
          	    if (logisticsFile.exists()) {
          	        FileReader fileReader = new FileReader(logisticsFile);
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

    public double EmployeeCost(String name,double workingHours) {
        Set<String> lines = getHumans();
        for (String line : lines) {
            String[] parts = line.split(", ");
            if (parts.length == 3 && parts[1].equals(name)) {
            	double H=Double.parseDouble(parts[2]);
                return H*workingHours;
            }
        }
        return 0.0; // or throw an exception if the name is not found
    }
    public void addHumanR(String name, double cost) {
  	 
  	   	 int newIdentifier = 1;
  	   	File logisticsFile = new File("humans.txt");
  	    Path path = Paths.get("humans.txt");
  	    try {
              if (Files.exists(path)) {
              	int k= (int)Files.lines(path).count();
                  newIdentifier = k+1;
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
  	        try{
  	        	String A;
  	        	int k= (int)Files.lines(path).count();
  	        	if(k==0) {A="";}
  	        	else{A="\n";}
  	        	 String id = String.valueOf(newIdentifier);
  	    	        String costPerUnitStr = String.valueOf(cost);
  	    	       Writer logWrite=new FileWriter(logisticsFile,true);
  	     	        A+=id+", "+name+", "+costPerUnitStr;
  	               logWrite.write(A);
  	               logWrite.flush();
                     logWrite.close();
  	        } catch (IOException e) {
  	            e.printStackTrace();
  	        }
  	    }
    public void updateLog(int identifier, String name, double cost) {
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
            try {
          	  String A="";
          	   	File logisticsFile = new File("humans.txt");
          	   Writer logWrite=new FileWriter(logisticsFile,true);
          	   A+=updatedContent;
          	   logWrite.write(A);
	               logWrite.flush();
                 logWrite.close();
                 
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Entry not found for updating.");
        }
    }
  }
    public static void main (String[] args) {
    	Logistics logistics = new Logistics("", 0.0);
    	Logistics logistics1 = new Logistics("", 0.0);
    	 logistics.updateLog(1, "UpdatedSampleItemBBB", 20.0);
    	double K= logistics1.logisticsCost("SampleItem2");
         // Read and print the log entries
         Set logEntries = logistics.getLogistics();
         System.out.println(K);
    }
}

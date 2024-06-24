import java.io.*;
import java.util.*;

public class Process implements Serializable, Comparable<Process> {
    private static int next = 1;
    private int identifier;
    private String name;
    private Resource resource; 
    private TreeMap<String,TreeSet<Resource>> rscMap;  
    private double cost;
    private String state;
    private int duration;
    private static Map<Integer, Process> processMap = new TreeMap<>();
    private static final String FILE_NAME = "process.txt";
    public Process(String name,TreeMap<String,TreeSet<Resource>> rsc,double cost,String state, int duration) {
        this.identifier = next++;
        this.name = name;
        this.state = state;
        this.duration = duration;
        this.rscMap=rsc;
        this.cost=cost;
        
    }
    public int getIdentifier() {
        return identifier;
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
        return name;
    }
    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.identifier, other.identifier);
    }
    @Override
    public String toString() {
        return identifier + "," + name + "," + cost + "," + state + "," + duration;
    }

    public static void addProcess(Process process) {
        try {
            loadProcessesFromFile();
            processMap.put(process.getIdentifier(), process);
            saveProcessesToFile();
            System.out.println("Process added successfully!");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add process.");
        }
    }


    public static Set<Process> getProcess() {
        loadProcessesFromFile();
        return new TreeSet<>(processMap.values());
    }

    private static void saveProcessesToFile() {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("process.txt"))) {
            os.writeObject(processMap);
        }  catch(EOFException e) {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadProcessesFromFile() {
        File file = new File("process.txt");
        if (!file.exists()) {
            return; // File doesn't exist, nothing to load
        }

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("process.txt"))) {
            Object obj = is.readObject();
            if (obj instanceof Map) {
                processMap = (Map<Integer, Process>) obj;
            }
        } catch(EOFException e) {}
        catch (FileNotFoundException nfne) {
            System.out.println("YOUR FILE IS NOT FOUND!!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
  //  public static void updateProcess(int identifier, Process updatedProcess) {
     //   try (RandomAccessFile file = new RandomAccessFile("process.txt", "rw")) {
       //     long currentPosition = 0;
       //     while (currentPosition < file.length()) {
          //      long position = file.getFilePointer();
           //     Process process = (Process) new ObjectInputStream(new FileInputStream("process.txt")).readObject();
           //     if (process.getIdentifier() == identifier) {
               //     file.seek(position);
              //      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temp.txt"))) {
                //        oos.writeObject(updatedProcess);
                //        file.writeUTF("temp.txt");
                   //     System.out.println("Process updated successfully!");
                  //  }
                 //   return;
              //  }
            // //   currentPosition = file.getFilePointer();
          //  }
       // } catch (IOException | ClassNotFoundException e) {
       //     e.printStackTrace();
       // }
      //  System.out.println("Process not found!");
    //}

    public static void main(String[] args) {
        Set<Process> processes = getProcess();
        for (Process process : processes) {
            System.out.println(process);
        }
        processes = getProcess();
        for (Process process : processes) {
            System.out.println(process);
        }
    }
}

class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // Do not write a header
        reset();
    }
}

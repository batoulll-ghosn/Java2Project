import java.io.*;
import java.util.*;

public class Process implements Serializable, Comparable<Process> {
    private static final long serialVersionUID = 1L;
    private static int next = 1;
    private int identifier;
    private String name;
    private Resource resource;
    private TreeMap<String, TreeSet<?>> rscMap;
    private double cost;
    private String state;
    private int duration;
    private static TreeSet<Process> processSet = new TreeSet<>();
    private static final String FILE_NAME = "process.txt";

    public Process(String name, TreeMap<String, TreeSet<?>> rsc,double cost, String state, int duration) {
        this.identifier = next++;
        this.name = name;
        this.state = state;
        this.duration = duration;
       this.rscMap = rsc;
        this.cost = cost;
    }
  
    public int getIdentifier() {
        return identifier;
    }

    public double getCost() {
        return this.cost;
    }
    public double getCostProcess(Process p) {
        return this.cost;
    }
    public String getState() {
        return state;
    }
    public TreeMap<String, TreeSet<?>> getResources() {
        return rscMap;
    }
    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public TreeMap<String, TreeSet<?>> getResource() {
        return rscMap;
    }

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.identifier, other.identifier);
    }

    @Override
    public String toString() {
        return name + "," + cost + "," + state + "," + duration;
    }

    public static void addProcess(Process process) {
        boolean fileExists = new File(FILE_NAME).exists();
        try (ObjectOutputStream os = fileExists
                ? new AppendableObjectOutputStream(new FileOutputStream(FILE_NAME, true))
                : new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            os.writeObject(process);
            System.out.println("Process added successfully!");
        } catch (NotSerializableException n) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Process> getProcess() {
        Set<Process> myProcesses = new TreeSet<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("The file does not exist.");
            return myProcesses;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Process process = (Process) ois.readObject();
                    myProcesses.add(process);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (InvalidClassException c) {
            System.out.println("Invalid class exception occurred. Ensure the Process class has not changed and has a proper serialVersionUID.");
            c.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class Not Found Exception");
            cnfe.printStackTrace();
        } catch (FileNotFoundException nfne) {
            System.out.println("YOUR FILE IS NOT FOUND!!");
            nfne.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myProcesses;
    }

    public static void main(String[] args) {
     


        Set<Process> processes = getProcess();
        for (Process p : processes) {
            System.out.println(p);
        }
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

    


class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    
}

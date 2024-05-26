package Java3Project;
public class Resource {
	private int idetifier;
	public double TotalResourceCost(double matCost , double HumanCost, double logisticCost) {
          double totalCostResource = matCost + HumanCost+ logisticCost;
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
	public class HumanResources extends Resource{
		String speciality;
		String function;
		double costPerHour;
		String Name;
		public double CalculCostHum(double numberOfWorkingHours, String[] func) {
    double costPerHour = 0.00; 
    for (int i=0; i<4 ;i++) {
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
	public class Matricielle extends Resource{
	String Name;
	double costPerUnit;
	public double CalculCostMat(double quantity, String Name) {
		double TotalCost=0.00;
		if (Name.equalsIgnoreCase("plastic")) {
			TotalCost+=1.00*quantity;
		}
		if (Name.equalsIgnoreCase("iron")) {
			TotalCost+=2.00*quantity;
		}
		if (Name.equalsIgnoreCase("Glass")) {
			TotalCost+=4.00*quantity;
		}
		return TotalCost;
	}
	}	
	}

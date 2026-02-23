package vehicleTypes;

// Bike class inherits from Vehicles class
public class Bike extends Vehicles {

    //Additional attribute in Bike
   private final int engineCapacityCC;

    //Constructor
    public Bike(String vehicleID, String brand, String model, double baseRatePerDay,
                boolean isAvailable, int engineCapacityCC){

        //Call parent class constructor
        super(vehicleID, brand, model, baseRatePerDay, isAvailable);
        // Assign Engine Capacity (CC)
        this.engineCapacityCC = engineCapacityCC;
    }

    //Getter method to return Engine Capacity (CC)
    public int getEngineCapacityCC() {
        return engineCapacityCC;
    }


    //Method to calculate rental for Bike
    @Override
    public double calculateRentalCost(int days){
        return getBaseRatePerDay() * days + (engineCapacityCC * 0.5 * days);
    }

    //Method to convert object data into file format String
    @Override
    public String toFileString(){
        return "Bike|" +getVehicleID() + "|"+getBrand()+"|"+getModel()+"|"
                +getBaseRatePerDay()+"|"+getIsAvailable()+"|"+getEngineCapacityCC();
    }

    //Override to display engine capacity
    @Override
    public void displayDetails(){
        super.displayDetails();
        System.out.println("Engine capacity: " + engineCapacityCC + " CC");

    }
}
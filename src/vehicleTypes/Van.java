package vehicleTypes;

// Van class inherits from Vehicles class
public class Van extends Vehicles {

    //Additional attribute in Van
    private final double cargoCapacityKg;

    //Constructor
    public Van(String vehicleID, String brand, String model, double baseRatePerDay,
               boolean isAvailable, double cargoCapacityKg){

        //Call parent class constructor
        super(vehicleID, brand, model, baseRatePerDay, isAvailable);
        // Assign Cargo Capacity (Kg)
        this.cargoCapacityKg = cargoCapacityKg;
    }

    //Getter method to return Cargo Capacity (Kg)
    public double getCargoCapacityKg() {
        return cargoCapacityKg;
    }


    //Method to calculate rental for Van
    @Override
    public double calculateRentalCost(int days){
        return getBaseRatePerDay() * days + (cargoCapacityKg * 0.2 * days);
    }

    //Method to convert object data into file format String
    @Override
    public String toFileString(){
        return "Van|" +getVehicleID() + "|"+getBrand()+"|"+getModel()
                +"|"+getBaseRatePerDay() +"|"+getIsAvailable()+"|"+getCargoCapacityKg();
    }

    //Override to display cargo capacity
    @Override
    public void displayDetails(){
        super.displayDetails();
        System.out.println("Cargo Capacity: " + cargoCapacityKg + " Kg");

    }
}

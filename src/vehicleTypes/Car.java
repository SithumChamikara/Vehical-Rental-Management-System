package vehicleTypes;

// Car class inherits from Vehicles class
public class Car extends Vehicles {

    //Additional attribute in Car
   private final int numberOfSeats;

    //Constructor
    public Car(String vehicleID, String brand, String model, double baseRatePerDay,
               boolean isAvailable, int numberOfSeats){

        //Call parent class constructor
        super(vehicleID, brand, model, baseRatePerDay, isAvailable);
        // Assign number of seats
        this.numberOfSeats = numberOfSeats;
    }
    //Getter method to return number of seats
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    //Method to calculate rental for Car
    @Override
    public double calculateRentalCost(int days){
        return getBaseRatePerDay() * days + (numberOfSeats * 200 * days);
    }

    //Method to convert object data into file format String
    @Override
    public String toFileString(){
        return "Car|" +getVehicleID() + "|"+getBrand()+"|"+getModel()+"|"
                +getBaseRatePerDay()+"|"+getIsAvailable()+"|"+getNumberOfSeats();
    }

    ////Override to display number of seats
    @Override
    public void displayDetails(){
        super.displayDetails();
        System.out.println("Number of seats: " + numberOfSeats);

    }
}
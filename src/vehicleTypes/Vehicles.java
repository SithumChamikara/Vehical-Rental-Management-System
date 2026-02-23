package vehicleTypes;

// Abstract class representing a general vehicle
public abstract class Vehicles {

    //Attributes of a vehicle
    private final String vehicleID;
    private final String brand;
    private final String model;
    private final double baseRatePerDay;
    private boolean isAvailable;

    // Constructor to initialize vehicle details
    public Vehicles(String vehicleID, String brand, String model,
                    double baseRatePerDay, boolean isAvailable) {

        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.baseRatePerDay = baseRatePerDay;
        this.isAvailable = isAvailable;
    }

    // Getter methods to access vehicle data
    public String getVehicleID() {return vehicleID;}
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public double getBaseRatePerDay() {
        return baseRatePerDay;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    // Method to display vehicle details
    public void displayDetails(){
        System.out.println("Vehicle ID: " + vehicleID);
        System.out.println("Vehicle Brand: "+ brand);
        System.out.println("Vehicle Model: "+ model);
        System.out.println("Base Rate Per Day: "+baseRatePerDay);
        System.out.println("Availability: "+(isAvailable ? "Available":"Not available"));
    }

    // Method to rent the vehicle (functioning availability)
    public void rentVehicle(){
        if (isAvailable){
            isAvailable = false;
            System.out.println("Vehicle Rented.");
        }else{
            System.out.println("This vehicle is not available.");
        }
    }

    // Method  to return the vehicle (Change it as available again)
    public void returnVehicle(){
        isAvailable = true;
        System.out.println("Vehicle Returned Successfully.");
    }

    // Method to convert object data to file format (Override in subclasses)
    public String toFileString() {
        return "";
    }

    // Abstract method to calculate rental cost (Implemented in subclasses)
    public abstract double calculateRentalCost(int days);
}
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Import parent class and their classes
import vehicleTypes.Car;
import vehicleTypes.Bike;
import vehicleTypes.Van;
import vehicleTypes.Vehicles;

// This is the main class of the Vehicle Rental Management System,
public class RentalApp {

    // ArrayList to store all vehicle objects
    static ArrayList<Vehicles> vehicles = new ArrayList<>();

    // Creating a variable to track rental income
    static double totalRentalIncome = 0;

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Load saved data from vehicle.txt file at program start
        loadData();

        // Infinite loop to display menu continuously
        while (true) {
            System.out.println("\n===== Vehicle Rental Management System =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Search Vehicle by ID");
            System.out.println("6. View Total Rental Income");
            System.out.println("7. Exit");

            try {
                System.out.println("----------------------------------------------------------------");
                int choice = getValidInt("Enter your choice (Input 1-7 number according to your choice): ");// clear buffer
                if (choice == -999) continue;

                switch (choice) {
                    case 1:
                        addVehicle();
                        break;
                    case 2:
                        viewAllVehicle();
                        break;
                    case 3:
                        rentVehicle();
                        break;
                    case 4:
                        returnVehicle();
                        break;
                    case 5:
                        searchVehicle();
                        break;
                    case 6:
                        System.out.print("Total Rental Income: " + totalRentalIncome);
                        break;
                    case 7:
                        saveData(); // save data before exit
                        System.out.println("Thank you! Have a nice day.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid menu selection. Please try again.");

                }
            } catch (Exception e) {
                // Handle invalid menu selection
                System.out.println("Invalid input. Please enter a valid input.");
                scanner.nextLine();
            }
        }
    }

    // Method to add a new vehicle
    static void addVehicle(){
        System.out.println("----------------------------------------------------------------");

        // Get vehicle ID (Take vehicle register number as vehicle ID)
        String vehicleID = getValidString("Enter Vehicle ID (Ex.: XX-1111): ");
        if (vehicleID == null) return;

        // Check vehicle ID if it is already exists (To prevent duplicate ID)
        if (findByVehicleID(vehicleID) != null)  {
            System.out.print("Vehicle ID: " + vehicleID + " already exists.");
            return;
        }

        // Get validate inputs (Brand, Model, Base Rate Per Day) using custom method
        String brand = getValidString("Enter Brand (Ex.: Honda, Toyota): ");
        if (brand == null) return;

        String model = getValidString("Enter Model (Ex.: Dio, Alto, HiAce):");
        if (model == null) return;

        double baseRatePerDay = getValidDouble("Enter Base Rate Per Day (Rs.)): ");
        if (baseRatePerDay == -999) return;

        //Select Vehicle Type
        int type = getValidInt("Select Vehicle Type: 1.Car  2.Bike  3.Van (Input 1 or 2 or 3): ");
        if (type == -999) return;


        // Create vehicle object based on selected type
        switch (type){
            case 1:

                int numberOfSeats = getValidInt("Enter Number of Seats: ");
                if (numberOfSeats == -999) return;
                vehicles.add(new Car(vehicleID, brand, model, baseRatePerDay, true, numberOfSeats));
                break;

            case 2:

                int engineCapacityCC = getValidInt("Enter Engine Capacity (CC): ");
                if (engineCapacityCC == -999) return;
                vehicles.add(new Bike(vehicleID, brand, model, baseRatePerDay, true, engineCapacityCC));
                break;

            case 3:

                double cargoCapacityKg = getValidInt("Enter Cargo Capacity (Kg): ");
                if (cargoCapacityKg  == -999) return;
                vehicles.add(new Van(vehicleID, brand, model, baseRatePerDay, true, cargoCapacityKg));
                break;

            default:
                System.out.print("Invalid vehicle type.");
                return;
        }
        saveData();
        System.out.println("Vehicle added successfully.");
    }

    // Display all vehicle in the system
    static void viewAllVehicle(){
        if (vehicles.isEmpty()){
            System.out.print("Not available.");
            return;
        }
        for (Vehicles vehicle : vehicles){
            vehicle.displayDetails();
            System.out.println("------------------------------");
        }
    }

    // Rent a vehicle and calculate rental cost using polymorphism
    static void rentVehicle(){
        String vehicleID = getValidString("Enter Vehicle ID (Ex.: XX-1111): ");
        if (vehicleID == null) return;

        Vehicles vehicle = findByVehicleID(vehicleID);

        if (vehicle == null){
            System.out.println("Vehicle not found.");
            return;
        }
        if (!vehicle.getIsAvailable()){
            System.out.println("Vehicle is already rented.");
            return;
        }

        int days = getValidInt("Enter number of rental days: ");

        if (days <= 0){
            System.out.println("Rental days must be grater than zero.");
        }else {

            double cost = vehicle.calculateRentalCost(days); //Polymorphism
            System.out.println("Rental Cost: " + cost);

            vehicle.rentVehicle();
            saveData();
        }
    }

    // Return a rented vehicle
    static void returnVehicle(){
        String vehicleID = getValidString("Enter Vehicle ID (Ex.: XX-1111): ");
        if (vehicleID == null) return;

        Vehicles vehicle = findByVehicleID(vehicleID);

        if(vehicle == null){
            System.out.println("Vehicle not found.");
            return;
        }
        vehicle.returnVehicle();
        saveData();
    }

    // Search and display a vehicle by ID
    static void searchVehicle(){

        String vehicleID = getValidString("Enter Vehicle ID (Ex.: XX-1111): ");
        if (vehicleID == null) return;

        Vehicles vehicle = findByVehicleID(vehicleID);

        if (vehicle == null){
            System.out.println("Vehicle not found.");
        }else{
            vehicle.displayDetails();
        }
    }

    // Find vehicle object using vehicle ID
    private static Vehicles findByVehicleID(String vehicleID){
        for (Vehicles vehicleId : vehicles) {
            if (vehicleId.getVehicleID().equalsIgnoreCase(vehicleID)) {
                return vehicleId;
            }
        }
        return null;
    }

    // Load saved vehicle data from file
    static void loadData(){
        try{
            File file = new File("vehicles.txt");
            if(!file.exists())return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null){
                String[] data = line.split("\\|");

                if (data[0].equals("INCOME")){
                    totalRentalIncome = Double.parseDouble(data[1]);
                    continue;
                }
                if(data[0].equals("Car")){
                    vehicles.add(new Car(
                            data[1], data[2], data[3],
                            Double.parseDouble(data[4]),
                            Boolean.parseBoolean(data[5]),
                            Integer.parseInt(data[6])
                    ));
                }else if(data[0].equals("Bike")) {
                    vehicles.add(new Bike(
                            data[1], data[2], data[3],
                            Double.parseDouble(data[4]),
                            Boolean.parseBoolean(data[5]),
                            Integer.parseInt(data[6])
                    ));
                }else if(data[0].equals("Van")) {
                    vehicles.add(new Van(
                            data[1], data[2], data[3],
                            Double.parseDouble(data[4]),
                            Boolean.parseBoolean(data[5]),
                            Double.parseDouble(data[6])
                    ));
                }
            }
            br.close();
        }catch (IOException e){
            System.out.println("Error loading file.");
        }
    }

    // save all vehicle data and income to vehicle.txt file
    static void saveData(){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("vehicles.txt"));

            for(Vehicles vehicle : vehicles){
                pw.println(vehicle.toFileString());
            }

            pw.println("INCOME|" + totalRentalIncome);
            pw.close();
        }catch (IOException e){
            System.out.println("Error saving file.");
        }
    }

    /*
    * -------------------------------
    * Custom Input Validation Methods
    * (Implemented by our team)
    * -------------------------------
     */

    // Validate integer input
    static int getValidInt(String message){
        int attempts = 0; // Count number of invalid attempts

        // Allow user to try only 3 times
        while (attempts < 3) {

            // Display input message
            System.out.print(message);

            // Read input from user
            String input = scanner.nextLine();

            // Check if input is empty
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty! Try again.");
                attempts++; // Increase attempt count

            } else {
                // Check input type validation through try-catch
                try {
                    int value = Integer.parseInt(input);
                    return value;
                }catch(NumberFormatException e){
                    System.out.println("Invalid number! Try again.");
                    attempts++; // Increase attempt count
                }
            }
        }
        // After 3 invalid attempts, return to main menu
        System.out.println("Too many invalid attempts. Returning to Main Menu.");
        return -999; // return -999 to indicate failure
    }

    // Validate double input
    static double getValidDouble(String message){
        int attempts = 0; // Count number of invalid attempts

        // Allow user to try only 3 times
        while (attempts < 3) {

            // Display input message
            System.out.print(message);

            // Read input from user
            String input = scanner.nextLine();

            // Check if input is empty
            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty! Try again.");
                attempts++; // Increase attempt count
            } else {
                // Check input type validation through try-catch
                try {
                    double value = Double.parseDouble(input);
                    return value;
                }catch(NumberFormatException e){
                    System.out.println("Invalid number! Try again.");
                    attempts++; // Increase attempt count
                }
            }
        }
        // After 3 invalid attempts, return to main menu
        System.out.println("Too many invalid attempts. Returning to Main Menu.");
        return -999; // return -999 to indicate failure
    }

    // Validate string input
    static String getValidString(String message) {

        // Count number of invalid attempts
        int attempts = 0;

        // Allow user to try only 3 times
        while (attempts < 3) {

            // Display input message
            System.out.print(message);

            // Read input from user
            String value = scanner.nextLine();

            // Check if input is empty
            if (!value.trim().isEmpty()) {
                return value;
            } else {
                System.out.println("Input cannot be empty! Try again.");
                attempts++; // Increase attempt count
            }
        }
        // After 3 invalid attempts, return to main menu
        System.out.println("Too many invalid attempts. Returning to Main Menu.");
        return null; // return null value to indicate failure
    }
}

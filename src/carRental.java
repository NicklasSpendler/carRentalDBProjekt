import java.sql.*;
import java.util.Scanner;

public class carRental {
    static final String JDBC_DRIVER = "COM.MYSQL.JDBC.DRIVER";
    static final String DATABASE_URL = "JDBC:MYSQL://LOCALHOST:3306/carrental";
    static Connection CON;
    static Statement s;

    public static void main(String[] args) {
        try{
            CON = DriverManager.getConnection(DATABASE_URL,"root", "x5hxnqMP#Zu3&6x@!T5muQ");
            s = CON.createStatement();
            /*
            ResultSet rs = s.executeQuery("SELECT * FROM cars");

            if(rs != null){
                while(rs.next()){
                    System.out.println(rs.getString("carType"));
                }
            }

             */
        }catch(SQLException sql){
            System.out.println("error " + sql.getMessage());
        }


        carMenu();
        //showBookings();
        //addCar();
        //deleteCar(2);
        //updateCar(1, "2", "2", "2", "2", 20200101, 1);
        //showCars();

        //updateCustomer(2, "lol", "lol", 2010, "lol", "lol", 20, "lol", 20201010, 20201010);
        //deleteCustomer(1);
        //deleteBooking(1);
        //showCustomers();
        //showBookings();
    }

    // Menu

    public static void carMenuText(){
        System.out.println("Options:");
        System.out.println("1. Show car overview.");
        System.out.println("2. Add new car.");
        System.out.println("3. Edit car.");
        System.out.println("4. Delete car");
    }

    public static void carMenu() {
        carMenuText();
        Scanner input = new Scanner(System.in);
        int valg = input.nextInt();
        switch (valg) {
            case 1:
                showCars();
                carMenu();
                break;
            case 2:
                addCarfunction(input);
                carMenu();
                break;
            case 3:
                System.out.println("Choose the car you wish you edit: ");
                showCars();
                int selectedCarID = input.nextInt();
                System.out.println("Car type: ");
                String carType = input.next();
                System.out.println("Brand: ");
                String brand = input.next();
                System.out.println("Model: ");
                String model = input.next();
                System.out.println("Enter Number Plate: ");
                String numberPlate = input.next();
                System.out.println("First year of registration: ");
                int firstRegYear = input.nextInt();
                System.out.println("Kilometer Driven: ");
                int kmDriven = input.nextInt();
                updateCar(selectedCarID, carType, brand, model, numberPlate, firstRegYear, kmDriven);
                carMenu();
                break;
            case 4:
                showCars();
                int selectedID = input.nextInt();
                deleteCar(selectedID);
                break;
        }
    }

    public static void addCarfunction(Scanner input){
        System.out.println("Add new car:\nEnter car type: ");
        String carType=input.next();
        System.out.println("Enter car brand ");
        String brand=input.next();
        System.out.println("Enter model: ");
        String model=input.next();
        System.out.println("Enter Number Plate: ");
        String numberPlate=input.next();
        System.out.println("Enter first registration date: ");
        int firstRegDate=input.nextInt();
        System.out.println("Enter Kilometers driven; ");
        int kmDriven=input.nextInt();
        addCar(carType,brand,model,numberPlate,firstRegDate,kmDriven);
    }

    // Car methods.
    public static void updateCar(int selectedCarID, String carType, String brand, String model, String numberPlate, int firstRegYear, int kmDriven){
        String query = "UPDATE cars SET carType = ?,brand = ?,model = ?,regNumberPlate = ?,firstRegYearMonth = ?,kmDriven = ? where carID = ?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(query);

            preparedStatement.setString(1, carType);
            preparedStatement.setString(2, brand);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, numberPlate);
            preparedStatement.setInt(5, firstRegYear);
            preparedStatement.setInt(6, kmDriven);
            preparedStatement.setInt(7, selectedCarID);
            int rs = preparedStatement.executeUpdate();
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void deleteCar(int selectedID){
        String query = "DELETE FROM Cars WHERE carID = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(query);
            preparedStatement.setInt(1, selectedID);
            int rs = preparedStatement.executeUpdate();
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void addCar(String carType, String brand, String model, String numberPlate, int firstRegDate, int kmDriven){
        String query = "INSERT into cars (carType, brand, model, regNumberPlate, firstRegYearMonth, kmDriven) Values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(query);
            preparedStatement.setString(1, carType);
            preparedStatement.setString(2, brand);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, numberPlate);
            preparedStatement.setInt(5, firstRegDate);
            preparedStatement.setInt(6, kmDriven);
            int rs = preparedStatement.executeUpdate();
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void showCars(){
        try{
            ResultSet rs = s.executeQuery("SELECT * FROM Cars");

            if(rs != null){
                while(rs.next()){
                    System.out.println("Car id: " + rs.getInt("carID") + "| Car Type: " + rs.getString("carType") + "| Car Brand: " + rs.getString("brand")+ "| Car Model: " + rs.getString("model")+ "| Car Plate: " + rs.getString("regNumberPlate")+ "| Car Reg date: " + rs.getString("firstRegYearMonth") + "| Kilometers driven: " + rs.getString("kmDriven"));
                }
            }
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    // Bookings

    public static void showBookings(){
        try{
            ResultSet rs = s.executeQuery("SELECT bookingID, firstName, lastName, carID, brand, model, rentDate, returnDate from booking join cars using(carID) join privatecustomer using(customerID)");

            if(rs != null){
                while(rs.next()){
                    System.out.println("Booking id: " + rs.getInt("bookingID") + "| Customer First Name: " + rs.getString("firstName") + "| Customer Last Name: " + rs.getString("lastName") + "| Car ID: " + rs.getInt("carID") + "| Car brand: " + rs.getString("brand") + "| Car Model: " + rs.getString("model") + "| Rent Date: " + rs.getDate("rentDate") + "| Return Date: " + rs.getDate("returnDate"));
                }
            }
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void addBooking(int CustomerID, int CarID, int returnDate){
        String query = "INSERT into booking (customerID, carID, rentDate, returnDate) Values (?, ?, 20200101, ?)";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = CON.prepareStatement(query);
            preparedStatement.setInt(1, CustomerID);
            preparedStatement.setInt(2, CarID);
            preparedStatement.setInt(3, returnDate);
            int rs = preparedStatement.executeUpdate();
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void deleteBooking(int selectedBookingID){
        String query = "DELETE FROM Booking WHERE bookingid = ?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = CON.prepareStatement(query);
            preparedStatement.setInt(1, selectedBookingID);
            int rs = preparedStatement.executeUpdate();
        }catch (SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }




    // customers
    public static void showCustomers() {
        try{
            Statement s = CON.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM privateCustomer");
            if(rs != null){
                while(rs.next()){
                    System.out.println("id: " + rs.getString("customerID"));
                    System.out.println("Fornavn: " + rs.getString("firstname"));
                    System.out.println("Efternavn: " + rs.getString("lastname"));
                    System.out.println("Postnummer: " + rs.getString("zipCode"));
                    System.out.println("By: " + rs.getString("cityName"));
                    System.out.println("mobil: " + rs.getString("mobileNr"));
                    System.out.println("email: " + rs.getString("email"));
                    System.out.println("k??rekort: " + rs.getString("driversLicenceNumber"));
                    System.out.println("Tilf??jet: " + rs.getString("CustomerSinceDate"));
                    System.out.println();
                }
            }
        }catch(SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void addACustomer(String firstName, String lastName, int zipCode, String cityName, String adress, int mobileNr, String email, int driversLicenceNumber, int customerSinceDate) {
        String insstr = "INSERT INTO privatecustomer (firstName, lastName, zipCode, cityName, adress, mobileNr, email, driversLicenceNumber, CustomerSinceDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(insstr);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, zipCode);
            preparedStatement.setString(4, cityName);
            preparedStatement.setString(5, adress);
            preparedStatement.setInt(6, mobileNr);
            preparedStatement.setString(7, email);
            preparedStatement.setInt(8, driversLicenceNumber);
            preparedStatement.setInt(9, customerSinceDate);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlerr)   {
            System.out.println("fejl i insert=" + sqlerr.getMessage());
        }
    }

    public static void addDataToCustomer(Scanner scan) {
        System.out.println("Indtast fornavn");
        String firstName = scan.next();
        System.out.println("Indtast efternavn");
        String lastName = scan.next();
        System.out.println("Indtast postnummer");
        int zipCode = scan.nextInt();
        System.out.println("Indtast bynavn");
        String cityName = scan.next();
        System.out.println("Indtast adresse");
        String adress = scan.next();
        System.out.println("Indtast mobile nummer");
        int mobileNr = scan.nextInt();
        System.out.println("Indtast email");
        String email = scan.next();
        System.out.println("Indtast k??rekort");
        int driversLicenceNumber = scan.nextInt();
        System.out.println("Indtast hvorn??r kunden blev oprettet");
        int customerSinceDate = scan.nextInt();

        addACustomer(firstName, lastName, zipCode, cityName, adress, mobileNr, email, driversLicenceNumber, customerSinceDate);
    }

    public static void updateCustomer(int customerID, String firstName, String lastName, int zipCode, String cityname, String adress, int mobileNr, String email, int driversLicenseNumber, int CustomerSinceDate) {
        String insstr = "UPDATE privatecustomer SET firstName = ?, lastName = ?, zipCode = ?, cityname = ?, adress = ?, mobileNr = ?, email = ?, driversLicenceNumber = ?, CustomerSinceDate = ? where customerID = ?";
        PreparedStatement preparedStatement;
        try{
            preparedStatement = CON.prepareStatement(insstr);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, zipCode);
            preparedStatement.setString(4, cityname);
            preparedStatement.setString(5, adress);
            preparedStatement.setInt(6, mobileNr);
            preparedStatement.setString(7, email);
            preparedStatement.setInt(8, driversLicenseNumber);
            preparedStatement.setInt(9, CustomerSinceDate);
            preparedStatement.setInt(10, customerID);

            int rs = preparedStatement.executeUpdate();
        }catch(SQLException sql){
            System.out.println("error " + sql.getMessage());
        }
    }

    public static void deleteCustomer(int selectedCustomerID) {
        String delstr = "DELETE FROM privatecustomer WHERE customerID = ?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CON.prepareStatement(delstr);
            preparedStatement.setInt(1, selectedCustomerID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlerr) {
            System.out.println("FEJL i delete =" + sqlerr.getMessage());
        }
    }
}
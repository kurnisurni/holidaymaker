package com.holidaymaker;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private Connection conn;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private Scanner scanner = new Scanner(System.in);

    public Program() throws SQLException {
        connect();
        printMenu();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printMenu(){
        boolean running = true;
        System.out.println("Welcome to Holidaymaker Travel Agency");
        System.out.println("---------------------------------------");
        while (running) {
            System.out.println("Please, select the menu:");
            System.out.println("[1] Register a customer");
            System.out.println("[2] Search for available rooms and make a reservation");
            System.out.println("[3] Cancel a reservation");
            System.out.println("[0] Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addCustomerToDb();
                    break;
                case "2":
                    addBooking();
                    break;
                case "3":
                    cancelBooking();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Wrong input. Please, enter a number between 0 to 3");

            }
        }
    }

    // Register a customer
    public void addCustomerToDb() {
        System.out.println("Enter a customer's name : ");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer's e-mail:");
        String customerEmail = scanner.nextLine();
        System.out.println("Enter customer's username:");
        String customerUsername = scanner.nextLine();

        try {
            statement = conn.prepareStatement("INSERT INTO guests SET NAME = ? , email = ? , username = ?; ");
            statement.setString(1, customerName);
            statement.setString(2, customerEmail);
            statement.setString(3, customerUsername);
            statement.executeUpdate();
            System.out.println("New customer, " + customerName + " is registered! Please, remember customer's username: " + customerUsername);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to register the customer");
        }

    }

    //Make a reservation
    private void addBooking() {
        int customerId = -1;

    //find customer by username
        while(true){
            System.out.print("Enter customer's username or fill blank to quit: ");
            String username = scanner.nextLine().trim();

            if(username.isBlank()) {
                return;
            }

            customerId = findCustomerByUsername(username);

            if(customerId != -1) {
                break;
            }
            else {
                System.out.println("No customer found with that username! Please, try again!");
            }
        }

        // check-in and check-out
        Date checkIn = null, checkOut = null;
        while(true) {
            try {

                System.out.print("Please, enter the check-in date between June 1 - July 31, 2020 (format: YYYY-MM-DD): ");
                checkIn = Date.valueOf(scanner.nextLine());

                if (checkIn.before(Date.valueOf(LocalDate.of(2020, 06, 01))) || checkIn.after(Date.valueOf(LocalDate.of(2020, 07, 31)))) {
                    System.out.println("Check-in date is invalid. Please, input between 2020-06-01 to 2020-07-31!");
                    continue;
                }

                System.out.print("Enter check-out date between June 1 - July 31, 2020 (format: YYYY-MM-DD): ");
                checkOut = Date.valueOf(scanner.nextLine());
            }
            catch (IllegalArgumentException e){
                System.out.println("Date was invalid or incorrect format! Please, try again!");
                continue;
            }

            if (checkOut.before(Date.valueOf(LocalDate.of(2020,06,01))) || checkOut.after(Date.valueOf(LocalDate.of(2020,07,31)))) {
                    System.out.println("Check-out date is invalid. Please, input between 2020-06-01 to 2020-07-31!");
                    continue;
                }

            if(checkIn.equals(checkOut)|| checkIn.after(checkOut)) {
                System.out.println("Input invalid! Check-in date must be before the check-out date");
                continue;
            }

            break;
            }

        // create booking
        int bookingId = -1;
        try {
            statement = conn.prepareStatement("INSERT INTO bookings (check_in, check_out, guest) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setDate(1, checkIn);
            statement.setDate(2, checkOut);
            statement.setInt(3, customerId);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) {
                bookingId = resultSet.getInt(1);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }

        // accommodation facilities
        int facilityId = -1;
        try {
            System.out.println("Accommodation facilities:");
            resultSet = getFacilities();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ". " + resultSet.getString("facilities_desc"));
            }

            facilityId = getInput("Enter facilities id: ");
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }

        // accommodation option
        int accommodationId = -1;
        try {
            System.out.println("List of accommodations:");
            statement = conn.prepareStatement("SELECT id, name FROM accommodations WHERE facility=?");
            statement.setInt(1, facilityId);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                System.out.println(resultSet.getInt("id") + ". " + resultSet.getString("name"));
            }

            accommodationId = getInput("Enter accommodation id: ");
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }

        // choose room
        while (true) {
            int roomCapacity = getInput("Enter the number of guests or fill blank to quit: ");
            if(roomCapacity == 0) {
                break;
            }

            System.out.println("Available rooms:");
            resultSet = getAvailableRooms(accommodationId, checkIn, checkOut, roomCapacity);
            try {
                if(!resultSet.next()) {
                    System.out.println("There are no available rooms that matches your criteria! Please, try again!");
                    continue;
                }

                do {
                    System.out.println(resultSet.getInt("id") + ". Room type:" + resultSet.getString("description"));
                } while (resultSet.next());
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }

            int roomId = getInput("Enter id room to book: ");

            try {
                statement = conn.prepareStatement("INSERT INTO booking_x_rooms (room, booking) VALUES (?, ?)");
                statement.setInt(1, roomId);
                statement.setInt(2, bookingId);
                statement.executeUpdate();
            }
            catch(Exception e) {
                e.printStackTrace();
                return;
            }

            System.out.print("Do you want to book another room? [y/n]: ");
            if(!scanner.nextLine().trim().toLowerCase().equals("y")) {
                break;
            }
        }

        // if no room booked, delete booking
        try {
            statement = conn.prepareStatement("SELECT COUNT(*) AS room_booked_id FROM booking_x_rooms WHERE booking=?");
            statement.setInt(1, bookingId);
            resultSet = statement.executeQuery();
            resultSet.next();

            int room_booked_id = resultSet.getInt("room_booked_id");

            if(room_booked_id == -1){
                statement = conn.prepareStatement("DELETE FROM bookings WHERE id=?");
                statement.setInt(1, bookingId);
                statement.executeUpdate();
                System.out.println("(Deleted booking since no room have been booked.)");
                return;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Reservation has been made successfully!");
    }

    private int getInput(String s) {
        while(true) {
            System.out.print(s);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            }
            catch(NumberFormatException e) {
                System.out.println("Error: \"" + input  +"\" is not a valid input. Please, Try again!");
            }
        }
    }

    private ResultSet getFacilities() {
        try {
            statement = conn.prepareStatement("SELECT facilities.id, CONCAT('pool:', facilities.pool, ', restaurant:', facilities.restaurant, ', kids club:', facilities.kids_club, ', night club:', facilities.night_club) AS facilities_desc FROM facilities");
            return statement.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int findCustomerByUsername(String username) {
        try {
            statement = conn.prepareStatement("SELECT id FROM guests WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            return resultSet.next() ? resultSet.getInt("id") : -1;
        }
        catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private ResultSet getAvailableRooms(int accommodationId, Date checkIn, Date checkOut, int roomCapacity) {
        try {
            statement = conn.prepareStatement(
                    "SELECT * FROM bookingrooms_date RIGHT JOIN roomsxroom_types ON room_id=id " +
                            "WHERE accommodation = ? AND guests_capacity=? " +
                            "AND ((check_in IS NULL AND check_out IS NULL) OR (NOT (? < check_out AND ? > check_in)))");

            statement.setInt(1, accommodationId);
            statement.setInt(2, roomCapacity);
            statement.setDate(3, checkIn);
            statement.setDate(4, checkOut);
            return statement.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Cancel a reservation
    private void cancelBooking() {
        ArrayList<String> bookings = new ArrayList<>();
        ArrayList<Integer> bookingsIds = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bookings");

            if (!resultSet.next()) {
                System.out.println("There are no reservations in the system..");
                return;
            }

            resultSet.beforeFirst();

            while (resultSet.next()) {
                String row = "Booking id: " + resultSet.getInt("id")
                        + ", Check-in: " + resultSet.getString("check_in")
                        + ", Check-out: " + resultSet.getString("check_out")
                        + ", Customer id: " + resultSet.getInt("guest");
                bookings.add(row);
                bookingsIds.add(resultSet.getInt("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error! Please, return to the home menu");
            return;
        }

        while (true) {
                    cancelBooking(bookings,bookingsIds);
                    return;
            }
        }

    public void cancelBooking(ArrayList<String> bookings, ArrayList<Integer> bookingsIds) {
        for (String booking : bookings) System.out.println(booking);

        int selection;
        while (true) {
            System.out.println("Enter the booking id to cancel or '0' to return: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (!bookingsIds.contains(selection)) throw new IndexOutOfBoundsException();
                for (int id : bookingsIds) {
                    if (selection == id) {
                        try {
                            Statement statement = conn.createStatement();
                            statement.executeUpdate("DELETE FROM bookings WHERE id = " + id + ";");
                            System.out.println("Booking id " + id + " cancelled succesfully!");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("Unable to cancel the reservation!");
                        }
                        break;
                    }
                }
                break;
            } catch (Exception ex) {
                System.out.println("Please, enter the valid booking id!\n");
            }
        }
    }


    
}




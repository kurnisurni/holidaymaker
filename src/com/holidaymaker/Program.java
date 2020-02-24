package com.holidaymaker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    Scanner scanner = new Scanner(System.in);
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private Booking booking = new Booking();
    private BookingInput bookingInput = new BookingInput();

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

    private void printMenu() throws SQLException {
        boolean running = true;
        System.out.println("Welcome to Holidaymaker Travel Agency");
        System.out.println("---------------------------------------");
        while (running) {
            System.out.println("Please, select the menu:");
            System.out.println("1. Register a customer");
            System.out.println("2. Search available rooms and make a reservation");
            System.out.println("3. Cancel a reservation");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addCustomerToDb();
                    break;
                case "2":
                    searchAndBookARoom();
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

    private void searchAndBookARoom() {
        String checkIn = bookingInput.CheckInDate();
        String checkOut = bookingInput.CheckOutDate();

        int numberOfGuests = bookingInput.numberOfGuests();
        System.out.println("Accommodation with swimming pool: Y/N ");
        String pool = scanner.nextLine();
        System.out.println("Accommodation with restaurant: Y/N ");
        String restaurant = scanner.nextLine();
        System.out.println("Accommodation with night club: Y/N ");
        String nightClub = scanner.nextLine();
        System.out.println("Accommodation with kids club: Y/N ");
        String kidsClub = scanner.nextLine();

        //booking.searchAvailableRooms(numberOfGuests, pool, restaurant, nightClub, kidsClub, checkOut, checkIn);
        //booking.printAvailableRooms();


        //Book - room
        System.out.println("Choose room number to book on chosen dates.");
        System.out.println("Room id: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.println("Guest id: ");
        int guestId = Integer.parseInt(scanner.nextLine());
        booking.bookRoom(guestId, roomId, numberOfGuests, checkIn, checkOut);
        System.out.println(" ");

    }

    private void cancelBooking() {
        ArrayList<String> bookings = new ArrayList<>();
        ArrayList<Integer> bookingIds = new ArrayList<>();
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
                        + ", Customer id: " + resultSet.getInt("guest_id")
                        + ", Number of guests: " + resultSet.getInt("number_of_guests")
                        + ", Room id: " + resultSet.getInt("room_id")
                        + ", Check-in: " + resultSet.getString("check_in_date")
                        + ", Check-out: " + resultSet.getString("check_out_date");
                bookings.add(row);
                bookingIds.add(resultSet.getInt("id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error! Please, return to the home menu");
            return;
        }

        while (true) {
                    cancelBooking(bookings,bookingIds);
                    return;
            }
        }

    public void cancelBooking(ArrayList<String> bookings, ArrayList<Integer> bookingIds) {
        for (String booking : bookings) System.out.println(booking);

        int selection;
        while (true) {
            System.out.println("Enter the booking ID to cancel or '0' to return: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) return;
                if (!bookingIds.contains(selection)) throw new IndexOutOfBoundsException();
                for (int id : bookingIds) {
                    if (selection == id) {
                        try {
                            Statement statement = conn.createStatement();
                            statement.executeUpdate("DELETE FROM bookings WHERE id = " + id + ";");
                            System.out.println("Booking " + id + " cancelled succesfully!");
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


    private void addCustomerToDb() {
        System.out.println("Enter a customer's name : ");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer's e-mail:");
        String customerEmail = scanner.nextLine();

        try {
            statement = conn.prepareStatement("INSERT INTO guests SET NAME = ? , email = ?; ");
            statement.setString(1, customerName);
            statement.setString(2, customerEmail);
            statement.executeUpdate();
            System.out.println("New customer " + customerName + " is registered!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to register the customer");
        }
    }

    
}




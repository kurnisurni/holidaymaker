package com.holidaymaker;

import javax.naming.directory.SearchResult;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program {
Scanner scanner = new Scanner(System.in);
private  Connection conn = null;
private PreparedStatement statement;
private ResultSet resultSet;

    public Program() throws SQLException {
        connect();
        printMenu();
    }

    private void connect(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printMenu() throws SQLException {
        boolean running = true;
        System.out.println("Welcome to Holidaymaker Travel Agent");
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
                    searchAvailableRooms();
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

    private void cancelBooking() {
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


        private void searchAvailableRooms() {
            int customerId;
            int numberOfGuests;
            String facilityChoice;
            SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");
            String startDateString;
            String endDateString;
            java.util.Date startDate = null;
            java.util.Date endDate = null;
            String chooseSwimPoolFacility;
            String chooseRestaurantFacility;
            String chooseNightClubFacility;
            String chooseKidsClubFacility;
            ArrayList<Integer> customerIds = new ArrayList<>();

            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM guests;");

                while (resultSet.next()) {
                    customerIds.add(resultSet.getInt("id"));
                    String row = "Customer ID: " + resultSet.getInt("id")
                            + ". Name: " + resultSet.getString("name")
                            + ", E-mail: " + resultSet.getString("email");
                    System.out.println(row);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            while (true) {
                System.out.println("Enter customer ID for booking or '0' to return:");
                try {
                    int selection = Integer.parseInt(scanner.nextLine());
                    if (selection == 0) return;
                    if (!customerIds.contains(selection)) throw new IndexOutOfBoundsException();
                    customerId = selection;
                    break;
                } catch (Exception ex) {
                    System.out.println("Please enter a valid customer ID or '0' to return");
                }
            }

            while (true) {
                System.out.println("Enter number of guests for booking or '0' to return:");
                try {
                    numberOfGuests = Integer.parseInt(scanner.nextLine());
                    if (numberOfGuests == 0) return;
                    if (numberOfGuests < 0) {
                        throw new IndexOutOfBoundsException();
                    }
                    break;
                } catch (Exception ex) {
                    System.out.println("Please input at least 1 guest");
                }
            }

            while (true) {
                System.out.println("Enter check-in date for Summer season (2020-06-01 until 2020-07-31): ");
                try {
                    startDateString = scanner.nextLine();
                    startDate = date.parse(startDateString);
                    if (startDate.before(date.parse("2020-08-01")) && startDate.after(date.parse("2020-05-31"))) {
                        break;
                    } else throw new IndexOutOfBoundsException();
                } catch (Exception ex){
                    System.out.println("Check-in date is unavailable");
                }
            }

            while (true) {
                System.out.println("Enter check-out date for Summer season (2020-06-01 until 2020-07-31)");
                try {
                    endDateString = scanner.nextLine();
                    endDate = date.parse(endDateString);
                    if (endDate.after(startDate) && endDate.before(date.parse("2020-08-01"))){
                        break;
                    } else throw new IndexOutOfBoundsException();
                } catch (Exception ex){
                    System.out.println("Check-out date is unavailable");
                }
            }

            long diff = endDate.getTime() - startDate.getTime();
            long nights = (diff / (1000*60*60*24));

            while (true) {
                System.out.println("Swimming pool in the accommodation? (y/n)");
                facilityChoice = scanner.nextLine().toLowerCase();
                if (facilityChoice.equals("y")) {
                    chooseSwimPoolFacility = "pool = 1 AND ";
                    break;
                } else if (facilityChoice.equals("n")) {
                    chooseSwimPoolFacility = "";
                    break;
                } else System.out.println("Please, choose a valid option! (y/n)");
            }

            while (true) {
                System.out.println("Restaurant in the accommodation? (y/n)");
                facilityChoice = scanner.nextLine().toLowerCase();

                if (facilityChoice.equals("y")) {
                    chooseRestaurantFacility = "restaurant = 1 AND ";
                    break;
                } else if (facilityChoice.equals("n")) {
                    chooseRestaurantFacility = "";
                    break;
                } else System.out.println("Please, choose a valid option! (y/n)");
            }

            while (true) {
                System.out.println("Night club in the accommodation? (y/n)");
                facilityChoice = scanner.nextLine().toLowerCase();

                if (facilityChoice.equals("y")) {
                    chooseNightClubFacility = "night club = 1 AND ";
                    break;
                } else if (facilityChoice.equals("n")) {
                    chooseNightClubFacility = "";
                    break;
                } else System.out.println("Please, choose a valid option! (y/n)");
            }

            while (true) {
                System.out.println("Kids club in the accommodation? (y/n)");
                facilityChoice = scanner.nextLine().toLowerCase();

                if (facilityChoice.equals("y")) {
                    chooseKidsClubFacility = "kids club = 1 AND ";
                    break;
                } else if (facilityChoice.equals("n")) {
                    chooseKidsClubFacility = "";
                    break;
                } else System.out.println("Please, choose a valid option! (y/n)");
            }


            ArrayList<Integer> accommodation_ids = new ArrayList<>();
            ArrayList<SearchResult> sortedResults = new ArrayList<>();

            java.sql.Date startDate2 = new java.sql.Date(startDate.getTime());
            java.sql.Date endDate2 = new java.sql.Date(endDate.getTime());

            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT a.accommodation_id, a.name, a.swimming_pool, a.restaurant, a.night_club, a.kids_club, b.check_in_date, b.check_out_date " +
                        "FROM accommodation a LEFT JOIN bookings b ON a.accommodation_id = a.accommodation_id " +
                        "WHERE (" + chooseSwimPoolFacility + chooseRestaurantFacility + chooseNightClubFacility + chooseKidsClubFacility + " AND b.number_of_guests = " + numberOfGuests + ") AND (b.check_in_date is null OR ('" + startDate2 + "' < b.check_in_date" + " OR '" + startDate2 + "' > b.check_out_date)" + " AND ('" + endDate2 + "' < b.check_in_date OR '" + endDate2 + "' > b.check_out_date))" +
                        " GROUP BY a.name;");

                if (!resultSet.next()) {
                    System.out.println("Sorry, we could not find any rooms available with the specified criteria.");
                    return;
                }
                resultSet.beforeFirst();

                while (resultSet.next()) {
                    Statement anotherStatement = conn.createStatement();
                    ResultSet amountOfRooms = anotherStatement.executeQuery("SELECT COUNT(r.room_id) FROM rooms r WHERE (r.accommodation_id = " + resultSet.getInt("accommodation_id") + " AND b.antal_sängar = " + numberOfGuests + ") AND (b.check_in_date is null OR ('" + startDate2 + "' < b.check_in_date" + " OR '" + startDate2 + "' > b.check_out_date)" + " AND ('" + endDate2 + "' < b.check_in_date OR '" + endDate2 + "' > b.check_out_date));");
                    int rooms = 0;
                    int pricePerNight = resultSet.getInt("room_price");

                    while (amountOfRooms.next()) {
                        rooms = amountOfRooms.getInt(1);
                    }

                    double totalPrice = pricePerNight * (nights);

                    int accommodation_id = resultSet.getInt("accommodation_id");
                    String name = resultSet.getString("name");
                    boolean swimmingPool = resultSet.getBoolean("swimming_pool");
                    boolean restaurant = resultSet.getBoolean("restaurant");
                    boolean nightClub = resultSet.getBoolean("night_club");
                    boolean kidsClub = resultSet.getBoolean("kids_club");

                    accommodation_ids.add(accommodation_id);
                    sortedResults.add(new MatchFacility(accommodation_id,name,swimmingPool,restaurant,nightClub,kidsClub,numberOfGuests,rooms, pricePerNight, totalPrice));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Wrong option!");
            }

            for (SearchResult searchResult : sortedResults) {
                System.out.println(searchResult);
            }

            while (true) {
                System.out.println("1. Make a reservation");


                String selection = scanner.nextLine();

                switch (selection) {
                    case "1":
                        int roomId = 0;
                        while (true) {
                            System.out.println("Book a room between " + startDateString + " - " + endDateString + " by entering the accommodation number or 0 to end the search");
                            try {
                                int selection2 = Integer.parseInt(scanner.nextLine());
                                if (selection2 == 0) return;
                                if (!accommodation_ids.contains(selection2)) {
                                    throw new IndexOutOfBoundsException();
                                }
                                Statement statement = conn.createStatement();
                                ResultSet resultSet = statement.executeQuery("SELECT r.room_id FROM rooms r WHERE (b.number_of_guests = " + numberOfGuests + " AND r.accommodation_id = " + selection2 + ") AND (b.check_in_date is null OR ('" + startDate2 + "' < b.check_in_date" + " OR '" + startDate2 + "' > b.check_out)" + " AND ('" + endDate2 + "' < b.check_in_date OR '" + endDate2 + "' > b.check_out_date));");

                                while (resultSet.next()) {
                                    roomId = resultSet.getInt("room_id");
                                    break;
                                }
                                break;
                            } catch (Exception ex) {
                                System.out.println("Please, input the valid option!\n");
                            }
                        }

                        try {
                            PreparedStatement statement = conn.prepareStatement("INSERT INTO bookings (guest_id, room_id, numnber_of_guests, check_in_date, check_out_date) VALUES (?, ?, ?, ?, ?);");
                            statement.setInt(1, customerId);
                            statement.setInt(2, roomId);
                            statement.setInt(3, numberOfGuests);
                            statement.setDate(4, startDate2);
                            statement.setDate(5, endDate2);
                            statement.executeUpdate();

                            System.out.println("The reservation is complete!\n");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("Sorry, the reservation can not be made!\n");
                        }
                        return;
                    case "0":
                        return;
                    default:
                        System.out.println("Please, insert a valid option!\n");
                }
            }
        }
    }




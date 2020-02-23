package com.holidaymaker;

import java.sql.*;
import java.util.Scanner;

public class Program {
Scanner scanner = new Scanner(System.in);
private  Connection connection = null;
private PreparedStatement statement;
private ResultSet resultSet;

    public Program() throws SQLException {
        connect();
        printMenu();
    }

    private void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
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
            System.out.println("2. Search available rooms");
            System.out.println("3. Make a reservation");
            System.out.println("4. Cancel a reservation");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addCustomerToDb();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Wrong input. Please, enter a number between 0 to 4");

            }
        }
    }



    private void addCustomerToDb() {
        System.out.println("Enter a customer's name : ");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer's e-mail:");
        String customerEmail = scanner.nextLine();

        try {
            statement = connection.prepareStatement("INSERT INTO guests SET NAME = ? , email = ?; ");
            statement.setString(1, customerName);
            statement.setString(2, customerEmail);
            statement.executeUpdate();
            System.out.println("New customer " + customerName + " is registered!");
        }
         catch (Exception ex) {
            ex.printStackTrace();
             System.out.println("Unable to register the customer");
        }
    }



}

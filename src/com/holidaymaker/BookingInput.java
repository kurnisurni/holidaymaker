package com.holidaymaker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BookingInput {
    Scanner input = new Scanner(System.in);

    public int numberOfGuests() {
        while (true) {
            System.out.println("Enter the number of guests: ");
            int numberOfGuests = Integer.parseInt(input.nextLine());
            if (numberOfGuests < 1) {
                System.out.println("Invalid input! Guests can not be 0");
            } else {
                return numberOfGuests;
            }
        }
    }


    public String CheckInDate() {
        while (true) {
            System.out.println("Enter check-in date for Summer season (2020-06-01 until 2020-07-31)");
            String checkIn = input.nextLine();
            LocalDate checkInDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkIn));
            LocalDate startOfSeason = LocalDate.of(2020, 6, 1);
            LocalDate endOfSeason = LocalDate.of(2020, 7, 31);
            if (checkInDate.isBefore(startOfSeason)||checkInDate.isAfter(endOfSeason)) {
                System.out.println("Invalid input! Please, check-in between 2020-06-01 and 2020-07-31");
            } else {
                return checkIn;
            }
        }


    }

    public String CheckOutDate() {
        while (true) {
            System.out.println("Enter check-out date for Summer season (2020-06-01 until 2020-07-31)");
            String checkOut = input.nextLine();
            LocalDate checkOutDate = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(checkOut));
            LocalDate startOfSeason = LocalDate.of(2020, 6, 1);
            LocalDate endOfSeason = LocalDate.of(2020, 7, 31);
            if (checkOutDate.isBefore(startOfSeason)||checkOutDate.isAfter(endOfSeason)) {
                System.out.println("Invalid input! Please, check-out between 2020-06-01 and 2020-07-31");
            } else {
                return checkOut;
            }
        }

    }
}

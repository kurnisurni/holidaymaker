package com.holidaymaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Booking {
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public Booking() {

    }

    private void connect() {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void searchAvailableRooms(int numberOfGuests, String pool, String restaurant, String nightClub, String kidsClub, String checkOutDate, String checkInDate) {

        if (numberOfGuests == 1) {
            try {
                statement = conn.prepareStatement("SELECT * FROM accommodations WHERE swimming_pool = ? AND restaurant = ? " +
                        "AND night_club = ? AND kids_club = ? " +
                        "AND type = 'Single' HAVING check_out_date <= ? OR check_in_date >= ?");
                statement.setString(1, pool);
                statement.setString(2, restaurant);
                statement.setString(3, nightClub);
                statement.setString(4, kidsClub);
                statement.setString(5, checkOutDate);
                statement.setString(6, checkInDate);
                resultSet = statement.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (numberOfGuests == 2) {
            try {
                statement = conn.prepareStatement("SELECT * FROM accommodations WHERE swimming_pool = ? AND restaurant = ? " +
                        "AND night_club = ? AND kids_club = ? " +
                        "AND type = 'Double' OR 'VIP Suite' HAVING check_out_date <= ? OR check_in_date >= ?");
                statement.setString(1, pool);
                statement.setString(2, restaurant);
                statement.setString(3, nightClub);
                statement.setString(4, kidsClub);
                statement.setString(5, checkOutDate);
                statement.setString(6, checkInDate);
                resultSet = statement.executeQuery();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (numberOfGuests == 4){
            try {
                statement = conn.prepareStatement("SELECT * FROM accommodations WHERE swimming_pool = ? AND restaurant = ? " +
                        "AND night_club = ? AND kids_club = ? " +
                        "AND type = 'Family' HAVING check_out_date <= ? OR check_in_date >= ?");
                statement.setString(1, pool);
                statement.setString(2, restaurant);
                statement.setString(3, nightClub);
                statement.setString(4, kidsClub);
                statement.setString(5, checkOutDate);
                statement.setString(6, checkInDate);
                resultSet = statement.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void bookRoom(int guestId, int roomId, int numberOfGuests, String checkInDate, String checkOutDate) {

        try {
            statement = conn.prepareStatement("INSERT INTO bookings SET guest_id = ?, room_id = ?, number_of_guests = ?, check_in_date = ?, check_out_date");
            statement.setInt(1, guestId);
            statement.setInt(2, roomId);
            statement.setInt(3, numberOfGuests);
            statement.setString(4, checkInDate);
            statement.setString(5, checkOutDate);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void printAvailableRooms() {
        try {
            while (resultSet.next()) {
                String row = "Room id: " + resultSet.getString("room_id")
                        + ", Accommodation: " + resultSet.getString("hotel_name")
                        + ", City: " + resultSet.getString("hotel_city")
                        + ", Room type: " + resultSet.getString("type")
                        + ", Price: " + resultSet.getDouble("price");
                System.out.println(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

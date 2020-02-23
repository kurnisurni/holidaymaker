package com.holidaymaker;

import java.util.Comparator;

public class MatchFacility {
    private int accommodation_id;
    private String name;
    private boolean swimmingPool;
    private boolean restaurant;
    private boolean nightClub;
    private boolean kidsClub;
    private int numberOfGuests;
    private int rooms;
    private int pricePerNight;
    private int totalPrice;

    public MatchFacility(int accommodation_id, String name, boolean swimmingPool, boolean restaurant, boolean nightClub, boolean kidsClub, int numberOfGuests, int rooms, int pricePerNight, int totalPrice) {
        this.accommodation_id = accommodation_id;
        this.name = name;
        this.swimmingPool = swimmingPool;
        this.restaurant = restaurant;
        this.nightClub = nightClub;
        this.kidsClub = kidsClub;
        this.numberOfGuests = numberOfGuests;
        this.rooms = rooms;
        this.pricePerNight = pricePerNight;
        this.totalPrice = totalPrice;
    }

    public String toString(){
        return accommodation_id + ". Accommodation: " + name
                + ", Swimming pool: " + swimmingPool
                + ", Restaurant: " + restaurant
                + ", Night club: " + nightClub
                + ", Kids club: " + kidsClub
                + ", Available room for " + numberOfGuests + " guests: " + rooms
                + ", Price per night: " + pricePerNight
                + ", Total price: " + totalPrice + "\n";
    }

    public int getAccommodation_id() {
        return accommodation_id;
    }


    public int getPricePerNight() {
        return pricePerNight;
    }
}

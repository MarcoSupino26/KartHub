package models.track;

import javafx.scene.image.Image;
import models.booking.BookingInterface;
import models.event.KartEvent;
import models.slots.TimeSlot;
import models.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Track {
    private String name;
    private String description;
    private int availableKarts;
    private final HashMap<LocalDate, List<TimeSlot>> timeSlots;
    private final HashMap<String, BookingInterface> bookings;
    private User owner;
    private String address;
    private Image image;
    private List<Double> cost;
    private double openingHour;
    private double closingHour;
    private double shiftDuration;
    private final HashMap<String, KartEvent> events;

    public Track(){
        timeSlots = new HashMap<>();
        cost = new ArrayList<>();
        bookings = new HashMap<>();
        events = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getAvailableKarts() {
        return availableKarts;
    }

    public void setAvailableKarts(int availableKarts) {
        this.availableKarts = availableKarts;
    }

    public List<TimeSlot> getTimeSlots(LocalDate date) {
        return timeSlots.getOrDefault(date, Collections.emptyList());
    }


    public void addTimeSlots(List<TimeSlot> timeSlots, LocalDate date) {
        this.timeSlots.put(date, timeSlots);
    }

    public void setSlotAvailability(LocalDate date, int numShifts, double startTime){
        int i = 0;
        for(TimeSlot timeSlot : timeSlots.get(date)){
            if(timeSlot.getStartTime() == startTime){
                for (int j = 0; j < numShifts; j++) {
                    timeSlots.get(date).get(i+j).setAvailability(false);
                }
            }
            i++;
        }
    }

    public BookingInterface getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public List<BookingInterface> allBookings(){
        return new ArrayList<>(bookings.values());
    }

    public void addBooking(BookingInterface booking) {
        this.bookings.put(booking.getId(), booking);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){return address;}

    public void setImage(Image image){
        this.image = image;
    }

    public void setCost(List<Double> cost){
        this.cost = cost;
    }

    public double getCost(int i){
        return cost.get(i-1);
    }

    public Image getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setShiftDuration(double shiftDuration) {this.shiftDuration = shiftDuration;}

    public void setOpeningHour(double openingHour) {this.openingHour = openingHour;}

    public void setClosingHour(double closingHour) {this.closingHour = closingHour;}

    public double getOpeningHour() {return openingHour;}

    public double getClosingHour() {return closingHour;}

    public double getShiftDuration() {return shiftDuration;}

    public KartEvent getEvent(String eventName) {return events.get(eventName);}

    public void addEvent(KartEvent event) {events.put(event.getEventName(), event);}

    public List<KartEvent> allEvents() {
        return new ArrayList<>(events.values());
    }
}

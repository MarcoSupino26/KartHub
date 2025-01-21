package utils;

import models.event.KartEvent;

import java.util.ArrayList;
import java.util.List;

public class EventSession {
    private List<KartEvent> kartEvents;
    private KartEvent currentKartEvent;
    private int shoppedTickets;
    private double payment;
    private String trackname;

    public EventSession() {
        shoppedTickets = 0;
        kartEvents = new ArrayList<>();
    }

    public void addEvent(KartEvent kartEvent) {
        kartEvents.add(kartEvent);
    }

    public List<KartEvent> getEvents() {return kartEvents;}

    public void setKartEvents(List<KartEvent> kartEvents) {this.kartEvents = kartEvents;}

    public void setCurrentKartEvent(KartEvent currentKartEvent){this.currentKartEvent = currentKartEvent;}

    public KartEvent getCurrentKartEvent(){return currentKartEvent;}

    public int getShoppedTickets() {return shoppedTickets;}

    public void setShoppedTickets(int shoppedTickets) {this.shoppedTickets = shoppedTickets;}

    public double getPayment() {return payment;}

    public void setPayment(double payment) {this.payment = payment;}

    public String getTrackname() {return trackname;}

    public void setTrackname(String trackname) {this.trackname = trackname;}
}

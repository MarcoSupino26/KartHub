package beans;

import java.time.LocalDate;

public class BookingsDisplayBean {
    private String description;
    private final LocalDate date;
    private String shift;
    private String rental;
    private String personal;
    private String user;
    private String cost;
    private LocalDate day;
    private String trackName;

    public BookingsDisplayBean(LocalDate selectedDay){
        this.date = selectedDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getUsr() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCost() {return cost;}

    public void setCost(String cost) {this.cost = cost;}

    public LocalDate getSelectedDay(){return day;}

    public void setSelectedDay(LocalDate selectedDay){this.day = selectedDay;}

    public String getTrackName() {return trackName;}

    public void setTrackName(String trackName) {this.trackName = trackName;}
}

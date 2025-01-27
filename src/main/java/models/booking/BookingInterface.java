package models.booking;

import models.user.User;

import java.time.LocalDate;

public interface BookingInterface {
    String getDescription();
    double getCost();
    String getId();
    int getRental();
    int getPersonal();
    String getUser();
    String getShift();
    String getTrackName();
    LocalDate getSelectedDay();
}

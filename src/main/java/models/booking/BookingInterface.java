package models.booking;

import models.user.User;

public interface BookingInterface {
    String getDescription();
    double getCost();
    String getId();
    int getRental();
    int getPersonal();
    User getUser();
}

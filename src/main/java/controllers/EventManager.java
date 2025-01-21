package controllers;

import models.dao.factory.FactoryDAO;
import models.event.KartEvent;
import models.event.KartEventDao;
import utils.EventSession;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    public EventManager() {}

    public void getAllEvents() {

        KartEventDao kartEventDao = FactoryDAO.getInstance().createKartEventDao();
        List<KartEvent> kartEvents = kartEventDao.getAllEvents();

        EventSession eventSession = new EventSession();
        eventSession.setKartEvents(kartEvents);

        SessionManager.getInstance().createEventSession(eventSession);
    }

    public void makePayment() {
    }
}

package controllers;

import beans.DisplayBean;
import models.dao.factory.FactoryDAO;
import models.track.Track;
import models.track.TrackDao;
import utils.BookingSession;
import utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class BookManager {

    public List<DisplayBean> getTracks(){
        List<DisplayBean> disBeans = new ArrayList<DisplayBean>();
        List<Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            DisplayBean displayBean = new DisplayBean();
            displayBean.setImage(track.getImage());
            displayBean.setName(track.getName());
            displayBean.setDescription(track.getDescription());
            disBeans.add(displayBean);
        }
        return disBeans;

    }
    public void setBookingSession(String trackName){
        List <Track> tracks;
        tracks = FactoryDAO.getInstance().createTrackDao().getAllTracks();
        for (Track track : tracks) {
            if(track.getName().equals(trackName)){
                BookingSession bookingSession = new BookingSession(track);
                String logged = SessionManager.getInstance().getLoggedUser().getUsername();
                SessionManager.getInstance().createBookingSession(bookingSession, logged);
            }
        }
    }
}

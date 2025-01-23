package beans;

import javafx.scene.image.Image;

public class BookingTrackBean {
    private final String trackName;
    private final Image trackImage;

    public BookingTrackBean(String trackName, Image trackImage) {
        this.trackName = trackName;
        this.trackImage = trackImage;
    }

    public String getTrackName() {return trackName;}

    public Image getTrackImage() {return trackImage;}
}

package beans;

import javafx.scene.image.Image;
import models.slots.TimeSlot;
import models.user.User;

import java.util.List;

public class TrackBean {
    private String name;
    private Image image;
    private String description;
    private String address;

    public TrackBean() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {return  address;}

    public Image getImage() {return image;}

    public String getDescription() {return description;}

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

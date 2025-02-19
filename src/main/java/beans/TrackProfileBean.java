package beans;

import javafx.scene.image.Image;

public class TrackProfileBean {
    private final Image image;
    private final String name;

    public TrackProfileBean(Image image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

}

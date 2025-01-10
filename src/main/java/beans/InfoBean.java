package beans;

import javafx.scene.image.Image;

public class InfoBean {
    private Image image;
    private String name;

    public InfoBean(Image image, String name) {
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

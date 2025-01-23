package beans;

import javafx.scene.image.Image;

public class DisplayBean {
    private Image image;
    private String name;
    private String description;

    public DisplayBean(){//La new non ha bisogno di un parametro
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

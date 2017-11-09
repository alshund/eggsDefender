package view.egg;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Egg {
    private ImageView imageView;
    private IdleAnimation idleAnimation;
    private DieAnimation dieAnimation;
    private int id;

    public Egg(int id) {
        this.id = id;

        InputStream inputStream = Egg.class.getResourceAsStream(EggSpriteData.PATH);
        Image image = new Image(inputStream);
        imageView = new ImageView(image);
        idleAnimation = new IdleAnimation(imageView);
        dieAnimation = new DieAnimation(imageView);
    }

    public void idle() {
        idleAnimation.play();
    }

    public void die() {
        idleAnimation.pause();
        dieAnimation.play();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

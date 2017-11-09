package view.character;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Character {
    private ImageView imageView;
    private MoveAnimation moveAnimation;
    private IdleAnimation idleAnimation;
    private ShotAnimation showAnimation;

    public Character() {
        InputStream inputStream = Character.class.getResourceAsStream(CharacterSpriteData.CHARACTER_PATH);
        Image image = new Image(inputStream);
        imageView = new ImageView(image);
        idleAnimation = new IdleAnimation(imageView);
        moveAnimation = new MoveAnimation(imageView, idleAnimation);
        showAnimation = new ShotAnimation(imageView, idleAnimation);
    }

    public void start() {
        idleAnimation.resume();
    }


    public void move(double speed) {
        moveAnimation.setSpeed(speed);
        idleAnimation.pause();
        moveAnimation.play();
    }

    public Bullet attack() {
        idleAnimation.pause();
        showAnimation.play();
        return new Bullet();
    }

    public double getBulletCoordinateX() {
        return imageView.getTranslateX() + CharacterSpriteData.BULLET_dX;
    }

    public double getBulletCoordinateY() {
        return imageView.getTranslateY() + CharacterSpriteData.BULLET_dY;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}

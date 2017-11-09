package view.character;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.ObservableAnimation;
import view.animations.AnimationObserver;

import static view.character.CharacterSpriteData.*;

public class MoveAnimation extends Transition implements ObservableAnimation {
    private ImageView imageView;
    private int lastIndex;

    private double speed = 0;

    private AnimationObserver animationObserver;

    public MoveAnimation(ImageView imageView, AnimationObserver animationObserver) {
        this.imageView = imageView;
        setAnimationObserver(animationObserver);
        setCycleDuration(Duration.millis(MOVE_DURATION));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != lastIndex) {
            final int coordinateX = (index % SPRITES_COUNT) * MOVE_WIDTH  + MOVE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, MOVE_Y, MOVE_WIDTH, MOVE_HEIGHT));
            adjustImage();
            lastIndex = index;
            move();
        }
        if (lastIndex == SPRITES_COUNT - 1) {
            animationObserver.resume();
        }
    }

    private void adjustImage() {
        imageView.setFitHeight(FIT_HEIGHT);
        imageView.setFitWidth(FIT_WIDTH);
        imageView.preserveRatioProperty();
        imageView.smoothProperty();
    }

    public void move() {
        double newCoordinateY = imageView.getTranslateY() + speed;
        imageView.setTranslateY(newCoordinateY);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setAnimationObserver(AnimationObserver animationObserver) {
        this.animationObserver = animationObserver;
    }

    @Override
    public AnimationObserver getAnimationObserver() {
        return animationObserver;
    }
}

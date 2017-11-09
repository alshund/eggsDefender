package view.egg;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static view.egg.EggSpriteData.*;

public class IdleAnimation extends Transition {
    private ImageView imageView;
    private double currentSprite = 0;

    public IdleAnimation(ImageView imageView) {
        this.imageView = imageView;

        setCycleDuration(Duration.millis(IDLE_DURATION));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * IDLE_SPRITES_COUNT), IDLE_SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % IDLE_SPRITES_COUNT) * WIDTH  + IDLE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, IDLE_Y, WIDTH, HEIGHT));
            adjustImage();
            currentSprite = index;
        }
    }

    private void adjustImage() {
        imageView.setFitHeight(FIT_HEIGHT);
        imageView.setFitWidth(FIT_WIDTH);
        imageView.preserveRatioProperty();
        imageView.smoothProperty();
    }
}

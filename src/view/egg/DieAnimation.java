package view.egg;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static view.egg.EggSpriteData.*;
import static view.egg.EggSpriteData.FIT_HEIGHT;
import static view.egg.EggSpriteData.FIT_WIDTH;

public class DieAnimation extends Transition {
    private ImageView imageView;
    private double currentSprite = 0;

    public DieAnimation(ImageView imageView) {
        this.imageView = imageView;

        setCycleDuration(Duration.millis(IDLE_DURATION));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * DIE_SPRITES_COUNT), DIE_SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % DIE_SPRITES_COUNT) * WIDTH  + DIE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, DIE_Y, WIDTH, HEIGHT));
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

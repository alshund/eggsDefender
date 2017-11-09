package view.enemy;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.AnimationObserver;

import static view.enemy.EnemySpriteData.*;

public class IdleAnimation extends Transition implements AnimationObserver{
    private ImageView imageView;
    private double currentSprite = 0;

    public IdleAnimation(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(200));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % SPRITES_COUNT) * ENEMY_WIDTH + IDLE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, IDLE_Y, ENEMY_WIDTH, ENEMY_HEIGHT));
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

    @Override
    public void resume() {
        this.play();
    }
}
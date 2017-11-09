package view.enemy;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.AnimationObserver;
import view.animations.ObservableAnimation;

import static view.enemy.EnemySpriteData.*;
import static view.enemy.EnemySpriteData.SPRITES_COUNT;

public class DieAnimation extends Transition implements ObservableAnimation {
    private ImageView imageView;
    private double currentSprite = 0;

    private AnimationObserver animationObserver;

    public DieAnimation(ImageView imageView, AnimationObserver animationObserver) {
        this.imageView = imageView;

        setAnimationObserver(animationObserver);
        setCycleDuration(Duration.millis(HURT_DURATION));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % SPRITES_COUNT) * ENEMY_WIDTH  + DIE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, DIE_Y, ENEMY_WIDTH, ENEMY_HEIGHT));
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
    public void setAnimationObserver(AnimationObserver animationObserver) {
        this.animationObserver = animationObserver;
    }

    @Override
    public AnimationObserver getAnimationObserver() {
        return animationObserver;
    }
}
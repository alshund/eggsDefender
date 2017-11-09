package view.character;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.ObservableAnimation;
import view.animations.AnimationObserver;

import static view.character.CharacterSpriteData.*;

public class ShotAnimation extends Transition implements ObservableAnimation {
    private ImageView imageView;
    private int lastIndex;

    private AnimationObserver animationObserver;

    public ShotAnimation(ImageView imageView, AnimationObserver animationObserver) {
        this.imageView = imageView;
        setAnimationObserver(animationObserver);
        setCycleDuration(Duration.millis(SHOT_DURATION));
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != lastIndex) {
            final int coordinateX = (index % SPRITES_COUNT) * SHOT_WIDTH + SHOT_X;
            imageView.setViewport(new Rectangle2D(coordinateX, SHOT_Y, SHOT_WIDTH, SHOT_HEIGHT));
            adjustImage();
            lastIndex = index;
        }
        if (index == SPRITES_COUNT - 1) {
            animationObserver.resume();
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

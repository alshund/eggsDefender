package view.enemy;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.AnimationObserver;
import view.egg.Egg;

import java.util.List;

import static view.enemy.EnemySpriteData.*;

public class MoveAnimation extends Transition implements AnimationObserver {
    private Enemy enemy;
    private ImageView imageView;
    private double currentSprite = 0;
    private Controller controller;
    private Thread thread;

    public MoveAnimation(Enemy enemy, Controller controller) {
        this.enemy = enemy;
        this.imageView = enemy.getImageView();
        this.controller = controller;

        setCycleDuration(Duration.millis(MOVE_DURATION));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
        imageView.setViewport(new Rectangle2D(MOVE_X, MOVE_Y, ENEMY_WIDTH, ENEMY_HEIGHT));
        adjustImage();
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % SPRITES_COUNT) * ENEMY_WIDTH  + MOVE_X;
            imageView.setViewport(new Rectangle2D(coordinateX, MOVE_Y, ENEMY_WIDTH, ENEMY_HEIGHT));
            adjustImage();
            currentSprite = index;
            enemy.moveEnemy();
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

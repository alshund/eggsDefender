package view.enemy;

import controller.Controller;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.animations.AnimationObserver;
import view.egg.Egg;

import java.util.List;

import static view.enemy.EnemySpriteData.*;
import static view.enemy.EnemySpriteData.FIT_WIDTH;

public class AttackAnimation extends Transition implements AnimationObserver{
    private int enemyId;
    private ImageView imageView;
    private int eggId;
    private Controller controller;
    private double currentSprite = 0;

    public AttackAnimation(Enemy enemy, Controller controller) {
        this.enemyId = enemy.getId();
        this.imageView = enemy.getImageView();
        this.controller = controller;
        setCycleDuration(Duration.millis(200));
        setInterpolator(Interpolator.LINEAR);
        setCycleCount(Animation.INDEFINITE);
    }

    @Override
    protected void interpolate(double fraction) {
        final int index = Math.min((int) Math.floor(fraction * SPRITES_COUNT), SPRITES_COUNT - 1);
        if (index != currentSprite) {
            final int coordinateX = (index % SPRITES_COUNT) * ENEMY_WIDTH + ATTACK_X;
            imageView.setViewport(new Rectangle2D(coordinateX, ATTACK_Y, ENEMY_WIDTH, ENEMY_HEIGHT));
            adjustImage();
            currentSprite = index;
            controller.enemyAttack(enemyId, eggId);
        }
    }

    private void adjustImage() {
        imageView.setFitHeight(FIT_HEIGHT);
        imageView.setFitWidth(FIT_WIDTH);
        imageView.preserveRatioProperty();
        imageView.smoothProperty();
    }

    public void setEggId(int eggId) {
        this.eggId = eggId;
    }

    @Override
    public void resume() {
        this.play();
    }
}

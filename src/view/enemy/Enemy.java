package view.enemy;

import controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.egg.Egg;

import java.io.InputStream;
import java.util.List;

public class Enemy {
    private ImageView imageView;
    private MoveAnimation moveAnimation;
    private AttackAnimation attackAnimation;
    private HurtAnimation hurtAnimation;
    private DieAnimation dieAnimation;
    private IdleAnimation idleAnimation;

    private int id;

    private Controller controller;

    private List<Egg> idleEggs;

    public Enemy(Controller controller, List<Egg> idleEggs, int id) {
        this.controller = controller;
        this.idleEggs = idleEggs;
        this.id = id;
        InputStream inputStream = Enemy.class.getResourceAsStream(EnemySpriteData.Path);
        Image image = new Image(inputStream);
        imageView = new ImageView(image);

        moveAnimation = new MoveAnimation(this, controller);
        attackAnimation = new AttackAnimation(this, controller);
        hurtAnimation = new HurtAnimation(imageView, moveAnimation);
        dieAnimation = new DieAnimation(imageView, moveAnimation);
        idleAnimation = new IdleAnimation(imageView);
    }

    public void moveEnemy() {

        if (!isAttack() && !isWin()) {
            controller.moveEnemy(id);
        } else if (isWin()) {
            controller.enemyWin();
        }
    }

    private boolean isAttack() {

        for (Egg egg : idleEggs) {
            if (egg.getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                playAttackAnimation(egg.getId());
                return true;
            }
        }
        return false;
    }

    private boolean isWin() {

        return imageView.getTranslateX() == 0;
    }

    public void playMoveAnimation() {

        attackAnimation.pause();
        hurtAnimation.setAnimationObserver(moveAnimation);
        dieAnimation.setAnimationObserver(moveAnimation);
        moveAnimation.play();
    }

    public void playHurtAnimation() {

        stopCurrentAnimation();
        hurtAnimation.play();
    }

    public void playDieAnimation() {

        hurtAnimation.pause();
        stopCurrentAnimation();
        dieAnimation.play();
    }

    public void playAttackAnimation(int eggId) {

        moveAnimation.pause();
        hurtAnimation.setAnimationObserver(attackAnimation);
        dieAnimation.setAnimationObserver(attackAnimation);
        attackAnimation.setEggId(eggId);
        attackAnimation.play();
    }

    public void playIdleAnimation() {

        stopCurrentAnimation();
        hurtAnimation.setAnimationObserver(idleAnimation);
        dieAnimation.setAnimationObserver(idleAnimation);
        idleAnimation.play();
    }

    public void stopCurrentAnimation() {
        if (dieAnimation.getAnimationObserver() == moveAnimation) {
            moveAnimation.pause();
        } else if (dieAnimation.getAnimationObserver() == attackAnimation) {
            attackAnimation.pause();
        } else if (dieAnimation.getAnimationObserver() == idleAnimation) {
            idleAnimation.pause();
        }
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

    public void setIdleEggs(List<Egg> idleEggs) {
        this.idleEggs = idleEggs;
    }
}

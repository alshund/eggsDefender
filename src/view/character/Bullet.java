package view.character;

import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.enemy.Enemy;

import java.io.InputStream;
import java.util.List;

public class Bullet {
    private ImageView imageView;
    private AnimationTimer tracing;

    private int id;

    public Bullet() {
        InputStream inputStream = Bullet.class.getResourceAsStream(CharacterSpriteData.BULLET_PATH);
        Image image = new Image(inputStream);
        imageView = new ImageView(image);
        adjustImage();
    }

    private void adjustImage() {
        imageView.setFitHeight(CharacterSpriteData.BULLET_FIT_HEIGHT);
        imageView.setFitWidth(CharacterSpriteData.BULLET_FIT_WIDTH);
        imageView.preserveRatioProperty();
        imageView.smoothProperty();
    }

    public void startTracing(Controller controller, List<Enemy> enemyWave, double limitWidth) {
        tracing = new AnimationTimer() {
            @Override
            public void handle(long now) {
                imageView.setTranslateX(imageView.getTranslateX() + CharacterSpriteData.BULLET_SPEED);
                if (isMiss(limitWidth, controller) || isHurt(enemyWave, controller)) {
                    this.stop();
                }
            }
        };
        tracing.start();
    }

    private boolean isMiss(double limitWidth, Controller controller) {
        if (imageView.getTranslateX() == limitWidth) {
            controller.missAttack(id);
            return true;
        }
        return false;
    }

    private boolean isHurt(List<Enemy> enemyWave, Controller controller) {
        for (Enemy enemy : enemyWave) {
            if (enemy.getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                controller.damageEnemy(Bullet.this.getId(), enemy.getId());
                return true;
            }
        }
        return false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package view;

import controller.Controller;
import controller.exceptions.NoSuchElement;
import controller.listeners.CharacterListener;
import controller.listeners.FreezingHandler;
import controller.listeners.PriceBonusHandler;
import controller.listeners.TaskHandler;
import controller.observers.Observer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.character.Bullet;
import view.character.Character;
import view.egg.Egg;
import view.enemy.Enemy;

import java.util.LinkedList;
import java.util.List;

public class BattleField implements Observer {
    public static final String MONEY_COUNTER_PATH = "/Money.png";
    public static final String ARENA_BACKGROUND_PATH = "/Back.png";
    public static final String GAME_OVER_PATH = "/Gameover.png";
    public static final String FREEZING_PATH = "/Freezing.png";
    public static final String PRICE_BONUS_PATH = "/PriceBonus.png";

    public static final String NO_BULLET_EXCEPTION = "There is no bullet on the GUI!";
    public static final String NO_ENEMY_EXCEPTION = "There is no enemy on the GUI!";
    public static final String NO_EGG_EXCEPTION = "There is no egg on the GUI!";

    public static final double MIN_WIDTH = 1000;
    public static final double MIN_HEIGHT = 600;
    public static final int EGG_COLUMN = 1;

    private Scene scene;
    private Text moneyCounter = new Text();
    private BorderPane game = new BorderPane();
    private GridPane arena = new GridPane();
    private Character character = new Character();

    private Button freezingButton = new Button();
    private Button priceBonusButton = new Button();
    private Button bulletBonusButton = new Button();

    private double[] columns = {10, 10, 80};
    private double[] rows = {20, 20, 20, 20, 20};

    private Controller controller;

    private List<Egg> idleEggs = new LinkedList<>();
    private List<Bullet> tracingBullets = new LinkedList<>();
    private List<Enemy> enemyWave = new LinkedList<>();

    public BattleField(Stage primaryStage, Controller controller) {

        this.controller = controller;
        this.controller.setObserver(this);
        scene = new Scene(createBattlePane(), MIN_WIDTH, MIN_HEIGHT);
        scene.setOnKeyPressed(new CharacterListener(arena, character, controller));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.startGame();
    }

    private BorderPane createBattlePane() {

        game = new BorderPane();
        game.setTop(createControlPanel());
        game.setCenter(createArena());
        return game;
    }

    private Pane createControlPanel() {

        HBox controlPane = new HBox();
        controlPane.getChildren().add(createFreezingButton());
        controlPane.getChildren().add(createPriceBonusButton());
        controlPane.getChildren().add(createMoneyCounter());
        return controlPane;
    }

    private StackPane createMoneyCounter() {

        StackPane stackPane = new StackPane();
        ImageView moneyCounterImageView = createImageForButton(MONEY_COUNTER_PATH);
        stackPane.getChildren().addAll(moneyCounterImageView, moneyCounter);
        StackPane.setAlignment(moneyCounterImageView, Pos.CENTER);
        StackPane.setAlignment(moneyCounter, Pos.CENTER);
        return stackPane;
    }

    private Button createFreezingButton() {

        StackPane stackPane = new StackPane();
        ImageView freezingButtonImageView = createImageForButton(FREEZING_PATH);
        freezingButton.setOnAction(new FreezingHandler(controller));
        freezingButton.setGraphic(freezingButtonImageView);
        freezingButton.setFocusTraversable(false);
        stackPane.getChildren().addAll(freezingButton);
        return freezingButton;
    }

    public Button createPriceBonusButton() {

        StackPane stackPane = new StackPane();
        ImageView priceBonusButtonImageView = createImageForButton(PRICE_BONUS_PATH);
        priceBonusButton.setOnAction(new PriceBonusHandler(controller));
        priceBonusButton.setGraphic(priceBonusButtonImageView);
        priceBonusButton.setFocusTraversable(false);
        stackPane.getChildren().addAll(priceBonusButton);
        return priceBonusButton;
    }

    public Button createBulletBonusButton() {

        return null;
    }

    private ImageView createImageForButton(String path) {

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        return imageView;
    }

    private GridPane createArena() {

        addArenaRows(rows);
        addArenaColumns(columns);
        arena.setBackground(createBackground(ARENA_BACKGROUND_PATH));
        addCharacter();
        addEggs();
        return arena;
    }

    private Background createBackground(String path) {

        BackgroundSize backgroundSize = createBackgroundSize();
        BackgroundImage backgroundImage = createBackgroundImage(path, backgroundSize);
        Background background = new Background(backgroundImage);
        return background;
    }

    private BackgroundSize createBackgroundSize() {

        return new BackgroundSize(MIN_WIDTH, MIN_HEIGHT,
                true, true, true, true);
    }

    private BackgroundImage createBackgroundImage(String imagePath, BackgroundSize backgroundSize) {

        Image image = new Image(BattleField.class.getResourceAsStream(imagePath));
        return new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
    }

    private void addCharacter() {

        ImageView imageView = character.getImageView();
        arena.getChildren().add(imageView);
        character.start();
    }

    private void addEggs() {

        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            Egg egg = new Egg(rowIndex);
            idleEggs.add(egg);
            arena.add(egg.getImageView(), EGG_COLUMN, rowIndex);
            GridPane.setHalignment(egg.getImageView(), HPos.CENTER);
            egg.idle();
        }
    }

    private void addArenaRows(double...percentHeights) {

        for (double percentHeight : percentHeights) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(percentHeight);
            row.setVgrow(Priority.ALWAYS);
            arena.getRowConstraints().add(row);
        }
    }

    private void addArenaColumns(double...percentWidths) {

        for (double percentWidth : percentWidths) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(percentWidth);
            column.setHgrow(Priority.ALWAYS);
            arena.getColumnConstraints().add(column);
        }
    }

    @Override
    public void changeMoneyCounter(int money) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                moneyCounter.setText(String.valueOf(money));
            }
        });
    }

    @Override
    public void moveCharacter(double speed) {

        character.move(speed);
    }

    @Override
    public void characterAttack(double speed, int bulletId) {

        Bullet bullet = character.attack();
        bullet.setId(bulletId);
        tracingBullets.add(bullet);
        arena.getChildren().add(bullet.getImageView());
        setBulletPosition(bullet);
        bullet.startTracing(controller, enemyWave, MIN_WIDTH);
    }

    private void setBulletPosition(Bullet bullet) {

        bullet.getImageView().setTranslateX(character.getBulletCoordinateX());
        bullet.getImageView().setTranslateY(character.getBulletCoordinateY());
    }

    @Override
    public void attackDone(int bulletId) {

        try {
            Bullet bullet = getBulletById(bulletId);
            tracingBullets.remove(bullet);
            arena.getChildren().remove(bullet.getImageView());
        } catch (NoSuchElement noSuchElement) {
            noSuchElement.printStackTrace();
        }
    }

    @Override
    public void creteEnemy(double speed, int enemyId) {

        Task<Enemy> task = new Task<Enemy>() {
            @Override
            protected Enemy call() throws Exception {
                return new Enemy(controller, idleEggs, enemyId);
            }
        };
        task.setOnSucceeded(new TaskHandler(task, enemyWave, arena));
        new Thread(task).start();
    }

    @Override
    public void moveEnemy(int enemyId, double speed) {

        try {
            Enemy enemy = getEnemyById(enemyId);
            double newCoordinateX = enemy.getImageView().getTranslateX() - speed;
            enemy.getImageView().setTranslateX(newCoordinateX);
        } catch (NoSuchElement noSuchElement) {
            noSuchElement.printStackTrace();
        }
    }

    @Override
    public void hurtEnemy(int bulletId, int enemyId) {

        try {
            Enemy enemy = getEnemyById(enemyId);
            attackDone(bulletId);
            enemy.playHurtAnimation();
        } catch (NoSuchElement noSuchElement) {
            noSuchElement.printStackTrace();
        }
    }

    @Override
    public void killEnemy(int bulletId, int enemyId) {

        try {
            Enemy enemy = getEnemyById(enemyId);
            attackDone(bulletId);
            enemyWave.remove(enemy);
            enemy.playDieAnimation();
        } catch (NoSuchElement noSuchElement) {
            noSuchElement.printStackTrace();
        }
    }

    @Override
    public void killEgg(int enemyId, int eggId) {

        try {
            Enemy enemy = getEnemyById(enemyId);
            Egg egg = getEggById(eggId);
            idleEggs.remove(egg);
            egg.die();
            enemy.playMoveAnimation();
        } catch (NoSuchElement noSuchElement) {
            noSuchElement.printStackTrace();
        }
    }

    @Override
    public void enemyWin() {

        game.getChildren().clear();
        game.setBackground(createBackground(GAME_OVER_PATH));
    }

    @Override
    public void freezingEnemy() {

        for (Enemy enemy : enemyWave) {
            enemy.playIdleAnimation();
        }
    }

    private Bullet getBulletById(int bulletId) throws NoSuchElement {

        for (Bullet bullet : tracingBullets) {
            if (bullet.getId() == bulletId) {
                return bullet;
            }
        }
        throw new NoSuchElement(NO_BULLET_EXCEPTION);
    }

    private Enemy getEnemyById(int enemyId) throws NoSuchElement {

        for (Enemy enemy : enemyWave) {
            if (enemy.getId() == enemyId) {
                return enemy;
            }
        }
        throw new NoSuchElement(NO_ENEMY_EXCEPTION);
    }

    public Egg getEggById(int eggId) throws NoSuchElement {

        for (Egg egg : idleEggs) {
            if (egg.getId() == eggId) {
                return egg;
            }
        }
        throw new NoSuchElement(NO_EGG_EXCEPTION);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
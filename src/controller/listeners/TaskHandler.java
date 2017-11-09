package controller.listeners;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import view.BattleField;
import view.enemy.Enemy;
import view.enemy.EnemySpriteData;

import java.util.List;

public class TaskHandler implements EventHandler<WorkerStateEvent> {
    public static final int ROW_NUMBER = 5;

    private Task<Enemy> task;
    private List<Enemy> enemyWave;
    private GridPane arena;

    public TaskHandler(Task task, List<Enemy> enemyWave, GridPane arena) {

        this.task = task;
        this.enemyWave = enemyWave;
        this.arena = arena;
    }

    @Override
    public void handle(WorkerStateEvent event) {

        Enemy enemy = task.getValue();
        enemyWave.add(enemy);
        arena.getChildren().add(enemy.getImageView());
        enemy.getImageView().setTranslateX(BattleField.MIN_WIDTH);
        enemy.getImageView().setTranslateY(selectRow() * EnemySpriteData.FIT_HEIGHT);
        enemy.playMoveAnimation();
    }

    public int selectRow() {

        return (int) (Math.random() * ROW_NUMBER);
    }
}
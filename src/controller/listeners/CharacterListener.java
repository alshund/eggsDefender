package controller.listeners;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import view.character.Bullet;
import view.character.Character;

public class CharacterListener implements EventHandler<KeyEvent> {
    private GridPane arena;
    private Character character;
    private Controller controller;

    public CharacterListener(GridPane arena, Character character, Controller controller) {
        this.arena = arena;
        this.character = character;
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            controller.moveCharacterUp();
        }
        else if (event.getCode() == KeyCode.DOWN) {
            controller.moveCharacterDown();
        }
        else if (event.getCode() == KeyCode.SPACE) {
            controller.characterAttack();
        }
    }
}

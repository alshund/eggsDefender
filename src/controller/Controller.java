package controller;

import controller.observers.Observer;
import model.BattleLogic;

public class Controller {
    private BattleLogic battleLogic;

    public Controller(BattleLogic battleLogic) {
        this.battleLogic = battleLogic;
    }

    public void setObserver(Observer observer) {
        battleLogic.setObserver(observer);
    }

    public void startGame() {
        battleLogic.startGame();
    }

    public void moveCharacterUp() {
        battleLogic.moveCharacterUp();
    }

    public void moveCharacterDown() {
        battleLogic.moveCharacterDown();
    }

    public void characterAttack() {
        battleLogic.characterAttack();
    }

    public void missAttack(int bulletId) {
        battleLogic.missAttack(bulletId);
    }

    public void moveEnemy(int enemyId) {
        battleLogic.moveEnemy(enemyId);
    }

    public void damageEnemy(int bulletId, int enemyId) {
        battleLogic.damageEnemy(bulletId, enemyId);
    }

    public void enemyAttack(int enemyId, int eggId) {
        battleLogic.enemyAttack(enemyId, eggId);
    }

    public void enemyWin() {
        battleLogic.enemyWin();
    }

    public void freezingEnemy() {
        battleLogic.freezingEnemy();
    }

    public void priceBonus() {
        battleLogic.priceBonus();
    }
}

package controller.observers;

public interface Observer {
    void changeMoneyCounter(int money);
    void moveCharacter(double speed);
    void characterAttack(double speed, int bulletId);
    void attackDone(int bulletId);
    void creteEnemy(double speed, int enemyId);
    void moveEnemy(int enemyId, double speed);
    void hurtEnemy(int bulletId, int enemyId);
    void killEnemy(int bulletId, int enemyId);
    void killEgg(int enemyId, int eggId);
    void enemyWin();
    void freezingEnemy();
}

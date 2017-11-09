package model;

import controller.observers.Observable;
import controller.observers.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BattleLogic implements Observable {
    private final int ROW_NUMBER = 5;

    private Observer observer;
    private Character character;
    private Timer enemyGenerator = new Timer();
    private Timer moneyGenerator = new Timer();

    private List<Egg> idleEggs = new LinkedList<>();
    private List<Bullet> tracingBullets = new LinkedList<>();
    private List<Enemy> enemyWave = new LinkedList<>();

    private Bonuses bonuses = new Bonuses();
    private int sleepTime = 5000;
    private int collectedDelay = 20000;
    private int bulletId = 0;
    private int enemyId = 0;

    public BattleLogic() {

        character = new Character();
        setEggs();
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void setEggs() {

        for (int index = 0; index < ROW_NUMBER; index++) {
            Egg egg = new Egg();
            egg.setId(index);
            idleEggs.add(egg);
        }
    }

    public void startGame() {

        observer.changeMoneyCounter(character.getMoney());
        generateMobs();
        generateMoney();
    }

    public void generateMobs() {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Enemy enemy = createEnemy();
                enemyWave.add(enemy);
                observer.creteEnemy(enemy.getSpeed(), enemy.getId());
            }
        };
        enemyGenerator.schedule(timerTask, sleepTime, sleepTime);
    }

    public Enemy createEnemy() {
        return new Enemy(bonuses.getEnemyHealth(),
                bonuses.getEnemyDamage(),
                bonuses.getEnemySpeed(),
                enemyId++,
                bonuses.getEnemyPrice());
    }

    public void generateMoney() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int collectedMoney = getEggsMoney();
                character.setMoney(character.getMoney() + collectedMoney);
                observer.changeMoneyCounter(character.getMoney());
            }
        };
        moneyGenerator.schedule(timerTask, collectedDelay, collectedDelay);

    }

    public int getEggsMoney() {

        int collectedMoney = 0;
        for (Egg egg : idleEggs) {
            collectedMoney += egg.getValue();
        }
        return collectedMoney;
    }

    public void moveCharacterUp() {

        double characterUpSpeed = 0 - character.getSpeed();
        observer.moveCharacter(characterUpSpeed);
    }

    public void moveCharacterDown() {

        double characterDownSpeed = character.getSpeed();
        observer.moveCharacter(characterDownSpeed);
    }

    public void characterAttack() {

        Bullet bullet = character.attack();
        bullet.setId(bulletId++);
        tracingBullets.add(bullet);
        observer.characterAttack(bullet.getSpeed(), bullet.getId());
    }


    public void missAttack(int bulletId) {

        Bullet bullet = getBulletById(bulletId);
        tracingBullets.remove(bullet);
        observer.attackDone(bulletId);
    }

    public void moveEnemy(int enemyId) {

        Enemy enemy = getEnemyById(enemyId);
        observer.moveEnemy(enemyId, enemy.getSpeed());
    }

    public void damageEnemy(int bulletId, int enemyId) {

        Bullet bullet = getBulletById(bulletId);
        Enemy enemy = getEnemyById(enemyId);
        enemy.setHealth(enemy.getHealth() - bullet.getDamage());
        if (enemy.getHealth() <= 0) {
            killEnemy(enemy, bulletId, enemyId);
        } else {
            observer.hurtEnemy(bulletId, enemyId);
        }
        tracingBullets.remove(bullet);
    }

    public void killEnemy(Enemy enemy, int bulletId, int enemyId) {
        enemyWave.remove(enemy);
        observer.killEnemy(bulletId, enemyId);
        character.setMoney(character.getMoney() + enemy.getPrice());
        observer.changeMoneyCounter(character.getMoney());
    }

    public void enemyAttack(int enemyId, int eggId) {

        Enemy enemy = getEnemyById(enemyId);
        Egg egg = getEggById(eggId);
        egg.setHealth(egg.getHealth() - enemy.getDamage());
        if (egg.getHealth() <= 0) {
            idleEggs.remove(egg);
            System.out.println(idleEggs.size());
            observer.killEgg(enemyId, eggId);
        }
    }

    public void enemyWin() {

        enemyGenerator.cancel();
        observer.enemyWin();
    }

    public void freezingEnemy() {

        if (character.getMoney() > bonuses.getFreezingValue()) {
            character.setMoney(character.getMoney() - bonuses.getFreezingValue());
            observer.freezingEnemy();
            observer.changeMoneyCounter(character.getMoney());
        }
    }

    public void priceBonus() {

        if (character.getMoney() > bonuses.getPriceBonusValue()) {
            character.setMoney(character.getMoney() - bonuses.getPriceBonusValue());
            observer.changeMoneyCounter(character.getMoney());
            doubleEnemyPrice();
        }
    }

    private void doubleEnemyPrice() {

        for (Enemy enemy : enemyWave) {
            enemy.setPrice(enemy.getPrice() * bonuses.getPriceBonusValue());
        }
    }

    public Bullet getBulletById(int bulletId) {

        for (Bullet bullet : tracingBullets) {
            if (bullet.getId() == bulletId) {
                return bullet;
            }
        }
        return null; //TODO: NULL
    }

    public Enemy getEnemyById(int enemyId) {

        for (Enemy enemy : enemyWave) {
            if (enemy.getId() == enemyId) {
                return enemy;
            }
        }
        return null; //TODO: NULL
    }

    public Egg getEggById(int eggId) {

        for (Egg egg : idleEggs) {
            if (egg.getId() == eggId) {
                return egg;
            }
        }
        return null;
    }
}
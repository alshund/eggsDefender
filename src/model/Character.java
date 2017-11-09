package model;

public class Character {
    private double speed = 10;
    private int attackDelay = 1000;
    private int money = 0;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Bullet attack() {
        return new Bullet();
    }
}

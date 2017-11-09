package model;

public class Bonuses {
    private int enemyHealth = 100;
    private int enemySpeed = 1;
    private int enemyDamage = 50;
    private int enemyPrice = 4;

    private int freezingValue = 20;
    private int priceBonusValue = 2;

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public int getEnemySpeed() {
        return enemySpeed;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public int getEnemyDamage() {
        return enemyDamage;
    }

    public void setEnemyDamage(int enemyDamage) {
        this.enemyDamage = enemyDamage;
    }

    public int getEnemyPrice() {
        return enemyPrice;
    }

    public void setEnemyPrice(int enemyPrice) {
        this.enemyPrice = enemyPrice;
    }

    public int getFreezingValue() {
        return freezingValue;
    }

    public void setFreezingValue(int freezingValue) {
        this.freezingValue = freezingValue;
    }

    public int getPriceBonusValue() {
        return priceBonusValue;
    }

    public void setPriceBonusValue(int priceBonusValue) {
        this.priceBonusValue = priceBonusValue;
    }
}

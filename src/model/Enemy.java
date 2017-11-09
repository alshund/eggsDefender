package model;

public class Enemy {
    private int health;
    private int damage;
    private int speed;
    private int id;
    private int price;

    public Enemy(int health, int damage, int speed, int id, int price) {
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.id = id;
        this.price = price;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{

    public Gun(int currentFirearm, int maxFirearm) {
        super(currentFirearm, maxFirearm);
    }

    protected Fireable createBullet() {
        return new Bullet();
    }
}

package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int currentFirearm;
    private int maxFirearm;

    public Firearm(int currentFirearm, int maxFirearm) {
        this.currentFirearm = currentFirearm;
        this.maxFirearm = maxFirearm;
    }

    public Firearm(int currentFirearmt){
        this.currentFirearm = currentFirearmt;
        this.maxFirearm = currentFirearmt;
    }

    public int getAmmo(){
        return this.currentFirearm;
    }

    public void reload(int newAmmo){
        if(this.currentFirearm == this.maxFirearm){
            return;
        }
        this.currentFirearm = (this.currentFirearm + newAmmo > this.maxFirearm) ? this.maxFirearm : this.currentFirearm + newAmmo;
    }

    public Fireable fire(){
        if(this.getAmmo() > 0) {
            this.currentFirearm--;
            return createBullet();
        }
        else{
            return null;
        }
    }

    protected abstract Fireable createBullet();
}

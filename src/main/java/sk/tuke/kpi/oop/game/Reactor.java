package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import java.util.HashSet;
import java.util.Set;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;

    private boolean isOnReactor;
    private boolean isRunning;
    private Animation normalOffAnimation;
    private Animation normalAnimation;
    private Animation hotReactorAnimation;
    private Animation brokenReactorAnimation;
    private Animation extinguishedReactorAnimation;

    private Set<EnergyConsumer> devices;

    // create animation object

    public Reactor() {
        damage = 0;
        temperature = 0;
        isOnReactor = false;
        isRunning = false;
        normalOffAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotReactorAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenReactorAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedReactorAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f, Animation.PlayMode.ONCE);

        devices = new HashSet<>();
        // set actor's animation to just created Animation object
        setAnimation(normalOffAnimation);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public boolean isOn() {
        return this.isOnReactor;
    }

    public boolean isRunning() {
        return isRunning;
    }
    @Override
    public void turnOn() {
        if(damage == 100) {
            return;
        }
        this.isOnReactor = true;
        updateAnimation();

        for (EnergyConsumer energyConsumer : devices){
            energyConsumer.setPowered(isOnReactor);
        }
    }

    @Override
    public void turnOff() {
        this.isOnReactor = false;
        updateAnimation();
        for (EnergyConsumer energyConsumer : devices){
            energyConsumer.setPowered(isOnReactor);
        }
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !this.isOn()) {
            return;
        }

        int DEPENDENCE = 40;

        if (damage > 66) {
            temperature += (int) Math.ceil(increment * 2);
        } else if (damage >= 33 && damage <= 66) {
            temperature += (int) Math.ceil(increment * 1.5);
        } else {
            temperature += increment;
        }

        if (temperature >= 2000) {
            this.damage = (temperature < 6000 ) ? (temperature - 2000) / DEPENDENCE : 100;
            if(this.damage == 100) {
                turnOff();
            }
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if(decrement < 0 || !this.isOn()) {
            return;
        }

        if(this.damage >= 50 && this.damage < 100) {
            this.temperature -= decrement / 2;
            if(this.temperature < 0) {
                this.temperature = 0;
            }
        }
        else if(this.damage < 50) {
            this.temperature -= decrement;
        }

        updateAnimation();
    }

    private void updateAnimation(){
        if(this.temperature >= 0 && this.temperature < 4000) {
            if(this.isOn()) {
                setAnimation(normalAnimation);
            }
            else {
                setAnimation(normalOffAnimation);
            }
        }
        else if(this.temperature >= 4000 && this.temperature < 6000) {
            setAnimation(hotReactorAnimation);
        }
        else if(this.temperature >= 6000) {
            setAnimation(brokenReactorAnimation);
        }
    }

    @Override
    public boolean repair() {
        if (this.damage > 0 && this.damage < 100) {
            if (this.damage >= 50) {
                this.damage -= 50;
            } else {
                this.damage = 0;
            }

            this.temperature = this.damage * 40 + 2000;
            if (this.temperature < 0) {
                this.temperature = 0;
            }

            updateAnimation();
            return true;
        }

        return false;
    }

    @Override
    public boolean extinguish() {
        if(this.temperature >= 6000 && this.damage >= 100) {
            this.temperature = 4000;
            setAnimation(extinguishedReactorAnimation);
            return true;
        }
        return false;
    }

    public void addDevice(EnergyConsumer energyConsumer) {
        if(energyConsumer == null) {
            return;
        }
        devices.add(energyConsumer);
        if(isOnReactor) {
            energyConsumer.setPowered(true);
        }
        else {
            energyConsumer.setPowered(false);
        }
    }

    public void removeDevice(EnergyConsumer energyConsumer) {
        if (energyConsumer == null) {
            return;
        }
        energyConsumer.setPowered(false);
        devices.remove(energyConsumer);
    }

    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

}


/*public void extinguishWith(@NotNull FireExtinguisher frExtinguisher) {
        if(frExtinguisher.getRemainingUses() != 0) {
            frExtinguisher.useWith(frExtinguisher);
            this.decreaseTemperature(4000);
            setAnimation(extinguishedReactorAnimation);
        }
    }*/

    /*public void repairWith(Hammer hammer) {
        if(this.damage > 0 && this.damage < 100 && hammer != null && hammer.getRemainingUses() != 0) {
            if(this.damage >= 50) {
                this.damage -= 50;
            }
            else {
                this.damage = 0;
            }

            this.temperature = this.damage * 40 + 2000;
            if(this.temperature < 0) {
                this.temperature = 0;
            }

            hammer.useWith(hammer);
            updateAnimation();
        }
    }*/



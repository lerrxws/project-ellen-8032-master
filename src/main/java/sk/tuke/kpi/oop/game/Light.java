package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;
    private boolean isTurnOn;
    private boolean isPowered;

    public Light() {
        this.isTurnOn = false;
        this.isPowered = false;
        lightOnAnimation = new Animation("sprites/light_on.png");
        lightOffAnimation = new Animation("sprites/light_off.png");
        setAnimation(lightOffAnimation);
    }

    @Override
    public boolean isOn() {
        return this.isTurnOn;
    }

    @Override
    public void turnOn(){
        if(!this.isTurnOn) {
            this.isTurnOn = true;
            updateAnimationLight();
        }
    }

    @Override
    public void turnOff(){
        if(this.isTurnOn) {
            this.isTurnOn = false;
            updateAnimationLight();
        }
    }
    public void toggle() {

        if(this.isTurnOn) {
            this.turnOff();
        }
        else {
            this.turnOn();
        }
        updateAnimationLight();
    }

    public void setPowered(boolean isPowered) {
        if(isPowered) {
            this.isPowered = true;
        }
        else {
            this.isPowered = false;
        }
        updateAnimationLight();
    }

    private void updateAnimationLight() {
        if(this.isTurnOn && this.isPowered) {
            setAnimation(lightOnAnimation);
        }
        else {
            setAnimation(lightOffAnimation);
        }
    }
}

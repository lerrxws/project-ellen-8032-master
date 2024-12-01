package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
public class Cooler extends AbstractActor implements Switchable{

    private boolean isOn;
    private Reactor reactor;
    private Animation coolerOn;
    private Animation coolerOff;

    public Cooler(Reactor reactor) {
        this.isOn = false;
        this.reactor = reactor;

        coolerOff = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.ONCE);
        coolerOn = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(coolerOff);
    }

    public Reactor getReactor() {
        return reactor;
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void turnOn() {
        isOn = true;
        setAnimation(coolerOn);
    }

    @Override
    public void turnOff() {
        isOn = false;
        setAnimation(coolerOff);
    }

    public void coolReactor() {
        if(this.isOn() && this.reactor != null) {
            this.reactor.decreaseTemperature(1);
        }
    }

    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}

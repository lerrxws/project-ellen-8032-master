package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    public void smartCool() {
        if(this.getReactor() == null) {
            return;
        }
        if(this.getReactor().getTemperature() < 1500) {
            super.turnOff();
        }
        if(this.getReactor().getTemperature() >= 2500) {
            super.turnOn();
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCool)).scheduleFor(this);
    }
}

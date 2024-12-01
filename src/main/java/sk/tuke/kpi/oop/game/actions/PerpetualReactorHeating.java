package sk.tuke.kpi.oop.game.actions;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int increaseTemperatue;

    public PerpetualReactorHeating(int increaseTemperatue) {
        this.increaseTemperatue = increaseTemperatue;
    }

    public void execute(float deltaTime) {
        if(super.getActor() != null) {
            super.getActor().increaseTemperature(increaseTemperatue);
        }
    }

}

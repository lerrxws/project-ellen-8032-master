package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<T extends Actor> extends AbstractActor implements Usable<T> {
    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    @Override
    public void useWith(T actor) {

        if (this.remainingUses > 0) {
            this.remainingUses -= 1;
            if (this.remainingUses == 0) {
                getScene().removeActor(this);
            }
        }
    }
}

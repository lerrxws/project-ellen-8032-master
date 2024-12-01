package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A> {

    public Drop() {
    }

    @Override
    public void execute(float deltaTime) {
        if(this.isDone() || getActor() == null) {
            if(!this.isDone()){
                setDone(true);
            }
            return;
        }

        Collectible A = this.getActor().getBackpack().peek();
        if(A == null) {
            setDone(true);
            return;
        }

        this.getActor().getScene().addActor(A, (this.getActor().getPosX() + this.getActor().getWidth()/2 - A.getWidth()/2), (this.getActor().getPosY() + this.getActor().getHeight()/2 - A.getHeight()/2));
        this.getActor().getBackpack().remove(A);
        setDone(true);
    }
}

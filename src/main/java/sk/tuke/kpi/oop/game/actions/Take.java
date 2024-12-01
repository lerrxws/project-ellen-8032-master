package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take<A extends Keeper> extends AbstractAction<A> {

    public Take() {
    }

    @Override
    public void execute(float deltaTime) {
        if(this.isDone() || getActor() == null) {
            if(!this.isDone()){
                setDone(true);
            }
            return;
        }

        Actor collectible = findCollectibleInCollision();

        if (collectible != null) {
            try {
                this.getActor().getBackpack().add((Collectible) collectible);
                this.getActor().getScene().removeActor(collectible);
            } catch (IllegalStateException exception) {
                this.getActor().getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
            }
        }

        setDone(true);
    }

    public Actor findCollectibleInCollision() {
        return this.getActor().getScene()
            .getActors()
            .stream()
            .filter(actor -> actor instanceof Collectible && getActor().intersects(actor))
            .findFirst()
            .orElse(null); // or handle the absence of Collectible as needed
    }
}

package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;


public class Energy extends AbstractActor implements Usable<Alive>, Collectible{
    public Energy() {
        setAnimation(new Animation("sprites/energy.png"));
    }

    @Override
    public void useWith(Alive alive) {
        if(alive == null){
            return;
        }
        alive.getHealth().restore();
        getScene().removeActor(this);
    }

    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}

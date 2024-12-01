package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Ammo extends AbstractActor implements Usable<Armed>, Collectible{
    public Ammo() {
        setAnimation(new Animation("sprites/ammo.png"));
    }

    @Override
    public void useWith(Armed armed) {
        if(armed == null){
            return;
        }
        armed.getFirearm().reload(50);
        getScene().removeActor(this);
    }

    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}

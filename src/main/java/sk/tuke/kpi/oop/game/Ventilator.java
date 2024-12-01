package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Ventilator extends AbstractActor implements Repairable {
    private boolean isBroken;
    private Animation ventilatorAnimation;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("Ventilator repaired", Ventilator.class);
    public Ventilator() {
        isBroken = false;
        ventilatorAnimation = new Animation("sprites/ventilator.png", 32, 32,  0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(ventilatorAnimation);
    }

    @Override
    public boolean repair() {
        if(!this.isBroken) {
            return false;
        }
        this.isBroken = false;
        ventilatorAnimation.play();

        return true;
    }

    @Override
    public boolean extinguish() {
        return false;
    }
}

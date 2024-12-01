package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean isActivated;
    private boolean isExploeded;
    private Animation bombActivated;
    private Animation explosion;

    public TimeBomb(float time) {
        this.time = time;
        this.isActivated = false;
        bombActivated = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        explosion = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);


        setAnimation(new Animation("sprites/bomb.png"));
    }

    public boolean isActivated()
    {
        return this.isActivated;
    }


    public  boolean isExploeded() {
        return isExploeded;
    }


    public void activate() {
        if(isActivated) {
            return;
        }
        this.isActivated = true;
        setAnimation(this.bombActivated);
        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::explodeBomb),
            new Invoke<>(this::removeBomb)
        ).scheduleFor(this);
    }

    private void removeBomb() {
        if(this.isExploeded()) {
            new When<>( //remove object after animation
                () -> this.explosion.getCurrentFrameIndex() >= 7,
                new Invoke<>(() -> Objects.requireNonNull(this.getScene()).removeActor(this))
            ).scheduleFor(this);
        }
    }

    protected void explodeBomb() {
        if(this.isActivated() && !isExploeded()){
            isExploeded=true;
            setAnimation(explosion);
        }
    }

}

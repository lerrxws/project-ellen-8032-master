package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    private Health health;
    private Behaviour<? super Alien> behaviour;
    public static final Topic<Alien> ALIEN_DIED = Topic.create("alien died", Alien.class);
    public Alien() {
        this(100, null);
    }

    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        health = new Health(healthValue);
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        health.onFatigued(() -> getScene().removeActor(this));
        this.behaviour = behaviour;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        if (behaviour != null) {
            behaviour.setUp(this);
        }

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attackAlive),
                new Wait<>(0.3f)
            )
        ).scheduleFor(this);
    }

    public void attackAlive() {
        Scene scene = getScene();
        if (scene == null) {
            return;
        }

        List<Actor> actors = scene.getActors();
        actors.stream()
            .filter(actor -> actor instanceof Alive
                && !(actor instanceof Enemy)
                && this.intersects(actor))
            .findFirst()
            .ifPresent(actor -> ((Alive) actor).getHealth().drain(2));
    }
}

package sk.tuke.kpi.oop.game.weapons;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;
    public Bullet() {
        this.speed = 4;
        setAnimation(new Animation("sprites/bullet.png"));
    }

    public void startedMoving(Direction direction) {
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void collidedWithWall() {
        getScene().removeActor(this);
    }

    public void shoot(){
        Scene scene = getScene();
        if (scene == null) {
            return;
        }

        List<Actor> actors = scene.getActors();
        actors.stream()
            .filter(actor -> actor instanceof Alive
                && !(actor instanceof Ripley)
                && this.intersects(actor))
            .findFirst()
            .ifPresent(actor -> {
                ((Alive) actor).getHealth().drain(15);
                collidedWithWall();
            });
    }

    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::shoot)
        ).scheduleFor(this);

    }

}

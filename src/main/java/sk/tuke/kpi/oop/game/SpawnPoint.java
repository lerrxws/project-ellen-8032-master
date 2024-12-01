package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class SpawnPoint extends AbstractActor {
    private int alienNum;
    private int count;
    private boolean isDone;

    public SpawnPoint(int alienNum) {
        this.alienNum = alienNum;
        this.count = 0;
        this.isDone = false;
        setAnimation(new Animation("sprites/spawn.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }

    public void spawnPoint(){
        new Loop<>(
            new ActionSequence<>(
                new When<>(
                    () -> !isDone,
                    //new Wait<>(3f),
                    new Invoke<>(this::spawn)
                )
                //new Wait<>(3f)
            )
        ).scheduleFor(this);
    }

    private void spawn() {
        if(count == this.alienNum){
            dispose();
            return;
        }

        int xCenter = this.getPosX() + this.getWidth() / 2;
        int yCenter = this.getPosY() + this.getHeight() / 2;
        Ellipse2D.Float ellipse = new Ellipse2D.Float(xCenter - 50, yCenter - 50, 100, 100);

        List<Actor> actorList = getScene().getActors();

        for (Actor actor : actorList) {
            if (actor instanceof Ripley && actor != this) {
                Rectangle2D ripley = new Rectangle2D.Float(actor.getPosX(), actor.getPosY(), actor.getWidth(), actor.getHeight());
                if ((ellipse.intersects(ripley) || ellipse.contains(ripley))) {
                    getScene().addActor(new Alien(), xCenter, yCenter);
                    count++;
                    break;
                }
            }
            new Wait<>(3f).scheduleFor(this);
        }
    }


    public void dispose() {
        this.isDone = false;
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.spawnPoint();
    }

}

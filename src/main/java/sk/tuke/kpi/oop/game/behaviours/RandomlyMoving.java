package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable>{
    @Override
    public void setUp(Movable actor) {
        if(actor == null){
            return;
        }

        new Loop<>(
            new ActionSequence<>(
                new Wait<>(3f),
                new Invoke<>(this::randomMove)
            )).scheduleFor(actor);

    }

    private void randomMove(Movable actor){
        if(actor == null){
            return;
        }
        Random random = new Random();

        Direction[] directions = Direction.values();
        Direction direction = directions[random.nextInt(directions.length)];
        actor.getAnimation().setRotation(direction.getAngle());

        new Move<Movable>(direction, 3).scheduleFor(actor);
    }
}

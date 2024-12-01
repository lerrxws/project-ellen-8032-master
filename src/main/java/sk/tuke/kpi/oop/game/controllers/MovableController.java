package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable movableActor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private Set<Direction> keysEntered = new HashSet<>();
    private Move<Movable> move;
    private Disposable disposable;

    public MovableController(Movable movableActor) {
        this.movableActor = movableActor;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (this.keyDirectionMap.containsKey(key)) {
            Direction newDirection = keyDirectionMap.get(key);
            keysEntered.add(newDirection);

            if (move != null) {
                stoppedMoving();
            }

            newDirection = Direction.NONE;
            for (Direction direction : this.keysEntered) {
                if (direction != newDirection) {
                    newDirection = newDirection.combine(direction);
                }
            }

            this.move = new Move<>(newDirection, Float.MAX_VALUE);
            disposable = this.move.scheduleFor(this.movableActor);
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (this.keyDirectionMap.containsKey(key)) {
            Direction newDirection = keyDirectionMap.get(key);
            keysEntered.remove(newDirection);

            stoppedMoving();
            if(keysEntered.isEmpty()) return ;
            newDirection = Direction.NONE;
            for (Direction direction : this.keysEntered) {
                if (direction != newDirection) {
                    newDirection = newDirection.combine(direction);
                }
            }

            move = new Move<Movable>(newDirection, Float.MAX_VALUE);
            disposable = move.scheduleFor(movableActor);
        }
    }


    public void stoppedMoving() {
        if (move == null) {
            return;
        }
        move.stop();
        move = null;
        disposable.dispose();
    }

}

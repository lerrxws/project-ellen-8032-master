package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class  Move<A extends Movable> implements Action<A> {

    private A actor;
    private boolean isDone;
    private Direction direction;
    private float duration;
    private boolean isFirstExecute;
    public Move(Direction direction, float duration) {
        actor = null;
        isDone = false;
        isFirstExecute = true;
        this.direction = direction;
        this.duration = duration;
        // implementacia konstruktora akcie
    }

    public Move(Direction direction) {
        this(direction, 0);
        // implementacia konstruktora akcie
    }

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void reset() {
        this.isDone = false;
        this.isFirstExecute = true;
    }

    public void execute(float deltaTime) {
        if(actor == null || getActor() == null){
            return;
        }

        this.duration -= deltaTime;

        if(isDone()){
            return;
        }

        if(!this.isDone() && this.isFirstExecute) {
            actor.startedMoving(direction);
            this.isFirstExecute = false;
        }

        // якщо що написати як суму делтаЧасу - тривалість і порівняти це з 1e-5
        //Math.abs(sumDeltaTime - duration) < 1e - 5 => ...

        if(this.duration <= 0) {
            stop();
        }
        else {
            int oldX, oldY, newX, newY;
            oldX = this.actor.getPosX();
            oldY = this.actor.getPosY();
            newX = oldX + this.direction.getDx() * this.actor.getSpeed();
            newY = oldY + this.direction.getDy() * this.actor.getSpeed();
            this.actor.setPosition(newX, newY);
            if(this.getActor().getScene().getMap().intersectsWithWall(this.actor)) {
                this.actor.setPosition(oldX, oldY);
                this.actor.collidedWithWall();
            }
        }

    }

    public void stop() {
        if(this.actor != null){
            this.actor.stoppedMoving();
        }
        this.completeExecution();
    }

    private void completeExecution() {
        this.isDone = true;
        this.isFirstExecute = false;
    }
}

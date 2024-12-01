package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;




public class Fire<A extends Armed> extends AbstractAction<A>{

    @Override
    public void execute(float deltaTime) {
        if (this.getActor() == null || isDone()) {
            if(!this.isDone()){
                setDone(true);
            }
            return;
        }

        Fireable fireable = this.getActor().getFirearm().fire();
        if (fireable == null) {
            setDone(true);
            return;
        }

        Direction direction = Direction.fromAngle(this.getActor().getAnimation().getRotation());
        int x = direction.getDx();
        int y = direction.getDy();

        //int width = fireable.getWidth();
        //int height = fireable.getHeight();

        int pX = this.getActor().getPosX();
        int pY = this.getActor().getPosY();

        this.getActor().getScene().addActor(fireable, pX + 8 + x * 8 * 3, pY + + 8 + y * 8 * 3);
        fireable.startedMoving(direction);
        Move<Fireable> moveAction = new Move<>(direction, Float.MAX_VALUE);
        moveAction.scheduleFor(fireable);

        setDone(true);
    }

}

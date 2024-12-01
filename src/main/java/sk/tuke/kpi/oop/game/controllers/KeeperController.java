package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper actor;

    public KeeperController(Keeper keeper) {
        this.actor = keeper;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if(key == Input.Key.BACKSPACE){
            new Drop<>().scheduleFor(actor);
        }
        if(key == Input.Key.S){
            new Shift<>().scheduleFor(actor);
        }
        if(key == Input.Key.ENTER){
            new Take<>().scheduleFor(actor);
        }
        if(key == Input.Key.U){
            Usable<?> usable = (Usable<?>) actor.getScene().getActors().stream().filter(Usable.class::isInstance).filter(actor::intersects).findFirst().orElse(null);
            if(usable != null) {
                new Use<>(usable).scheduleForIntersectingWith(actor);
            }
        }
        if(key == Input.Key.B){
            Object topItem = actor.getBackpack().peek();

            if (topItem instanceof Usable) {
                Usable<?> usable = (Usable<?>) topItem;
                new Use<>(usable).scheduleForIntersectingWith(actor);
            }
        }
    }
}

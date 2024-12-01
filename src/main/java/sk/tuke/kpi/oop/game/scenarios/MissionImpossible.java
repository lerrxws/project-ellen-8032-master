package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {
        public Actor create(String type, String name){
            if(name == null) {
                return null;
            }

            if(name.equals("ellen")){
                return new Ripley();
            }
            if(name.equals("energy")) {
                return new Energy();
            }
            if(name.equals("door")) {
                return new LockedDoor();
            }
            if(name.equals("access card")) {
                return new AccessCard();
            }
            if(name.equals("ventilator")) {
                return new Ventilator();
            }
            if(name.equals("locker")) {
                return new Locker();
            }

            return null;
        }
    }

    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        scene.follow(ripley);

        MovableController movableController = new MovableController(ripley);
        Disposable move = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        Disposable keep = scene.getInput().registerListener(keeperController);

        scene.getGame().pushActorContainer(ripley.getBackpack());

    }

    @Override
    public void sceneUpdating(Scene scene){
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        ripley.showRipleyState();
    }
}

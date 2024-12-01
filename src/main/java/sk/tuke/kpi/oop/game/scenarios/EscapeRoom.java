package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.MotherAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class EscapeRoom implements SceneListener {
    public static class Factory implements ActorFactory {
        public Actor create(String type, String name) {
            if (name == null) {
                return null;
            }

            if (name.equals("ellen")) {
                return new Ripley();
            }
            if (name.equals("energy")) {
                return new Energy();
            }
            if (name.equals("back door")) {
                return new Door("back door", Door.Orientation.HORIZONTAL);
            }
            if (name.equals("front door")) {
                return new Door("front door", Door.Orientation.VERTICAL);
            }
            if(name.equals("alien")){
                return new Alien(100, new RandomlyMoving());
            }
            if(name.equals("alien mother")){
                return new MotherAlien(200, new RandomlyMoving());
            }
            if(name.equals("ammo")){
                return new Ammo();
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
        ShooterController shooterController = new ShooterController(ripley);
        Disposable shoot = scene.getInput().registerListener(shooterController);

        scene.getGame().pushActorContainer(ripley.getBackpack());
    }
}

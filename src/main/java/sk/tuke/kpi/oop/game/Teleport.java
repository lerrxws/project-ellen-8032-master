package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private boolean isActive;
    private Teleport destination;

    public Teleport() {
        this(null);
    }

    public Teleport(Teleport destination) {
        if(this == destination) {
            return;
        }

        this.destination = destination;
        setAnimation(new Animation("sprites/lift.png"));
        isActive = false;
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destination) {
        if(this == destination) {
            return;
        }

        this.destination = destination;
    }

    public void teleportPlayer(Player player) {
        player.setPosition(getPosX() + (getWidth() - player.getWidth()) / 2, getPosY() + (getHeight() - player.getHeight()) / 2);
        isActive = false;
    }

    @Override
    public void addedToScene(Scene scene) {
        if(scene == null) {
            return;
        }
        super.addedToScene(scene);
        initialTeleportation();
    }

    private void initialTeleportation() {
        new When<>(() -> {
            if (destination == null || getScene() == null) {
                return false;
            }
            if (!isActive && !activate()) {
                return false;
            }
            Player player = getScene().getFirstActorByType(Player.class);
            if (player == null) {
                return false;
            }
            int xCenterP = player.getPosX() + player.getWidth() / 2;
            int yCenterP = player.getPosY() + player.getHeight() / 2;
            return xCenterP > getPosX() && xCenterP < getPosX() + getWidth() && yCenterP > getPosY() && yCenterP < getPosY() + getHeight();
        }, new Invoke<>(() -> {
            destination.teleportPlayer(getScene().getFirstActorByType(Player.class));
            this.initialTeleportation();
        })).scheduleFor(this);
    }

    private boolean activate() {
        if (getScene() == null) {
            return false;
        }
        Player player = getScene().getFirstActorByType(Player.class);
        if (player == null) {
            return false;
        }

        int xCenterP = player.getPosX() + player.getWidth() / 2;
        int yCenterP = player.getPosY() + player.getHeight() / 2;
        isActive = !(xCenterP > getPosX() && xCenterP < getPosX() + getWidth() && yCenterP > getPosY() && yCenterP < getPosY() + getHeight());
        return isActive;
    }

}

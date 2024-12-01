package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.gamelib.map.MapTile;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private Animation openDoorAnimation;
    private Animation closeDoorAnimation;
    private boolean isOpen;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public enum Orientation {HORIZONTAL, VERTICAL};
    private Orientation orientation;

    private String doorPicV = "sprites/vdoor.png";
    private String doorPicH = "sprites/hdoor.png";
    //private String name;

    public Door() {
        this.isOpen = false;
        this.openDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        this.closeDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(closeDoorAnimation);
        orientation = Orientation.VERTICAL;
    }

    public Door(Orientation orientation) {
        this.isOpen = false;
        this.orientation = orientation;

        if(this.orientation == Orientation.HORIZONTAL){
            this.openDoorAnimation = new Animation(this.doorPicH, 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.closeDoorAnimation = new Animation(this.doorPicH, 32, 16, 0.1f, Animation.PlayMode.ONCE);
        }
        else{
            this.openDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.closeDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(closeDoorAnimation);
    }

    public Door(String name, Orientation orientation) {
        super(name);
        this.isOpen = false;
        this.orientation = orientation;
        //this.name = name;


        if(this.orientation == Orientation.HORIZONTAL){
            this.openDoorAnimation = new Animation(this.doorPicH, 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.closeDoorAnimation = new Animation(this.doorPicH, 32, 16, 0.1f, Animation.PlayMode.ONCE);
        }
        else{
            this.openDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            this.closeDoorAnimation = new Animation(this.doorPicV, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(closeDoorAnimation);
    }

    @Override
    public void open() {
        this.isOpen = true;
        setAnimation(openDoorAnimation);
        openDoorAnimation.play();
        openDoorAnimation.stop();

        if(this.orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        }
        else{
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(this.getScene()).getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        }

        this.getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        this.isOpen = false;
        setAnimation(closeDoorAnimation);
        closeDoorAnimation.play();
        closeDoorAnimation.stop();

        if(this.orientation == Orientation.VERTICAL){
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        }
        else{
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }

        this.getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpen) {
            this.close();
        } else {
            this.open();
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        if(isOpen()) open();
        else close();


    }

    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

}


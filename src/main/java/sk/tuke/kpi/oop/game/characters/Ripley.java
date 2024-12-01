package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;


public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Animation playerAnimation;
    private Animation playerDieAnimation;
    private int energy;
    private Backpack backpack;
    //private Disposable disposable;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    private Health health;
    private Firearm gun;

    public Ripley() {
        super();
        //this.energy = 100;
        backpack = new Backpack("Ripley's backpack", 10);

        playerAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        playerDieAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(playerAnimation);

        health = new Health(100);
        health.onFatigued(() -> {
            setAnimation(playerDieAnimation);
            this.getScene().getMessageBus().publish(RIPLEY_DIED, this);
        });

        gun = new Gun(150, 150);

    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public void startedMoving(Direction direction) {
        //this.getAnimation().setRotation(direction.getAngle());
        playerAnimation.setRotation(direction.getAngle());
        this.getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        this.getAnimation().stop();
    }

    /*public int getEnergy() {
        return this.energy;
    }

    public void setEnegry(int enegry) {
        this.energy = enegry;
    }*/

    @Override
    public Backpack getBackpack() {
        return (this.backpack != null) ? this.backpack : null;
    }

    public void showRipleyState(){
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = this.getScene().getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET*9;
        this.getScene().getGame().getOverlay().drawText("Health: " + this.energy + "  |", xTextPos, yTextPos);
        //this.getScene().getGame().getOverlay().drawText("Ammo: " + this.getFirearm().getAmmo(), xTextPos + 165, yTextPos);*/
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }
}

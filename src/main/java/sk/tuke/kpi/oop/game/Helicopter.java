package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter() {
        setAnimation(new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP));
    }

    public void search() {
        Player player = (Player) getScene().getLastActorByType(Player.class);

        if(player.getPosX() < this.getPosX()) {
            this.setPosition(this.getPosX() - 1, this.getPosY());
        }
        else if(player.getPosX() > this.getPosX()) {
            this.setPosition(this.getPosX() + 1, this.getPosY());
        }

        if(player.getPosY() < this.getPosY()) {
            this.setPosition(this.getPosX(), this.getPosY() - 1);
        }
        else if(player.getPosY() > this.getPosY()) {
            this.setPosition(this.getPosX(), this.getPosY() + 1);
        }

        if(this.intersects(player)) {
            player.setEnergy(player.getEnergy() - 1);
        }
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::search)).scheduleFor(this);
    }

}

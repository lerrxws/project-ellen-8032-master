package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float time) {
        super(time);
    }

    @Override
    protected void explodeBomb() {
        super.explodeBomb();
        int xCenter = this.getPosX() + this.getWidth() / 2;
        int yCenter = this.getPosY() + getHeight() / 2;
        Ellipse2D.Float ellipse = new Ellipse2D.Float(xCenter - 50, yCenter - 50, 100, 100);
        List<Actor> bombsList = getScene().getActors();

        for (Actor actor : bombsList) {
            if (actor instanceof ChainBomb && actor != this) {
                Rectangle2D chainBomb = new Rectangle2D.Float(actor.getPosX(), actor.getPosY(), actor.getWidth(), actor.getHeight());
                if (ellipse.intersects(chainBomb) || ellipse.contains(chainBomb)) {
                    ((ChainBomb) actor).activate();
                }
            }

        }
    }
}

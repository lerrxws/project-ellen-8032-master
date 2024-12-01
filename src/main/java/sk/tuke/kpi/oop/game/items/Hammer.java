package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {
    private Animation hammerAnimation;

    public Hammer() {
        super(1);
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }


    public void useWith(Repairable repairable) {
        if(this.getRemainingUses() != 0 && repairable != null && repairable.repair()) {
            super.useWith(repairable);
        }
    }

    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}

package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
public class Computer extends AbstractActor implements EnergyConsumer{
    private Animation normalAnimationComputer;
    private boolean isPowered;
    public Computer() {
        normalAnimationComputer = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        // set actor's animation to just created Animation object
        setAnimation(normalAnimationComputer);
        isPowered = false;
    }

    public int add(int a, int b){
        return (this.isPowered) ? a + b : 0;
    }

    public float add(float a, float b){
        return (this.isPowered) ? a + b : 0;
    }

    public int sub(int a, int b) {
        return (this.isPowered) ? a - b : 0;
    }

    public float sub(float a, float b){
        return (this.isPowered) ? a - b : 0;
    }

    public void setPowered(boolean isPowered){
        this.isPowered = isPowered;
        if(this.isPowered) {
            normalAnimationComputer.pause();
        }
        else if(!this.isPowered) {
            setAnimation(normalAnimationComputer);
        }
    }
}



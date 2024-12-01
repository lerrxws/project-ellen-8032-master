package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import java.util.Random;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Wait;

public class DefectiveLight extends Light implements Repairable {

    private Disposable disposable;
    private boolean isRepaired;

    public DefectiveLight() {
        super();// Use the constructor instead of a method with the class name
        this.isRepaired = false;
    }

    public void defectiveLight() {
        this.isRepaired = false;
        Random random = new Random();
        int randomNumber = random.nextInt(21);
        if (randomNumber == 1) {
            this.toggle();
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.disposable = new Loop<>(new Invoke<>(this::defectiveLight)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (isRepaired || this.disposable == null) {
            return false;
        }

        if (this.disposable != null) {
            this.disposable.dispose();
            this.isRepaired = true;
            new ActionSequence<>(
                new Wait<>(10),
                new Loop<>(new Invoke<>(this::defectiveLight))
            ).scheduleFor(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean extinguish() {
        return false;
    }
}

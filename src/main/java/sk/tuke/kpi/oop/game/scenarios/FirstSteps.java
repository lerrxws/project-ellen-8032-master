package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class FirstSteps implements SceneListener {
    private Ripley ripley;
    private MovableController moveTest;
    @Override
    public void sceneInitialized(Scene scene) {
        ripley = new Ripley();
        scene. addActor(ripley, 0, 0);


        moveTest = new MovableController(ripley);
        scene.getInput().registerListener(moveTest);


    }
}

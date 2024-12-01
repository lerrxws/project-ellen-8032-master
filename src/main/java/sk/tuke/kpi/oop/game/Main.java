package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;
import sk.tuke.kpi.oop.game.scenarios.TrainingGameplay;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania Game triedou GameApplication
        Game game = new GameApplication(windowSetup, new LwjglBackend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania Scene triedou World
        //Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));
        Scene scene = new World("escape-room", "maps/escape-room.tmx", new EscapeRoom.Factory());
        scene.addListener(new EscapeRoom());

        // pridanie sceny do hry
        game.addScene(scene);

        // spustenie hry
        game.start();

        game.getInput().onKeyPressed(Input.Key.ESCAPE, () -> game.stop());
    }
}

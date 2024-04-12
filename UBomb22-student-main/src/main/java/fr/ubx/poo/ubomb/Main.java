package fr.ubx.poo.ubomb;

import fr.ubx.poo.ubomb.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage)  {
        GameLauncherView launcher = new GameLauncherView(stage);
        Scene scene = new Scene(launcher);
        stage.setTitle("BombMan");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}
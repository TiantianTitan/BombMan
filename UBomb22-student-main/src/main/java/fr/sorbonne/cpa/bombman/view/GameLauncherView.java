package fr.sorbonne.cpa.bombman.view;

import fr.sorbonne.cpa.bombman.game.Game;
import fr.sorbonne.cpa.bombman.game.Level;
import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.launcher.GameLauncher;
import fr.sorbonne.cpa.bombman.engine.GameEngine;
import fr.sorbonne.cpa.bombman.game.Configuration;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;


public class GameLauncherView extends BorderPane {
    private final FileChooser fileChooser = new FileChooser();
    private boolean modeScore;
    private boolean mode2Players;

    public GameLauncherView(Stage stage)  {
        // Create menu
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem loadItem = new MenuItem("Load from file ...");
        MenuItem defaultItem = new MenuItem("SinglePlayerMode");
        MenuItem scoreModeItem = new MenuItem("scoreMode");
        MenuItem PlayersModeItem = new MenuItem("2PlayersMode");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuFile.getItems().addAll(
                loadItem, defaultItem, new SeparatorMenuItem(),
                scoreModeItem,PlayersModeItem,new SeparatorMenuItem(),
                exitItem);

        menuBar.getMenus().addAll(menuFile);
        this.setTop(menuBar);

        Text text = new Text("      BombMan      ");
        text.getStyleClass().add("message");
        Text textSchool = new Text("Sorbonne Université");
        textSchool.getStyleClass().add("message");
        VBox scene = new VBox();
        scene.getChildren().add(text);
        scene.getChildren().add(textSchool);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        scene.getStyleClass().add("message");
        this.setCenter(scene);

        // Load from file
        loadItem.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                // Implementation de lire le fichier
                java.util.Properties config = new Properties();;
                Reader in = null;
                try {
                    in = new FileReader(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    config.load(in);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Les varaibles qu'on a enregistré
                boolean compression = Boolean.parseBoolean(config.getProperty("compression"));
                int levels = Integer.parseInt(config.getProperty("levels"));
                int playerLives = Integer.parseInt(config.getProperty("playerLives"));
                // On va tester ici dans la future
                int bombBagCapacity = Integer.parseInt(config.getProperty("bombBagCapacity"));
                int monsterVelocity = Integer.parseInt(config.getProperty("monsterVelocity"));
                int playerInvisibilityTime = Integer.parseInt(config.getProperty("playerInvisibilityTime"));
                int monsterInvisibilityTime = Integer.parseInt(config.getProperty("monsterInvisibilityTime"));

                // Success for the player position initial '0x0' '1x1' '12x5'
                StringBuffer tmpx = new StringBuffer();
                StringBuffer tmpy = new StringBuffer();
                int ind = 0;
                while(config.getProperty("player").charAt(ind) != 'x'){
                    tmpx.append(config.getProperty("player").charAt(ind));
                    ind++;
                }
                ind++;
                for(int i = ind; i <config.getProperty("player").length();i++ ){
                    tmpy.append(config.getProperty("player").charAt(i));
                }
                int xPlayer = Integer.parseInt(tmpx.toString());
                int yPlayer = Integer.parseInt(tmpy.toString());

                // For the detail of the maps
                String string = config.getProperty("level1");
                Configuration configuration = new Configuration(new Position(xPlayer,yPlayer),bombBagCapacity,playerLives,playerInvisibilityTime,monsterVelocity,monsterInvisibilityTime);
                Game game = new Game(configuration,new Level(GameLauncher.load(string)));
                // Save all maps in the game
                game.stringMaps = new String[levels];
                for(int i = 0; i < levels; i++){
                    game.stringMaps[i] = config.getProperty("level"+(i+1));
                }
                modeScore = false;
                mode2Players = false;
                GameEngine engine = new GameEngine(game, stage,1,modeScore,mode2Players,false);
                engine.start();

            }
        });


        defaultItem.setOnAction(e -> {
            String filePath = "world/NewWorld.properties";
            File file = new File(filePath);
            if (file.exists()) {
                // Implementation de lire le fichier
                java.util.Properties config = new Properties();;
                Reader in = null;
                try {
                    in = new FileReader(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    config.load(in);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        
                // Les varaibles qu'on a enregistré
                boolean compression = Boolean.parseBoolean(config.getProperty("compression"));
                int levels = Integer.parseInt(config.getProperty("levels"));
                int playerLives = Integer.parseInt(config.getProperty("playerLives"));
                int bombBagCapacity = Integer.parseInt(config.getProperty("bombBagCapacity"));
                int monsterVelocity = Integer.parseInt(config.getProperty("monsterVelocity"));
                int playerInvisibilityTime = Integer.parseInt(config.getProperty("playerInvisibilityTime"));
                int monsterInvisibilityTime = Integer.parseInt(config.getProperty("monsterInvisibilityTime"));
        
                // Success for the player position initial '0x0' '1x1' '12x5'
                StringBuffer tmpx = new StringBuffer();
                StringBuffer tmpy = new StringBuffer();
                int ind = 0;
                while(config.getProperty("player").charAt(ind) != 'x'){
                    tmpx.append(config.getProperty("player").charAt(ind));
                    ind++;
                }
                ind++;
                for(int i = ind; i <config.getProperty("player").length();i++ ){
                    tmpy.append(config.getProperty("player").charAt(i));
                }
                int xPlayer = Integer.parseInt(tmpx.toString());
                int yPlayer = Integer.parseInt(tmpy.toString());
        
                // For the detail of the maps
                String string = config.getProperty("level1");
                Configuration configuration = new Configuration(new Position(xPlayer,yPlayer),bombBagCapacity,playerLives,playerInvisibilityTime,monsterVelocity,monsterInvisibilityTime);
                Game game = new Game(configuration,new Level(GameLauncher.load(string)));
                // Save all maps in the game
                game.stringMaps = new String[levels];
                for(int i = 0; i < levels; i++){
                    game.stringMaps[i] = config.getProperty("level"+(i+1));
                }
                modeScore = false;
                mode2Players = false;
                GameEngine engine = new GameEngine(game, stage,1,modeScore,mode2Players,false);
                engine.start();
        
            } else {
                System.out.println("File does not exist: " + filePath);
            }
        });

        // ScoreMode
        scoreModeItem.setOnAction(e -> {
            Game game = GameLauncher.loadScore();
            modeScore = true;
            mode2Players = false;
            GameEngine engine = new GameEngine(game, stage,1,modeScore,mode2Players,false);
            engine.start();
        });

        PlayersModeItem.setOnAction(e -> {
            Game game = GameLauncher.load2Players();
            modeScore = false;
            mode2Players = true;
            GameEngine engine = new GameEngine(game, stage,1,modeScore,mode2Players,false);
            engine.start();
        });


        // Exit
        exitItem.setOnAction(e -> System.exit(0));

    }


}

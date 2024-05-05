
package fr.sorbonne.cpa.bombman.engine;

import fr.sorbonne.cpa.bombman.game.Game;
import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.view.ImageResource;
import fr.sorbonne.cpa.bombman.view.ImageResourceFactory;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**
 * Represents a status bar for displaying game statistics and player statuses such as lives, bombs,
 * range, and scores. This bar can adapt to different game modes, showing additional information for
 * scoring or multi-player scenarios.
 */
public class StatusBar {
    public static final int height = 55;
    private final Game game;
    private final DropShadow ds = new DropShadow();
    private final HBox hBox = new HBox();
    private final Text lives = new Text();
    private final Text availableBombs = new Text();
    private final Text bombRange = new Text();
    private final Text lives2 = new Text();
    private final Text availableBombs2 = new Text();
    private final Text bombRange2 = new Text();

    private final Text keys = new Text();
    private final Text scores = new Text();
    private final HBox level = new HBox();
    private final boolean modeScore;
    private final boolean mode2Players;


    /**
     * Constructs a StatusBar with specified configurations and adds it to the provided game scene.
     * The StatusBar displays various game-related statistics which update dynamically during gameplay.
     *
     * @param root The root group to which the status bar will be added.
     * @param sceneWidth The width of the scene to determine the size of the status bar.
     * @param sceneHeight The height of the scene to determine the vertical position of the status bar.
     * @param game The game object to fetch current game status like level and player stats.
     * @param modeScore A boolean flag indicating if the game is in score mode, which affects displayed elements.
     * @param mode2Players A boolean flag indicating if the game is in two-player mode, which requires additional status displays.
     */
    public StatusBar(Group root, int sceneWidth, int sceneHeight, Game game,boolean modeScore,boolean mode2Players) {
        // Status bar
        this.game = game;
        this.modeScore = modeScore;
        this.mode2Players = mode2Players;
        level.getStyleClass().add("level");
        // Change the map when we go upstairs or go downstairs
        level.getChildren().add(new ImageView(ImageResourceFactory.digit(game.getCurrentLevel()).getImage()));

        ds.setRadius(5.0);
        ds.setOffsetX(3.0);
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.5f, 0.5f, 0.5f));


        HBox status = new HBox();
        status.getStyleClass().add("status");
        HBox live = statusGroup(ImageResource.HEART.getImage(), this.lives);
        HBox bombs = statusGroup(ImageResource.BANNER_BOMB.getImage(), availableBombs);
        HBox range = statusGroup(ImageResource.BANNER_RANGE.getImage(), bombRange);
        HBox key = statusGroup(ImageResource.KEY.getImage(), keys);
        if(modeScore) {
            HBox score = statusGroup(ImageResource.SCORE.getImage(),scores);
            status.getChildren().addAll(live, bombs, range,score);
        } else if (mode2Players) {
            HBox live2 = statusGroup(ImageResource.HEART.getImage(), this.lives2);
            HBox bombs2 = statusGroup(ImageResource.BANNER_BOMB.getImage(), availableBombs2);
            HBox range2 = statusGroup(ImageResource.BANNER_RANGE.getImage(), bombRange2);
            status.getChildren().addAll(live, bombs, range,live2,bombs2,range2);
        } else status.getChildren().addAll(live, bombs, range, key);

        status.setSpacing(40.0);


        hBox.getChildren().addAll(status);
        hBox.getStyleClass().add("statusBar");
        hBox.relocate(0, sceneHeight);
        hBox.setPrefSize(sceneWidth, height);
        root.getChildren().add(hBox);
    }
    /**
     * Creates a group of status elements consisting of an icon and a text label to display values like lives, bombs, etc.
     *
     * @param kind The image to use as the icon for the status.
     * @param number The text object used to display the numeric value of the status.
     * @return A horizontal box containing the image and text for a particular game status.
     */
    private HBox statusGroup(Image kind, Text number) {
        HBox group = new HBox();
        ImageView img = new ImageView(kind);
        group.setSpacing(5);
        number.setEffect(ds);
        number.setCache(true);
        number.setFill(Color.BLACK);
        number.getStyleClass().add("number");
        group.getChildren().addAll(img, number);
        return group;
    }
    /**
     * Updates the values displayed on the status bar according to the current state of the game.
     * This method should be called whenever there is a change in the game's state that needs to be reflected in the UI.
     *
     * @param game The current game object from which to fetch the latest stats for display.
     */
    public void update(Game game) {
        Player player = game.player();
        Player player2 = game.player2();
        // Implémentation d'afficher les status
        lives.setText(""+player.getLives());
        bombRange.setText(""+player.getRange());
        availableBombs.setText(""+player.getBombs());
        keys.setText(""+player.getKeys());
        if(modeScore) scores.setText(""+ player.getScores());
        if(mode2Players){
            lives2.setText(""+player2.getLives());
            bombRange2.setText(""+player2.getRange());
            availableBombs2.setText(""+player2.getBombs());
        }

    }
}

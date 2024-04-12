/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.view.ImageResource;
import fr.ubx.poo.ubomb.view.ImageResourceFactory;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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

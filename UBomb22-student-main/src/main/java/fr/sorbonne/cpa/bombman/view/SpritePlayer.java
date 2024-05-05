
package fr.sorbonne.cpa.bombman.view;

import fr.sorbonne.cpa.bombman.go.character.Player;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpritePlayer extends Sprite {

    public SpritePlayer(Pane layer, Player player) {
        super(layer, null, player);
        updateImage();
    }

    @Override
    public void updateImage() {
        Player player = (Player) getGameObject();
        if(player.isPlayer1()){
            Image image = ImageResourceFactory.getPlayer(player.getDirection(),player.isInvicility()).getImage();
            setImage(image);
        }else {
            Image image = ImageResourceFactory.getPlayer2(player.getDirection(),player.isInvicility()).getImage();
            setImage(image);
        }

    }
}


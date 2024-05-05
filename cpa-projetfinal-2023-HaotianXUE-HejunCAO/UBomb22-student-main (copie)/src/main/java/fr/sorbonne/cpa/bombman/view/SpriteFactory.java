
package fr.sorbonne.cpa.bombman.view;

import fr.sorbonne.cpa.bombman.game.Direction;
import fr.sorbonne.cpa.bombman.go.GameObject;
import fr.sorbonne.cpa.bombman.go.decor.Box;
import fr.sorbonne.cpa.bombman.go.decor.Stone;
import fr.sorbonne.cpa.bombman.go.decor.Tree;
import fr.sorbonne.cpa.bombman.go.decor.bonus.*;
import javafx.scene.layout.Pane;

import static fr.sorbonne.cpa.bombman.view.ImageResource.*;


public final class SpriteFactory {

    public static Sprite create(Pane layer, GameObject gameObject) {
        if (gameObject instanceof Stone)
            return new Sprite(layer, STONE.getImage(), gameObject);
        if (gameObject instanceof Tree)
            return new Sprite(layer, TREE.getImage(), gameObject);
        if (gameObject instanceof Key)
            return new Sprite(layer, KEY.getImage(), gameObject);
        if (gameObject instanceof Heart)
            return new Sprite(layer,HEART.getImage(), gameObject);
        if (gameObject instanceof Princess)
            return new Sprite(layer, PRINCESS.getImage(), gameObject);
        if (gameObject instanceof Box)
            return new Sprite(layer, BOX.getImage(), gameObject);
        if (gameObject instanceof BombRangeInc)
            return new Sprite(layer,BONUS_BOMB_RANGE_INC.getImage(), gameObject);
        if (gameObject instanceof BombRangeDec)
            return new Sprite(layer,BONUS_BOMB_RANGE_DEC.getImage(), gameObject);
        if (gameObject instanceof BombNumberInc)
            return new Sprite(layer,BONUS_BOMB_NB_INC.getImage(), gameObject);
        if (gameObject instanceof BombNumberDec)
            return new Sprite(layer,BONUS_BOMB_NB_DEC.getImage(), gameObject);
        if (gameObject instanceof DoorPrevOpened)
            return new Sprite(layer,DOOR_OPENED_MINUS.getImage(), gameObject);
        if (gameObject instanceof DoorNextOpened)
            return new Sprite(layer,DOOR_OPENED.getImage(), gameObject);
        if (gameObject instanceof DoorNextClosed)
            return new Sprite(layer,DOOR_CLOSED.getImage(), gameObject);

        // Monster image depends on direction
        if (gameObject instanceof Monster) {
            if(((Monster) gameObject).getDirection() == Direction.LEFT)  return new Sprite(layer, MONSTER_LEFT.getImage(), gameObject);
            if(((Monster) gameObject).getDirection() == Direction.RIGHT)  return new Sprite(layer, MONSTER_RIGHT.getImage(), gameObject);
            if(((Monster) gameObject).getDirection() == Direction.UP)  return new Sprite(layer, MONSTER_UP.getImage(), gameObject);
            if(((Monster) gameObject).getDirection() == Direction.DOWN)  return new Sprite(layer, MONSTER_DOWN.getImage(), gameObject);
        }

        throw new RuntimeException("Unsupported sprite for decor " + gameObject);
    }
}

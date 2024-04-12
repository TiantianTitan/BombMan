package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.bonus.DoorNextClosed;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteClosedDoor extends Sprite {

    public SpriteClosedDoor(Pane layer, DoorNextClosed doorNextClosed) {
        super(layer, null, doorNextClosed);
    }

    public SpriteClosedDoor(Pane layer,Image image, DoorNextClosed doorNextClosed) {
        super(layer, image, doorNextClosed);
        updateImage();
    }

    @Override
    public void updateImage() {
        DoorNextClosed doorNextClosed = (DoorNextClosed)getGameObject();
        Image image;
        if(doorNextClosed.isOpened()) image = ImageResource.DOOR_OPENED.getImage();
        else image = ImageResource.DOOR_CLOSED.getImage();
        setImage(image);
    }
}

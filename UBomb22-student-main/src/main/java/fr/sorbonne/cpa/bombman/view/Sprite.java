
package fr.sorbonne.cpa.bombman.view;

import fr.sorbonne.cpa.bombman.game.Position;
import fr.sorbonne.cpa.bombman.go.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprite {

    public static final int size = 40;
    private final Pane layer;
    private final GameObject gameObject;
    private ImageView imageView;
    private Image image;

    public Sprite(Pane layer, Image image, GameObject gameObject) {
        this.layer = layer;
        this.image = image;
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public final void setImage(Image image) {
        if (this.image == null || this.image != image ) {
            this.image = image;
        }
    }

    public void updateImage() {}

    public Position getPosition() {
        return getGameObject().getPosition();
    }

    public final void render() {
        if (gameObject.isModified()) {
            if (imageView != null) {
                remove();
            }

            updateImage();
            imageView = new ImageView(this.image);
            imageView.setX(getPosition().x() * size);
            imageView.setY(getPosition().y() * size);
            layer.getChildren().add(imageView);
            gameObject.setModified(false);
        }
    }

    public final void remove() {
        layer.getChildren().remove(imageView);
        imageView = null;
    }
}

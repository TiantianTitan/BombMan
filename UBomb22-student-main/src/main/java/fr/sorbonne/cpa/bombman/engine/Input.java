
package fr.sorbonne.cpa.bombman.engine;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;

import static javafx.scene.input.KeyCode.*;
/**
 * Handles keyboard inputs for a game, providing methods to determine the state
 * of various game-related keys. This class uses a {@code BitSet} to track the
 * pressed or released state of keys efficiently.
 *
 * Each key press or release updates this bitset, which then serves as the source
 * for checking key states through various methods like {@code isMoveUp()}, {@code isMoveDown()},
 * and others.
 */
public final class Input {

    /**
     * A {@code BitSet} to register if any {@code KeyCode} is currently pressed.
     * Each key's ordinal value from the {@code KeyCode} enum serves as the index in this set.
     */
    private final BitSet keyboardBitSet = new BitSet();
    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    private final EventHandler<KeyEvent> keyPressedEventHandler = event -> {

        // register key down
        keyboardBitSet.set(event.getCode().ordinal(), true);
    };
    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private final EventHandler<KeyEvent> keyReleasedEventHandler = event -> {

        // register key up
        keyboardBitSet.set(event.getCode().ordinal(), false);
    };
    /**
     * The scene to which this input handler is attached, allowing the handler to intercept
     * key press and release events.
     */
    private final Scene scene;
    /**
     * Constructs an {@code Input} handler for managing keyboard inputs on a specified {@code Scene}.
     *
     * @param scene The scene on which keyboard events are to be captured.
     */
    public Input(Scene scene) {
        this.scene = scene;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }
    /**
     * Clears all recorded key states. This method can be used to reset the input state,
     * for instance, when transitioning between screens or game states.
     */
    public void clear() {
        keyboardBitSet.clear();
    }

    /**
     * Checks if a specific {@code KeyCode} is currently pressed.
     *
     * @param key The {@code KeyCode} to check.
     * @return {@code true} if the key is currently pressed, {@code false} otherwise.
     */
    private boolean is(KeyCode key) {
        return keyboardBitSet.get(key.ordinal());
    }

    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // If direction and its opposite direction are pressed simultaneously, then the
    // direction isn't handled.
    // -------------------------------------------------
    /**
     * Determines if the "up" key is pressed and not counteracted by the "down" key.
     *
     * @return {@code true} if up movement is indicated, {@code false} otherwise.
     */
    public boolean isMoveUp() {
        return is(UP) && !is(DOWN);
    }
    /**
     * Determines if the "down" key is pressed and not counteracted by the "up" key.
     *
     * @return {@code true} if down movement is indicated, {@code false} otherwise.
     */
    public boolean isMoveDown() {
        return is(DOWN) && !is(UP);
    }
    /**
     * Determines if the "left" key is pressed and not counteracted by the "right" key.
     *
     * @return {@code true} if left movement is indicated, {@code false} otherwise.
     */
    public boolean isMoveLeft() {
        return is(LEFT) && !is(RIGHT);
    }
    /**
     * Determines if the "right" key is pressed and not counteracted by the "left" key.
     *
     * @return {@code true} if right movement is indicated, {@code false} otherwise.
     */
    public boolean isMoveRight() {
        return is(RIGHT) && !is(LEFT);
    }
    /**
     * Checks if the "space" key for placing a bomb is pressed.
     *
     * @return {@code true} if the bomb placement key is pressed, {@code false} otherwise.
     */
    public boolean isBomb() {
        return is(SPACE);
    }
    /**
     * Determines if the "up" key for the second player is pressed. This can be configured
     * to either 'Z' or 'W' keys and is not counteracted by the "down" ('S') key.
     *
     * @return {@code true} if up movement for the second player is indicated, {@code false} otherwise.
     */
    public  boolean isMoveUpPlayer2(){return (is(Z) || is(W)) && !is(S);}
    /**
     * Determines if the "down" key for the second player is pressed. This is activated by the 'S' key
     * unless an "up" movement key ('Z' or 'W') is also pressed, which would neutralize the down command.
     *
     * @return {@code true} if down movement for the second player is indicated, {@code false} otherwise.
     */
    public  boolean isMoveDownPlayer2(){return !(is(Z) || is(W)) && is(S);}
    /**
     * Determines if the "left" key for the second player is pressed. This can be configured
     * to either 'Q' or 'A' keys and is not counteracted by the "right" ('D') key.
     *
     * @return {@code true} if left movement for the second player is indicated, {@code false} otherwise.
     */
    public  boolean isMoveLeftPlayer2(){return (is(Q) || is(A)) && !is(D);}
    /**
     * Determines if the "right" key for the second player is pressed. This is activated by the 'D' key
     * unless a "left" movement key ('Q' or 'A') is also pressed, which would neutralize the right command.
     *
     * @return {@code true} if right movement for the second player is indicated, {@code false} otherwise.
     */
    public  boolean isMoveRightPlayer2(){return !(is(Q) || is(A)) && is(D);}
    /**
     * Checks if the "bomb" key for the second player is pressed, which is designated to the 'B' key.
     *
     * @return {@code true} if the bomb placement key for the second player is pressed, {@code false} otherwise.
     */
    public boolean isBombPlayer2() {return is(B); };
    /**
     * Checks if the "enter" key, often used for activating items or confirming selections, is pressed.
     *
     * @return {@code true} if the enter key is pressed, {@code false} otherwise.
     */
    public boolean isKey() {
        return is(ENTER);
    }
    /**
     * Checks if the "escape" key, often used for exiting the game or opening a menu, is pressed.
     *
     * @return {@code true} if the escape key is pressed, {@code false} otherwise.
     */
    public boolean isExit() {
        return is(ESCAPE);
    }
}

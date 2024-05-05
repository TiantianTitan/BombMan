package fr.sorbonne.cpa.bombman.game;

import fr.sorbonne.cpa.bombman.go.GameObject;
import fr.sorbonne.cpa.bombman.go.character.Player;

import java.util.LinkedList;
import java.util.List;
/**
 * Represents the central game logic and state management for a grid-based game. This class holds
 * configurations, players, and the game grid. It supports both single and dual player configurations,
 * allowing dynamic switching and state updates across the game's lifecycle.
 */
public class Game {

    private final Configuration configuration;
    private final Configuration configuration2 ;
    private final Player player;
    private Player player2;
    private final Grid grid;
    public String[] stringMaps;
    private int currentLevel;
    /**
     * Constructs a single player game with specified configuration and grid.
     *
     * @param configuration The configuration settings for the primary player and game mechanics.
     * @param grid The game grid that holds all game objects.
     */
    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        this.configuration2 = null;
        this.grid = grid;
        player = new Player(this, configuration.playerPosition(),true);
    }
    /**
     * Constructs a dual player game with specified configurations for both players and grid.
     *
     * @param configuration The configuration settings for the primary player.
     * @param configuration2 The configuration settings for the second player.
     * @param grid The game grid that holds all game objects.
     */
    public Game(Configuration configuration,Configuration configuration2, Grid grid){
        this.configuration = configuration;
        this.configuration2 = configuration2;
        this.grid = grid;
        player = new Player(this,configuration.playerPosition(),true);
        player2 = new Player(this,configuration2.playerPosition(),false);
    }

    /**
     * Gets the primary configuration.
     *
     * @return The primary game configuration.
     */
    public Configuration configuration() {
        return configuration;
    }
    /**
     * Gets the secondary configuration.
     *
     * @return The secondary game configuration, used for the second player.
     */

    public Configuration configuration2() {
        return configuration2;
    }

    /**
     * Retrieves all game objects present at a specified position on the grid.
     *
     * @param position The grid position to check for game objects.
     * @return A list of game objects located at the specified position.
     */
    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        return gos;
    }
    /**
     * Gets the game grid.
     *
     * @return The game grid.
     */
    public Grid grid() {
        return grid;
    }
    /**
     * Gets the primary player.
     *
     * @return The primary player.
     */
    public Player player() {
        return this.player;
    }
    /**
     * Gets the secondary player, if present.
     *
     * @return The secondary player or {@code null} if not configured.
     */
    public Player player2(){ return this.player2; }
    /**
     * Gets the current level number.
     *
     * @return The current level number.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Sets the current level number.
     *
     * @param currentLevel The new level number to set.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}

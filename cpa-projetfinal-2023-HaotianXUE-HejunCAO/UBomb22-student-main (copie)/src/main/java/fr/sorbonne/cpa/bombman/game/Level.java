package fr.sorbonne.cpa.bombman.game;

import fr.sorbonne.cpa.bombman.go.decor.Box;
import fr.sorbonne.cpa.bombman.go.decor.Decor;
import fr.sorbonne.cpa.bombman.go.decor.Stone;
import fr.sorbonne.cpa.bombman.go.decor.Tree;
import fr.sorbonne.cpa.bombman.go.decor.bonus.*;
import fr.sorbonne.cpa.bombman.launcher.Entity;
import fr.sorbonne.cpa.bombman.launcher.MapLevel;
import java.util.*;
/**
 * Represents a specific level in a grid-based game, managing all static and dynamic elements within the level.
 * This class facilitates the mapping of game elements to specific positions within a grid defined by its width and height,
 * supporting operations like adding, removing, and querying elements.
 *
 * It also manages special game locations such as player initial positions for game entry points.
 */
public class Level implements Grid {

    private final int width;

    private final int height;

    private final MapLevel entities;
    private Position playerInitPositionNext;
    private Position playerInitPositionPrev;

    private final Map<Position, Decor> elements = new HashMap<>();
    /**
     * Constructs a level using a predefined set of entities which describe the initial state and configuration
     * of the level such as the positioning of obstacles, enemies, and bonuses.
     *
     * @param entities A structured map defining the initial setup of the grid, with entities positioned according to the level design.
     */
    public Level(MapLevel entities) {
        this.entities = entities;
        this.width = entities.width();
        this.height = entities.height();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(i, j);
                Entity entity = entities.get(i, j);
                switch (entity) {
                    case Stone:
                        elements.put(position, new Stone(position));
                        break;
                    case Tree:
                        elements.put(position, new Tree(position));
                        break;
                    case Key:
                        elements.put(position, new Key(position));
                        break;
                    // Implémentation de insérer les roles dans les Entity
                    case Monster:
                        elements.put(position, new Monster(position));
                        break;
                    case Princess:
                        elements.put(position, new Princess(position));
                        break;
                    case Box:
                        elements.put(position, new Box(position));
                        break;
                    case BombRangeInc:
                        elements.put(position, new BombRangeInc(position));
                        break;
                    case BombRangeDec:
                        elements.put(position, new BombRangeDec(position));
                        break;
                    case BombNumberInc:
                        elements.put(position, new BombNumberInc(position));
                        break;
                    case BombNumberDec:
                        elements.put(position, new BombNumberDec(position));
                        break;
                    case Heart:
                        elements.put(position, new Heart(position));
                        break;
                    case DoorPrevOpened:
                        elements.put(position, new DoorPrevOpened(position));
                        playerInitPositionPrev = position;
                        break;
                    case DoorNextOpened:
                        elements.put(position, new DoorNextOpened(position));
                        playerInitPositionNext = position;
                        break;
                    case DoorNextClosed:
                        elements.put(position, new DoorNextClosed(position));
                        break;
                    case Empty:
                        break;
                    default:
                        throw new RuntimeException("EntityCode " + entity.name() + " not processed");
                }
            }
    }
    /**
     * Returns the width of the level grid.
     *
     * @return the width of the grid.
     */
    @Override
    public int width() {
        return this.width;
    }
    /**
     * Returns the height of the level grid.
     *
     * @return the height of the grid.
     */
    @Override
    public int height() {
        return this.height;
    }
    /**
     * Retrieves a decor object from a specific position on the grid.
     *
     * @param position The position from which to retrieve the decor.
     * @return The decor at the specified position, or null if no decor is present.
     */
    public Decor get(Position position) {
        return elements.get(position);
    }
    /**
     * Removes a decor object from a specific position on the grid.
     *
     * @param position The position from which to remove the decor.
     */
    @Override
    public void remove(Position position) {
        elements.remove(position);
    }
    /**
     * Returns a collection of all decor elements present in the level.
     *
     * @return a collection of all decors.
     */
    public Collection<Decor> values() {
        return elements.values();
    }

    /**
     * Checks if a given position is within the bounds of the grid.
     *
     * @param position The position to check.
     * @return true if the position is within the grid bounds, otherwise false.
     */
    @Override
    public boolean inside(Position position) {
        return true;
    }
    /**
     * Sets a decor at a specific position within the grid.
     *
     * @param position The position where the decor should be placed.
     * @param decor The decor object to place at the specified position.
     */
    @Override
    public void set(Position position, Decor decor) {
        if (!inside(position))
            throw new IllegalArgumentException("Illegal Position");
        if (decor != null)
            elements.put(position, decor);
    }
    /**
     * Retrieves the initial player position for the previously visited level.
     *
     * @return the initial position for entering a previously visited level.
     */
    public Position getPlayerInitPositionPrev() {
        return playerInitPositionPrev;
    }
    /**
     * Retrieves the initial player position for moving to the next level.
     *
     * @return the initial position for proceeding to the next level.
     */
    public Position getPlayerInitPositionNext() {
        return playerInitPositionNext;
    }
}




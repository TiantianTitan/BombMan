
package fr.sorbonne.cpa.bombman.game;

import java.util.Random;
/**
 * Enum representing the four cardinal directions (UP, RIGHT, DOWN, LEFT) in a grid-based game.
 * Each direction provides a method to calculate the next position based on a current position and
 * a step size, known as delta.
 *
 * This enum is particularly useful for moving characters or objects in a game environment by specifying
 * direction and magnitude of movement.
 */
public enum Direction {
    /**
     * Represents the upward direction in the grid. Decreases the y-coordinate.
     */
    UP {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x(), pos.y() - delta);
        }
    },
    /**
     * Represents the rightward direction in the grid. Increases the x-coordinate.
     */
    RIGHT {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x() + delta, pos.y());
        }
    },
    /**
     * Represents the downward direction in the grid. Increases the y-coordinate.
     */
    DOWN {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x(), pos.y() + delta);
        }
    },
    /**
     * Represents the leftward direction in the grid. Decreases the x-coordinate.
     */
    LEFT {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x() - delta, pos.y());
        }
    },
    ;

    /**
     * A static random generator used for selecting a random direction.
     */
    private static final Random randomGenerator = new Random();

    /**
     * Selects a random direction from the available directions.
     *
     * @return A randomly selected {@code Direction}.
     */
    public static Direction random() {
        int i = randomGenerator.nextInt(values().length);
        return values()[i];
    }

    /**
     * Calculates the next position from a given position and a delta value that specifies the step size.
     *
     * @param pos The current position from which to move.
     * @param delta The number of grid units to move in the specified direction.
     * @return The new position after moving.
     */
    public abstract Position nextPosition(Position pos, int delta);

    /**
     * Calculates the next position from a given position using a default delta value of 1.
     * This method simplifies moving to an adjacent grid cell in the specified direction.
     *
     * @param pos The current position from which to move.
     * @return The new position after moving one grid unit in the specified direction.
     */
    public Position nextPosition(Position pos) {
        return nextPosition(pos, 1);
    }


}
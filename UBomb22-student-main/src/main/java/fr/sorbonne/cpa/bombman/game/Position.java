package fr.sorbonne.cpa.bombman.game;
/**
 * A record that encapsulates a two-dimensional position with {@code x} and {@code y} coordinates.
 * This record is immutable and can be used in any context where a 2D coordinate system is applicable,
 * such as graphical applications, simulations, and games.
 *
 * @param x The x-coordinate of the position.
 * @param y The y-coordinate of the position.
 */
public record Position (double x, double y) {
    /**
     * Constructs a new {@code Position} instance by copying the coordinates from another {@code Position} instance.
     * This acts as a copy constructor, allowing for the creation of a new {@code Position} that matches the given one.
     *
     * @param position The position to copy.
     */
    public Position(Position position) {
        this(position.x, position.y);
    }


}

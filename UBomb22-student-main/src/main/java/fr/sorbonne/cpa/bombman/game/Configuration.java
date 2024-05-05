package fr.sorbonne.cpa.bombman.game;
/**
 * Represents a configuration setting for a game, encapsulating all necessary parameters
 * to initialize and manage game elements such as the player and monsters.
 *
 * This record is immutable, meaning once created, the values of its fields cannot be changed.
 * Each field represents a specific configuration setting for the game:
 *
 * - {@code playerPosition}: The initial position of the player on the game map.
 * - {@code bombBagCapacity}: The initial number of bombs a player can carry.
 * - {@code playerLives}: The starting number of lives a player has.
 * - {@code playerInvisibilityTime}: The duration (in milliseconds) a player remains invincible after being hit.
 * - {@code monsterVelocity}: The speed at which monsters move, potentially indicating how often they move.
 * - {@code monsterInvisibilityTime}: The duration (in milliseconds) monsters remain invisible after spawning or being hit.
 */
public record Configuration(Position playerPosition, int bombBagCapacity, int playerLives, long playerInvisibilityTime,
                            int monsterVelocity, long monsterInvisibilityTime) {
}

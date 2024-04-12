package fr.ubx.poo.ubomb.game;

public record Position (int x, int y) {

    public Position(Position position) {
        this(position.x, position.y);
    }


}

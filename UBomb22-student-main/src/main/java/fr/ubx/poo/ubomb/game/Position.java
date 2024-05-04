package fr.ubx.poo.ubomb.game;

public record Position (double x, double y) {

    public Position(Position position) {
        this(position.x, position.y);
    }


}

package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;

import java.util.Random;

public enum MalusRandom {

    NUMBERDEC{
        public BombNumberDec getElement(Position position){
            return new BombNumberDec(position);
        }
    },RANGEDEC{
        public BombRangeDec getElement(Position position){
            return new BombRangeDec(position);
        }
    },;

    private static final Random randomGenerator = new Random();

    public static MalusRandom random() {
        int i = randomGenerator.nextInt(values().length);
        return values()[i];
    }

    public abstract Bonus getElement(Position position);



}

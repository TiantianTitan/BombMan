package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Position;

import java.util.Random;

public enum BonusRandom {

    NUMBERINC{
        public BombNumberInc getElement(Position position){
            return new BombNumberInc(position);
        }
    },RANGEINC{
        public BombRangeInc getElement(Position position){
            return new BombRangeInc(position);
        }
    },HEART{
      public Heart getElement(Position position){
          return new Heart(position);
      }
    },
    ;

    private static final Random randomGenerator = new Random();

    public static BonusRandom random() {
        int i = randomGenerator.nextInt(values().length);
        return values()[i];
    }

    public abstract Bonus getElement(Position position);


}

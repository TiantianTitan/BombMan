package fr.sorbonne.cpa.bombman.go.decor.bonus;

import fr.sorbonne.cpa.bombman.game.Position;

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

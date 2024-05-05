package fr.sorbonne.cpa.bombman.go;

import fr.sorbonne.cpa.bombman.go.decor.bonus.Bonus;

// Double dispatch visitor pattern
public interface TakeVisitor {
    // Key
    default void take(Bonus bonus){}


}

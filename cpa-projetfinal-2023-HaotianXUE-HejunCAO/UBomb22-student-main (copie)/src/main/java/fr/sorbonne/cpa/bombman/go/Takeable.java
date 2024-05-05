
package fr.sorbonne.cpa.bombman.go;


import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.go.decor.bonus.Monster;

public interface Takeable {
    default void takenBy(Player player) {}
    default void takenBy(Monster monster){}
}

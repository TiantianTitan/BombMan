package fr.sorbonne.cpa.bombman.go;

import fr.sorbonne.cpa.bombman.go.character.Player;
import fr.sorbonne.cpa.bombman.go.decor.bonus.Monster;

public interface Walkable {
    default boolean walkableBy(Player player) { return false; }

    default boolean walkableBy(Monster monster){ return false;}
}

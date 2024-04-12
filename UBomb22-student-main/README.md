<link rel="stylesheet" href="readme.css">


# Projet de POO

Réalisation d'un jeu vidéo 2D : **UBomb**.


## INFO du 08/11/2022 

Il y a un bug sur les machines Linux du CREMI exclusivement qui empêche la taille de la fenêtre de s'ajuster automatiquement à la taille de la carte. Pour y remédier dans l'immédiat, remplacer `stage.setResizable(false);` par `stage.setResizable(true);` dans la méthode `initialize` de la classe `GameEngine`.

## Principes du jeu

Une princesse est détenue prisonnière par de méchants monstres verts. Votre mission, si vous l'acceptez, est d'aller la délivrer. Pour cela, vous devrez traverser plusieurs mondes, plus effrayants les uns que les autres. Des portes vous permettront de passer de monde en monde. Certaines portes seront fermées à clé et nécessiteront d'avoir une clé dans votre inventaire. Vous êtes un expert en explosif et utiliserez vos bombes pour détruire les obstacles devant vous et tuer les monstres qui vous attaqueront.


## Représentation du jeu

Chaque monde est représenté par une carte (rectangulaire) composée de cellules. Chaque cellule peut contenir :

-   le joueur ;
-   la princesse ;
-   des monstres ;
-   des éléments de décor (arbres, pierres...) infranchissables et
    indestructibles ;
-   des caisses destructibles et déplaçables ; 
-   des portes, ouvertes ou fermées, permettant d’évoluer entre les
    mondes ;
-   des clés pour débloquer les portes fermées ;
-   des bonus ou des malus qu'il est possible de ramasser.

![Bombeirb](img/ubomb.png)

## Prise en main

Nous vous fournissons une première ébauche du jeu, utilisant la bibliothèque JavaFX. Le lancement du jeu
fait apparaître une carte minimaliste, chargée statiquement en mémoire, dans laquelle le joueur peut se déplacer dans toutes les directions, quelle que soit la nature des cellules. Le code utilise `gradle` comme moteur de production. Il suffit de lancer la commande suivante pour compiler et exécuter le jeu. Toutes les dépendances seront automatiquement téléchargées et installées. Le jeu nécessite une version de Java au moins égale à 14. La version 17 est recommandée car il s'agit de la dernière version LTS (*Long Term Support*). Pour utiliser Java 17 sur les machines du CREMI, il suffit d'exécuter la commande `source  /opt/local/bin/java17.env` dans votre fichier de configuration. 

    $ ./gradlew run


Le jeu utilise quelques fonctionnalités nouvelles de Java qui n'ont pas été vues en cours ou en TD :

### Switch Expression (Java 13)

Les expressions `Switch` peuvent désormais retourner une valeur et vous pouvez utiliser une syntaxe de style lambda. 

```java
boolean result = switch (status) {
    case SUBSCRIBER -> true;
    case FREE_TRIAL -> false;
    default -> throw new IllegalArgumentException("something is murky!");
};
```

### Records (Java 14)

Il existe désormais des classes d'enregistrement (`record`) qui permettent de ne pas devoir écrire beaucoup de code pour manipuler des champs *immutables* (déclarés avec `final`). Ainsi le code suivant :

```java
final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() { return x; }
    public int y() { return y; }

    // state-based implementations of equals, hashCode, toString
    // nothing else
}
```
s'écrit simplement par :
```java
record Point(int x, int y) { }
```


### Pattern Matching pour InstanceOf (Java 14)

Alors que précédemment vous deviez écrire :

```java
if (obj instanceof String) {
    String s = (String) obj;
    // use s
}
```

vous pouvez maintenant écrire directement 
```java
if (obj instanceof String s) {
    System.out.println(s.contains("hello"));
}
```

Travail à fournir
=================



## Premiers pas

Ajouter l'affichage de tous les éléments (caisses, bonus, clés...) ainsi que les monstres et la princesse. Modifier le code pour que les mouvements du joueur soient limités par le cadre de la carte, les éléments de décor et les caisses. 
Le joueur peut marcher sur une case où se trouve un bonus, une clé, ou un autre personnage. 
S’il atteint la princesse, la partie se termine par une victoire.
Si ses points de vie tombent à 0, la partie se termine par une défaite.

## Ajoutons des monstres
Ajouter des monstres. Pour le moment, les monstres ne savent pas marcher, ils ne bougent pas. Faire en sorte que le joueur perde une vie lorsqu'il se trouve sur la même case qu'un monstre.

## Gestion du panneau d’informations

Le panneau d’information doit afficher le nombre de vies, le nombre de bombes et leur portée, le nombre de clés dans l’inventaire et le numéro de niveau courant.

## Gestion des mondes

Dans la version de base, le jeu ne dispose que d'un seul niveau codé en dur dans le code. Nous allons maintenant charger une configuration complète de jeu depuis un fichier. Vous trouverez un répertoire `world` à la racine du projet avec un fichier `sample.properties` représentant un monde avec 3 niveaux. Les fichiers [properties](https://docs.oracle.com/javase/tutorial/essential/environment/properties.html) en Java permettent de facilement stocker des couples de clés/valeurs. 

Voici un exemple de code pour lire la valeur correspondant à la clé `compression` dans le fichier `sample.properties`.

```java
Properties config = new Properties();;
Reader in = new FileReader(file);
config.load(in);
boolean compression = Boolean.parseBoolean(integerProperty(config, "compression", "false"));
```

Comme vous pouvez le constater, certaines clés peuvent avoir des valeurs par défaut si elles ne sont pas présentes dans le fichier. Nous utiliserons les valeurs par défaut suivantes :

Clé | Valeur par défaut
--- | --- |
levels | 1 | 
compression | false |
bombBagCapacity | 3 | 
playerLives | 5 | 
playerInvisibilityTime | 4000 | 
monsterVelocity | 5 | 
monsterInvisibilityTime | 1000 | 

La clé `player` est obligatoire et sa valeur représente les coordonnées (`i` et `j`) du joueur sur le premier niveau. Le fichier contient des clés de la forme `levelX` ou `X` représente un numéro de niveau compris entre 1 et la valeur de la clé `levels`. La valeur associée à un niveau est une chaine de caractère encodant le niveau avec ou sans compression (RLE) en fonction de la variable `compression`.
Modifier le code dans la classe `GameLauncher` pour que le monde du jeu puisse être chargé depuis un fichier. 

## Gestion des portes

Lorsque le joueur arrive sur la case d’une porte ouverte, il passe
automatiquement au niveau correspondant à cette porte (niveau supérieur
ou inférieur). Il se retrouve automatiquement sur la porte du niveau
correspondant. Seuls le premier niveau (on ne peut pas passer au niveau inférieur) et le dernier (on ne peut pas aller plus loin) n'ont qu'une seule porte.  Si la porte est fermée, le joueur doit utiliser une des
clés de son inventaire. Pour ce faire, il doit appuyer sur la touche `[ENTER]` lorsqu'il est à côté de la porte à ouvrir et qu'il regarde la porte. Une fois utilisée, la clé disparaît de
l’inventaire. Chaque clé peut ouvrir indifféremment n’importe quelle
porte fermée. Une fois qu'une porte est ouverte, elle le reste pour toute la partie du jeu.


## Gestion des bonus et malus

Le joueur ramasse automatiquement un bonus lorsqu'il marche sur la case qui le contient. Les monstres peuvent marcher sur les cases des bonus, mais ne peuvent pas les ramasser. Il existe 5 bonus différents :

Bonus | Effet
--- | --- |
![nb+](src/main/resources/images/bonus_bomb_nb_inc.png) | Augmente la capacité du sac de bombes d’une unité. |
![nb-](src/main/resources/images/bonus_bomb_nb_dec.png) | Diminue la capacité du sac de bombes d’une unité. Le sac contient toujours au minimum une bombe. |
| ![range+](src/main/resources/images/bonus_bomb_range_inc.png) | Augmente la portée des bombes d’une unité. La modification de portée n'affecte pas les bombes déjà posées. |
| ![range-](src/main/resources/images/bonus_bomb_range_dec.png) | Diminue la portée des bombes d’une unité. La portée minimale est d’un. La modification de portée n'affecte pas les bombes déjà posées. |
| ![live](src/main/resources/images/heart.png) | Ajoute une vie. |

## Déplacement des caisses

 Les caisses doivent pouvoir être déplacées par le joueur si rien ne gêne dans le sens de la poussée. Le joueur ne peut déplacer qu'une seule caisse à la fois. Si un bonus ou un monstre se trouve dans la direction de déplacement d’une caisse, la caisse reste bloquée. Le joueur ne peut pas déplacer deux caisses à la fois. Vous pouvez représenter les caisses comme des éléments de décor. Dans ce cas, déplacer une caisse revient à la supprimer et en créer une nouvelle aux bonnes coordonnées.

## Gestion des bombes

Lorsque le joueur presse la touche `[ESPACE]`, il dépose une bombe sur
la case sur laquelle il se trouve, déclenchant une explosion au bout de
4 secondes. La mèche de la bombe diminue chaque seconde. La portée de
la bombe est par défaut de 1 case, en croix (case du dessus, case du
dessous, case de gauche, case de droite). Les éléments de décor stoppent
la propagation de l’explosion dans le sens qu’ils obstruent. Si une caisse est sur le chemin de l’explosion, elle
disparaît. Une explosion ne peut
détruire qu’une seule caisse dans une même direction. Si un bonus (ou un malus) se trouve sur le chemin de l’explosion, il disparaît. 
Enfin, si un joueur ou un monstre est sur une cellule touchée par une explosion, il
perd une vie. Les explosions n’ont aucun effet sur les portes et les
clés. Lorsqu’une bombe explose, une nouvelle bombe est ajoutée à
l’inventaire du joueur. 

Si le joueur pose une bombe et change ensuite de niveau en franchissant une porte, la bombe doit tout de même exploser au bout de 4 secondes. Les éléments de décor détruits sur un niveau doivent le rester pendant toute la durée de la partie.

## Gestion des vies

Le joueur peut perdre une vie s’il se trouve sur une case à portée de l’explosion d’une bombe ou s'il croise un monstre. Le joueur bénéficie alors d’une temporisation d'une seconde pendant laquelle il est invulnérable. Si le joueur n’a plus de vie, la partie se termine. 

## Gestion des monstres

Les déplacements des monstres sont entièrement aléatoires. Une collision avec un monstre déclenche la perte d’une vie. Commencer par ajouter un seul monstre à la fois, puis augmenter le nombre de monstres. Les monstres ne peuvent pas ramasser les bonus qui se trouvent sur le sol. Les monstres ont peur des portes et ne peuvent pas les franchir. Ils ne peuvent pas marcher sur les cases des portes. Les monstres ne peuvent pas déplacer les caisse. 

## Fin de partie

La partie est finie lorsque le joueur arrive sur la case de la
princesse. Les monstres ne veulent pas de mal à la princesse, mais feront
tout pour la garder prisonnière. La touche `[ESCAPE]` permet de quitter la partie à tout moment.


## Pour aller plus loin et gagner des points bonus

- Faire en sorte que la vitesse de déplacement des monstres soit faible dans les premiers niveaux et augmente plus on se rapproche de la princesse. 
- Faire en sorte que les monstres possèdent une vie supplémentaire tous les 2 niveaux (2 vies à partir du niveau 2, 3 vies à partir du niveau 4, …)  Il n'est pas demandé d'afficher les vies restantes d'un monstre. 
- Faire en sorte que les monstres du dernier niveau se dirigent vers le joueur et non plus de manière aléatoire.

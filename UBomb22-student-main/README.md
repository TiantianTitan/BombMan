<link rel="stylesheet" href="readme.css">


# Projet de CPA
Sorbonne université

Haotian XUE , HeJun CAO

Un jeu vidéo 2D : **BombMan**.


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
fait apparaître une carte minimaliste, chargée statiquement en mémoire, dans laquelle le joueur peut se déplacer dans toutes les directions, quelle que soit la nature des cellules. Le code utilise `gradle` comme moteur de production. Il suffit de lancer la commande suivante pour compiler et exécuter le jeu. Toutes les dépendances seront automatiquement téléchargées et installées. Le jeu nécessite une version de Java au moins égale à 14. La version 17 est recommandée car il s'agit de la dernière version LTS (*Long Term Support*). 

    $ ./gradlew run


## Affichage

Ajouter l'affichage de tous les éléments (caisses, bonus, clés...) ainsi que les monstres et la princesse.  Les mouvements du joueur sont limités par le cadre de la carte, les éléments de décor et les caisses. 
Le joueur peut marcher sur une case où se trouve un bonus, une clé, ou un autre personnage. 
S’il atteint la princesse, la partie se termine par une victoire.
Si ses points de vie tombent à 0, la partie se termine par une défaite.

## Monstres 
Le joueur perde une vie lorsqu'il se trouve sur la même case qu'un monstre.

## Panneau d’informations

Le panneau d’information affiche le nombre de vies, le nombre de bombes et leur portée, le nombre de clés dans l’inventaire et le numéro de niveau courant.

## Gestion des mondes

Nous avons chargé une configuration complète de jeu depuis un fichier. Vous trouverez un répertoire world à la racine du projet avec un fichier sample.properties représentant un monde avec 3 niveaux. Les fichiers properties en Java permettent de facilement stocker des couples de clés/valeurs.

Voici un exemple de code pour lire la valeur correspondant à la clé `compression` dans le fichier `sample.properties`.

```java
Properties config = new Properties();;
Reader in = new FileReader(file);
config.load(in);
boolean compression = Boolean.parseBoolean(integerProperty(config, "compression", "false"));
```


Clé | Valeur par défaut 
--- |-------------------|
levels | 1                 | 
compression | false             |
bombBagCapacity | 2                 | 
playerLives | 2                 | 
playerInvisibilityTime | 4000              | 
monsterVelocity | 5                 | 
monsterInvisibilityTime | 1000              | 


La clé player était obligatoire et sa valeur représentait les coordonnées (i et j) du joueur sur le premier niveau. Le fichier contenait des clés de la forme levelX où X représentait un numéro de niveau compris entre 1 et la valeur de la clé levels. La valeur associée à un niveau était une chaîne de caractères encodant le niveau avec ou sans compression (RLE) en fonction de la variable compression.
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

Les caisses peuvent être déplacées par le joueur si aucun obstacle n'est présent dans la direction de la poussée. Le joueur ne peut déplacer qu'une seule caisse à la fois. Si un bonus ou un monstre se trouve dans la direction de déplacement d'une caisse, celle-ci reste bloquée. Il est impossible pour le joueur de déplacer deux caisses simultanément. Les caisses peuvent être représentées comme des éléments de décor, ce qui signifie que déplacer une caisse implique de la supprimer à son emplacement initial et de la recréer aux nouvelles coordonnées appropriées.
## Gestion des bombes


Lorsque le joueur appuyait sur la touche [ESPACE], il déposait une bombe sur la case où il se trouvait, déclenchant une explosion au bout de 4 secondes. La mèche de la bombe diminuait chaque seconde. La portée de la bombe était par défaut de 1 case en croix (case du dessus, case du dessous, case de gauche, case de droite). Les éléments de décor stoppaient la propagation de l’explosion dans le sens qu'ils obstruaient. Si une caisse se trouvait sur le chemin de l’explosion, elle disparaissait. Une explosion ne pouvait détruire qu’une seule caisse dans une même direction. Si un bonus (ou un malus) se trouvait sur le chemin de l’explosion, il disparaissait également. Si un joueur ou un monstre se trouvait sur une cellule touchée par une explosion, il perdait une vie. Les explosions n’avaient aucun effet sur les portes et les clés. Lorsqu’une bombe explosait, une nouvelle bombe était ajoutée à l’inventaire du joueur.

Si le joueur pose une bombe et change ensuite de niveau en franchissant une porte, la bombe doit tout de même exploser au bout de 4 secondes. Les éléments de décor détruits sur un niveau doivent le rester pendant toute la durée de la partie.

Si une bombe fait exploser une boîte, un bonus aléatoire peut être généré.

## Gestion des vies

Le joueur peut perdre une vie s’il se trouve sur une case à portée de l’explosion d’une bombe ou s'il croise un monstre. Le joueur bénéficie alors d’une temporisation d'une seconde pendant laquelle il est invulnérable. Si le joueur n’a plus de vie, la partie se termine. 

## Gestion des monstres

Les actions du monstre sont complètement aléatoires. Les collisions avec des monstres entraînent des pertes de vies. Commencez par ajouter les monstres un par un, puis augmentez le nombre de monstres. Les monstres ne peuvent pas récupérer de récompenses au sol. Les monstres ont peur des portes et ne peuvent pas les franchir. Ils ne peuvent pas marcher sur les places des portes. Les monstres ne peuvent pas déplacer les caisses. Lorsque le joueur entre dans le dernier niveau en mode solo ou joue en mode score, si le joueur est proche du monstre, le monstre suivra le joueur.
## Fin de partie

La partie est finie lorsque le joueur arrive sur la case de la
princesse. Les monstres ne veulent pas de mal à la princesse, mais feront
tout pour la garder prisonnière. La touche `[ESCAPE]` permet de quitter la partie à tout moment.


## Mode 2 Players

Mode deux joueurs, également un mode compétitif. Le joueur 1 utilise ZSQD ou WSAD pour se déplacer et ESPACE pour placer une bombe. Le joueur 2 utilise les flèches HAUT, BAS, GAUCHE, DROITE pour se déplacer et B pour placer une bombe.
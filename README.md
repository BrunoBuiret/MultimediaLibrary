# Médiathèque (v2)

## Base de données
La base de données `baseoeuvre` a été complétée de la façon suivante :

```sql
CREATE TABLE `emprunt`(
 `id_emprunt` int(100) unsigned NOT NULL,
 `id_oeuvrepret` int(10) unsigned NOT NULL,
 `id_adherent` int(10) unsigned NOT NULL,
 `date_debut` date NOT NULL,
 `date_fin` date NOT NULL,
 PRIMARY KEY (`id_emprunt`),
 KEY `id_oeuvrepret` (`id_oeuvrepret`),
 KEY `id_adherent` (`id_adherent`),
 FOREIGN KEY (`id_adherent`) REFERENCES `adherent` (`id_adherent`),
 FOREIGN KEY (`id_oeuvrepret`) REFERENCES `oeuvrepret` (`id_oeuvrepret`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## Fonctionnalités supplémentaires

Les fonctionnalités suivantes pourront être implémentées :

* Gestion des prêts
    * Consultation de la liste des prêts
    * Édition d'un prêt
    * Suppression d'un prêt
* Gestion des réservations
    * Consultation de la liste des réservations
    * Confirmation / Rejet d'une réservation
* Système de paginations sur les pages de listes
* Système d'internationalisation via des fichiers de propriétés

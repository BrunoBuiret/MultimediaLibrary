# Médiathèque (v2)

## Base de données
La base de données `baseoeuvre` a été complétée de la façon suivante :

```sql
CREATE TABLE `emprunt`(
 `id_oeuvrepret` int(10) unsigned NOT NULL,
 `id_adherent` int(10) unsigned NOT NULL,
 `date_debut` date NOT NULL,
 `date_fin` date NOT NULL,
 PRIMARY KEY (`id_oeuvrepret`,`id_adherent`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `emprunt`
ADD FOREIGN KEY (`id_oeuvrepret`) REFERENCES `oeuvrepret`(`id_oeuvrepret`);

ALTER TABLE `emprunt`
ADD FOREIGN KEY (`id_adherent`) REFERENCES `adherent`(`id_adherent`);
```

## Fonctionnalités supplémentaires

Les fonctionnalités suivantes pourront être implémentées :

* Consultation de la liste des prêts
* Édition d'un prêt
* Suppression d'un prêt
* Consultation de la liste des réservations
* Confirmation / Rejet d'une réservation

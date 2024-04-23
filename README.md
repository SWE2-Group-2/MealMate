# MealMate

- [MealMate](#mealmate)
  - [Hardware-Voraussetzungen](#hardware-voraussetzungen)
  - [Inbetriebnahme des Vaadin-Server](#inbetriebnahme-des-vaadin-server)
  - [Aufbau der Datenbank](#aufbau-der-datenbank)

![logo](doc_img/logo.jpg)
> MealMate erleichter den Bestellprozess für Benutzer:innen und Restaurantbesitzer:innen. Mit dieser Applikation können Restaurants ihre Speisekarte erstellen und bearbeiten und dem Kund:innen zur verfügungsellen. Die Benutzer:innen können wiederum aus den Speisekarten auswählen und Bestellungen an das Restaurant übermitteln.

## Hardware-Voraussetzungen
- Betriebssystem mit installierter Java Umgebung
  - Windows 10/11
  - MAC OS 10.0.4 oder neuer
  - Linux
- JDK 17 oder höher

## Inbetriebnahme des Vaadin-Server 
```bash
git clone https://github.com/SWE2-Group-2/MealMate.git
cd MealMate
mvn clean package -Pproduction
java -jar target/mealmate-1.0-SNAPSHOT.jar 
```

## Aufbau der Datenbank

![DatabaseDiagramm](doc_img/Database_Diagramm.png)
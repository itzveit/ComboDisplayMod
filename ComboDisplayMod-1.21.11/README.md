# ComboDisplayMod – Minecraft 1.21.11

Clientseitiger Fabric-Mod für eine kleine Combo-Anzeige im HUD.

## Voraussetzungen
- Minecraft Java Edition 1.21.11
- Java 21
- Fabric Loader und Fabric API
- Cloth Config Fabric 21.11.x
- Mod Menu 17.x (optional, für den Konfigurationsknopf)

## Bauen
Mit Java 21 und Gradle:
```sh
gradle build
```
Die Mod-JAR liegt danach in `build/libs/`.

## Konfiguration
In Mod Menu über das Zahnrad öffnen. Ohne Mod Menu werden die Standardwerte verwendet und die Datei
`config/combo-display-mod.json` kann manuell bearbeitet werden.

## Technischer Hinweis
`AttackEntityCallback` meldet einen clientseitigen Angriffsversuch auf ein Entity, nicht die serverseitig bestätigte Schadensvergabe. Da der Mod keine Pakete sendet und rein clientseitig bleibt, kann er nicht in jedem Fall sicher unterscheiden, ob ein Angriff tatsächlich getroffen hat (z. B. bei Reichweite, Unverwundbarkeit oder serverseitigen Regeln). Die Anzeige ist deshalb eine clientseitige Näherung und keine autoritative Trefferbestätigung.

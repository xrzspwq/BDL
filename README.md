# BDL

## Pour compiler l'interface graphique faites depuis le Dossier BDL : 
### Installation dépendence depuis un systeme 64-bits sous Linux
Dans le dossier BDL/window/dependencies faites : 
wget https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_linux-x64_bin-sdk.zip

Et unzip openjfx-21.0.6_linux-x64_bin-sdk.zip 

### Installation dépendence depuis un systeme 64-bits sous windows 
Cliquer sur le lien : https://download2.gluonhq.com/openjfx/21.0.6/openjfx-21.0.6_windows-x64_bin-sdk.zip 

Unzip le fichier 

Puis placer tout le dossier dans BDL/window/dependencies

## Compiler/Executer
Depuis le dossier /BDL faites : 

javac --module-path "window/dependencies/javafx-sdk-21.0.6/lib" --add-modules javafx.controls,javafx.fxml window/*.java

java --module-path "window/dependencies/javafx-sdk-21.0.6/lib" --add-modules javafx.controls,javafx.fxml window.App


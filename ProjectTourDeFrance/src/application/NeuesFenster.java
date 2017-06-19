package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class NeuesFenster extends Stage{

	Stage neueStage;

	public NeuesFenster(){

		neueStage = this;

		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);

		//Buttons in eigener Box anlegen 
		Button dbErstellen = new Button(" Datenbank 'tourdefrance2017' erstellen ");
		dbErstellen.setMinWidth(350);
		dbErstellen.setMinHeight(40);

		Button dbverbindenLIVE = new Button(" Mit der LIVE-Datenbank 'tourdefrance2017' verbinden ");
		dbverbindenLIVE.setMinWidth(350);
		dbverbindenLIVE.setMinHeight(40);

		Button close = new Button(" Schliessen ");
		close.setMinWidth(350);
		close.setMinHeight(40);

		Button dbverbindenVM = new Button(" Mit der VM-Datenbank 'tourdefrance2017' verbinden ");
		dbverbindenVM.setMinWidth(350);
		dbverbindenVM.setMinHeight(40);

		Button tabellenErstellen = new Button(" Tabellen erstellen ");
		tabellenErstellen.setMinWidth(350);
		tabellenErstellen.setMinHeight(40);

		Button einlesen = new Button(" .csv Datei einlesen ");
		einlesen.setMinWidth(350);
		einlesen.setMinHeight(40);

		VBox box = new VBox();
		box.getChildren().addAll(dbErstellen,dbverbindenLIVE,dbverbindenVM,tabellenErstellen,einlesen);
		borderPane.setTop(box);
		BorderPane.setMargin(box, new Insets(10.0, 10.0, 10.0, 10.0));

		HBox hbox= new HBox();
		hbox.getChildren().add(close);
		borderPane.setBottom(hbox);
		BorderPane.setMargin(hbox, new Insets(20.0, 10.0, 10.0, 10.0));

		//EventHandling

		dbErstellen.setOnAction(new EventHandler<ActionEvent>() {	
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.createDb();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "succeed"){
					alert.setTitle("Erfolgreiche Verbindung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich die Datenbank tourdefrance2017 erstellt!");

					alert.showAndWait();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Erstellung der Datenbank tourdefrance2017 fehlgeschlagen");

					alert.showAndWait();
				}
			}
		});

		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				neueStage.close();
			}
		});

		dbverbindenLIVE.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.connectLIVEDB();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "succeed"){
					alert.setTitle("Erfolgreiche Verbindung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich mit der Live-Datenbank 'tourdefrance2017' verbunden!");

					alert.showAndWait();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Verbinden mit der Live-Datenbank 'tourdefrance2017' fehlgeschlagen");

					alert.showAndWait();
				}
			}
		});

		dbverbindenVM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.connectVMDB();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "succeed"){
					alert.setTitle("Erfolgreiche Verbindung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich mit der VM-Datenbank 'tourdefrance2017' verbunden!");

					alert.showAndWait();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Verbinden mit der VM-Datenbank 'tourdefrance2017' fehlgeschlagen");

					alert.showAndWait();
				}
			}
		});

		tabellenErstellen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.tabellenAnlegen();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "succeed"){
					alert.setTitle("Erfolgreiche Erstellung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich alle Tabellen in der Datenbank 'tourdefrance2017' erstellt!");

					alert.showAndWait();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Tabellen konnten nicht angelegt werden!");

					alert.showAndWait();
				}
			}
		});

		einlesen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.ladeDaten();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "succeed"){
					alert.setTitle("Erfolgreiche csv eingelesen");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich die .csv Datei eingelesen");

					alert.showAndWait();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Tabellen konnten nicht angelegt werden!");

					alert.showAndWait();
				}
			}
		});

		neueStage.setTitle("Neues Fenster");
		neueStage.setScene(scene);
		neueStage.show();
	}
}

package application;

import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class TourDeFranceMain extends Application {
	//	try { connection.close();	} catch (SQLException e) {e.printStackTrace();}

	@Override
	public void start(Stage myStage) throws Exception 
	{
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		Label lIp = new Label(" IP Adresse: ");
		Label lPort = new Label(" Port: ");
		Label lUser = new Label(" Nutzername: ");
		Label lPassword = new Label(" Passwort: ");

		final TextField tIp = new TextField();
		tIp.setMinWidth(200);
		final TextField tPort = new TextField();
		final TextField tUser = new TextField();
		PasswordField tPassword = new PasswordField();

		//In der linken Spalte vom BorderPane
		VBox box = new VBox();
		box.getChildren().addAll(lIp,tIp,lPort,tPort,lUser,tUser,lPassword,tPassword);
		borderPane.setLeft(box);
		BorderPane.setMargin(box, new Insets(20.0, 10.0, 4.0, 10.0));

		Button verbinden = new Button(" Verbinden ");
		verbinden.setMinWidth(40);
		verbinden.setMinHeight(30);

		Button schliessen = new Button(" Schließen ");
		schliessen.setMinWidth(40);
		schliessen.setMinHeight(30);

		Button verbindenLIVE = new Button(" Verbinden zum LiveDB - Server ");
		verbindenLIVE.setMinWidth(80);
		verbindenLIVE.setMinHeight(80);

		Button verbindenVM = new Button(" Verbinden zum VM - Server ");
		verbindenVM.setMinWidth(80);
		verbindenVM.setMinHeight(80);

		//In der rechten Spalte vom BorderPane
		VBox box3 = new VBox();
		box3.getChildren().addAll(verbindenVM);
		borderPane.setRight(box3);
		box3.setAlignment(Pos.CENTER);
		BorderPane.setMargin(box3, new Insets(30.0, 10.0, 8.0, 10.0));

		//In der Mittleren Spalte vom BorderPane
		VBox box2 = new VBox();
		box2.getChildren().addAll(verbindenLIVE);
		borderPane.setCenter(box2);
		box2.setAlignment(Pos.CENTER);
		BorderPane.setMargin(box2, new Insets(30.0, 40.0, 8.0, 40.0));

		//In der unteren Spalte vom BorderPane
		HBox buttonbox = new HBox();
		buttonbox.getChildren().addAll(verbinden,schliessen);
		borderPane.setBottom(buttonbox);
		buttonbox.setAlignment(Pos.BOTTOM_LEFT);
		//Der Abstand vom Element zu anderen Elementen: oben, rechts, unten, links
		BorderPane.setMargin(buttonbox, new Insets(4.0, 10.0, 30.0, 10.0));

		//EventHandling

		verbinden.setOnAction(new EventHandler<ActionEvent>() {	
			public void handle(ActionEvent event)
			{
				if(!tIp.getText().isEmpty() && !tPort.getText().isEmpty() && !tUser.getText().isEmpty()
						&& !tPassword.getText().isEmpty())
				{
					String dummy = "";
					dummy = Funktionen.connect(tIp.getText(), tPort.getText(), tUser.getText(), tPassword.getText());

					Alert alert = new Alert(AlertType.INFORMATION);

					if(dummy == "Connection succeed"){
						alert.setTitle("Erfolgreiche Verbindung");
						alert.setHeaderText(null);
						alert.setContentText("Erfolgreich mit dem Server verbunden!");

						alert.showAndWait();
						NeuesFenster nf = new NeuesFenster();
						myStage.close();
					}
					else{
						alert.setTitle("Fehlgeschlagen");
						alert.setHeaderText(null);
						alert.setContentText("Datenbankverbindung fehlgeschlagen. Überprüfen Sie ihre Benutzereingaben");

						alert.showAndWait();
					}
				}
				else{
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("Achtung");
					alert2.setHeaderText(null);
					alert2.setContentText("Bitte alle Felder ausfüllen!");
					alert2.showAndWait();

				}
			}
		});

		schliessen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Programm beenden");
				alert.setHeaderText(null);
				alert.setContentText("Bist du sicher?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					System.exit(0);
				} 
			}
		});

		verbindenLIVE.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.connectLIVE();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "Connection succeed"){
					alert.setTitle("Erfolgreiche Verbindung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich mit dem Live-Server verbunden!");

					alert.showAndWait();
					NeuesFenster nf = new NeuesFenster();
				}
			}
		});
		
		verbindenVM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				String dummy = "";
				dummy = Funktionen.connectVM();

				Alert alert = new Alert(AlertType.INFORMATION);

				if(dummy == "Connection succeed"){
					alert.setTitle("Erfolgreiche Verbindung");
					alert.setHeaderText(null);
					alert.setContentText("Erfolgreich mit dem VM-Server verbunden!");

					alert.showAndWait();
					NeuesFenster nf = new NeuesFenster();
				}
				else{
					alert.setTitle("Fehlgeschlagen");
					alert.setHeaderText(null);
					alert.setContentText("Verbindung mit dem VM-Server fehlgeschlagen");

					alert.showAndWait();
				}
			}
		});

		myStage.setTitle("Datenbank Verbindung");
		myStage.setScene(scene);
		myStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

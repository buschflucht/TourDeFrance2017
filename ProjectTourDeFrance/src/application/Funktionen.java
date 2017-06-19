package application;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Funktionen {

	private static String LIVE_DB = "tourdefrance2017";
	private static final String LIVE_IP = "localhost";  //127.0.0.1
	private static final String LIVE_PORT = "3311";
	private static final String LIVE_USER = "root";
	private static final String LIVE_PW = "chestworkout";
	private static String VM_DB = "tourdefrance2017";
	private static final String VM_IP = "localhost";  //127.0.0.1
	private static final String VM_PORT = "3311";
	private static final String VM_USER = "root";
	private static final String VM_PW = "chestworkout";

	private static final String T_USER = "user";
	private static final String T_ETAPPEN = "etappen";
	private static final String T_FAHRER = "fahrer";
	private static final String T_TEAMS = "teams";
	private static final String T_RANKING = "ranking";
	private static final String T_TIPPS = "tipps";

	private static java.sql.Connection connection = null;
	private static Statement stmt = null;

	/**
	 * Stellt Verbindung zum DBMS her
	 * 
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	public static String connect(String ip, String port, String user, String password) {

		try {
			String url = "jdbc:mysql://" + ip + ":" + port;
			connection = DriverManager.getConnection(url, user, password);

			return "Connection succeed";

		} catch (SQLException e) {return "failed";}
	}


	/**
	 * Diese Methode erzeugt eine Datenbank mit dem Namen als 
	 * Parameter der vom User uebergeben wird
	 * 
	 * @param databaseName
	 *        Datenbankname der Datenbank die in PostgreSQL 
	 *        erstellt werden soll
	 */
	public static String createDb() {

		//Extra nochmal DB selektieren
		try {

			stmt = connection.createStatement();

			stmt.executeQuery("CREATE DATABASE IF NOT EXISTS " + LIVE_DB);

			return "succeed";
		} catch (Exception x) {return "failed";}
	}	

	public static String connectDB(String ip, String port, String user, String pw, String db){

		try {
			stmt = connection.createStatement();
			stmt.executeQuery("CREATE DATABASE IF NOT EXISTS " + db);

			String url = "jdbc:mysql://" + ip + ":" + port + "/" + db;
			connection = DriverManager.getConnection(url, user, pw);

			return "succeed";
		} catch (Exception x) {return "failed";}
	}

	/**
	 * Lädt die lokale etappen.csv datei in die Tabelle etappen
	 * 
	 * @return
	 */
	public static String datenEingeben (){

		String sql = "LOAD DATA LOCAL INFILE './resources/etappen.csv' " + "INTO TABLE etappen";

		try {
			stmt = connection.createStatement();
			stmt.executeQuery(sql);

			return "Successfully loaded csv file into Table 'etappen'";

		} catch (SQLException e) {e.printStackTrace();}
		return "";
	}


	public static String etappenplan(){

		try {
			stmt = connection.createStatement();
			List ll = new LinkedList();
			ResultSet rs = stmt.executeQuery("SELECT * FROM etappen ORDER BY datum");

			// Fetch each row from the result set
			while (rs.next()) {
				int etapnummer = rs.getInt("etappennummer");
				Date datum = rs.getDate("datum");
				String startort = rs.getString("startort");
				String zielort = rs.getString("zielort");
				Double laenge = rs.getDouble("laenge");
				int art = rs.getInt("art");

				System.out.println(etapnummer + "\t" + datum + "\t" + startort + "\t" + zielort
						+ "\t" + laenge + "\t" + art);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Verbindet sich mit vorgegebenen Konstanten als Parameter 
	 * fuer die connect Funktion
	 * 
	 * @return Verbindung zur Virtuellen Maschine mit hinterlegten Daten
	 */
	public static String connectVM() {
		return connect(VM_IP, VM_PORT, VM_USER, VM_PW);
	}

	/**
	 * Verbindet sich mit vorgegebenen Konstanten als Parameter 
	 * fuer die connect Funktion mit Angabe einer Datenbank
	 * 
	 * @return Verbindung zur Live Datenbank mit hinterlegten Daten
	 */
	public static String connectVMDB() {
		return connectDB(VM_IP, VM_PORT, VM_USER, VM_PW, VM_DB);
	}

	/**
	 * Verbindet sich mit vorgegebenen Konstanten als Parameter 
	 * fuer die connect Funktion
	 * 
	 * @return Verbindung zur Live Datenbank mit hinterlegten Daten
	 */
	public static String connectLIVE() {
		return connect(LIVE_IP, LIVE_PORT, LIVE_USER, LIVE_PW);
	}

	/**
	 * Verbindet sich mit vorgegebenen Konstanten als Parameter 
	 * fuer die connect Funktion mit Angabe einer Datenbank
	 * 
	 * @return Verbindung zur Live Datenbank mit hinterlegten Daten
	 */
	public static String connectLIVEDB() {
		return connectDB(LIVE_IP, LIVE_PORT, LIVE_USER, LIVE_PW, LIVE_DB);
	}

	/**
	 * Erstellt neue Tabellen in der MySQL-Datenbank sofern bereits eine Tabelle
	 * mit demselben Namen vorhanden ist, wird diese vorher geloescht
	 * 
	 * 
	 * @return liefert ob die Tabellen erfolgreich angelegt wurde nachdem
	 *         eventuell vorhandene geloescht wurden.
	 */
	public static String tabellenAnlegen() {

		Statement stmt = null;

		try {	
			String sql = "";
			// Loescht alle Tabelle falls vorhanden
			stmt = connection.createStatement();

			//wegen der FOREIGN KEYs sonst loeschen nicht moeglich
			sql = "SET foreign_key_checks = 0";
			stmt.execute(sql);

			sql = "DROP TABLE IF EXISTS " + T_TIPPS;
			stmt.execute(sql);

			sql = "DROP TABLE IF EXISTS " + T_USER;
			stmt.execute(sql);

			sql = "DROP TABLE IF EXISTS " + T_ETAPPEN;
			stmt.execute(sql);

			sql= "DROP TABLE IF EXISTS " + T_TEAMS;
			stmt.execute(sql);

			sql = "DROP TABLE IF EXISTS " + T_FAHRER;
			stmt.execute(sql);

			sql = "DROP TABLE IF EXISTS " + T_RANKING;
			stmt.execute(sql);

			// Erstellt die Tabelle user
			stmt.execute("create table " + T_USER 
					+ "(userID int(11) not null auto_increment,userName varchar(100) not null,"
					+ "sessionID varchar(50) not null," + "vorname varchar(50) not null," + "nachname varchar(50) not null," 
					+ "passwort varchar(50) not null," + "angelegt timestamp default CURRENT_TIMESTAMP not null," + "primary key (userID))");

			// Erstellt die Tabelle etappen
			stmt.execute("create table " + T_ETAPPEN 
					+ "(etappenID int(11) not null auto_increment,etappennummer int(11) not null,"
					+ "datum DATETIME not null," + "startort varchar(50) not null," + "zielort varchar(50) not null," 
					+ "laenge double not null," + "art int(11) not null," + "fahrerPlatz1 varchar(50)," 
					+ "siegerzeit TIME," + "fahrerPlatz2 varchar(50)," + "fahrerPlatz3 varchar(50),"
					+ "teamPlatz1 varchar(50)," + "teamPlatz2 varchar(50),"
					+ "teamPlatz3 varchar(50)," + "fahrerGelb varchar(50),"
					+ "fahrerGruen varchar(50)," + "fahrerBerg varchar(50),"
					+ "dopingFahrer varchar(50)," + "dopingTeam varchar(50),"
					+ "primary key (etappenID))");
			//+ "foreign key (art) references persons(sss)");

			// Erstellt die Tabelle teams
			stmt.execute("create table " + T_TEAMS 
					+ "(teamID int(11) not null auto_increment,teamName varchar(50) not null,"
					+ "teamBildUrl varchar(50),"
					+ "primary key (teamID))");

			// Erstellt die Tabelle fahrer
			stmt.execute("create table " + T_FAHRER
					+ "(fahrerID int(11) not null auto_increment,startnummer int(11) not null,"
					+ "fahrerVorname varchar(50) not null," + "fahrerNachname varchar(50) not null," + "team int(11) not null," 
					+ "aktiv tinyint(1) not null," + "gesamtzeit time," + "etappensiege int(11) not null," 
					+ "punkteGruen int(11) not null," + "punkteBerg int(11) not null,"
					+ "primary key (fahrerID),"
					+ "foreign key (team) references teams(teamID))");

			// Erstellt die Tabelle ranking
			stmt.execute("create table " + T_RANKING 
					+ "(rankingID int(11) not null auto_increment,datum DATETIME not null,"
					+ "userID int(11) not null," + "punkte int(11) not null," + "platz int(11) not null," 
					+ "primary key (rankingID),"
					+ "foreign key (userID) references user(userID))");

			// Erstellt die Tabelle tipps
			stmt.execute("create table " + T_TIPPS 
					+ "(tippID int(11) not null auto_increment,userID int(11) not null,etappenID int(11) not null,"
					+ "fahrerPlatz1 varchar(50)," + "fahrerPlatz2 varchar(50)," + "fahrerPlatz3 varchar(50),"
					+ "teamPlatz1 varchar(50)," + "teamPlatz2 varchar(50),"
					+ "teamPlatz3 varchar(50)," + "fahrerGelb varchar(50),"
					+ "fahrerGruen varchar(50)," + "fahrerBerg varchar(50),"
					+ "fahrerDoping varchar(50)," + "teamDoping varchar(50),"
					+ "primary key (tippID),"
					+ "foreign key (userID) references user(userID),"
					+ "foreign key (etappenID) references etappen(etappenID))");

			sql = "SET foreign_key_checks = 1";
			stmt.execute(sql);

			return "succeed";

		} catch (SQLException e) {return "failed";}
	}

	/**
	 * Lädt die lokale etappen.csv datei in die Tabelle etappen
	 * 
	 * @return
	 */
	public static String ladeDaten(){

		String sql = "LOAD DATA LOCAL INFILE './resources/Testdaten_Etappen_Teams_Fahrer_2016/etappen2016.csv' " 
				+ "INTO TABLE etappen";

		try {
			stmt = connection.createStatement();
			stmt.executeQuery(sql);

			return "succeed";

		} catch (SQLException e) {e.printStackTrace(); return "failed";}
	}
}

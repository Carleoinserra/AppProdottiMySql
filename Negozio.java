import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Negozio {

	 public static void main(String[] args) throws SQLException, ClassNotFoundException {
		 
		 
		 /*Creiamo un negozio che ci consente di vendere i prodotti della nostra tabella
		  * e poi stampa un resoconto con i prodotti acquistati e il prezzo totale da pagare
		  * L'utente può visualizzare tutti i prodotti nella tabella
		  * può acquistare un prodotto dal nome
		  * 
		  * 
		  */
		 
		 // Effettuiamo una connessione indicando quali sono le configurazioni per rragiungere il nostro database
		 String url = "jdbc:mysql://localhost:3306/mydb";
	        String username = "root";
	        String password = "Ilfoggia1";
	        Connection connection = null;
		 
	        
	        // creiamo un arraylist di prodotti che ci consentirà di stampare il riepilogo dei prodotti acquistati
	        // Per il tipo prodotto vedere la classe prodotto
		 ArrayList <Prodotto> listaProdotti = new ArrayList <>();
		 
		 
		 int scelta = 0;
		 do {
			 System.out.println("Cosa vuoi fare?: 1 per visualizzare tutti i prodotti 2 per acquistare un prodotto dal nome");
			 Scanner input = new Scanner (System.in);
			 
			 scelta = input.nextInt();
			 
			 // se la scelta è 1 vengono stampati tutti i prodotti
			 if (scelta == 1) {
				 
				// Creiamo una connessione chimando il metodo getConnection sul driver e passando i dati di configurazione
                 connection = DriverManager.getConnection(url, username, password);
                 String selectQuery = "SELECT * FROM prodotti";
                 
                 // creiamo uno statement semplice: in questo caso non sarà un prepared statement perchè non abbiamo segnaposto
                 Statement stmt2 = connection.createStatement();
                 
                 // Eseguiamo la query sullo statementm passando la query come parametro al metodo execute query
                 // il risultato di questa query restituisce un result set
                 ResultSet rs = stmt2.executeQuery(selectQuery);

                 while (rs.next()) {
                     String nomeProdotto = rs.getString("nome");
                     String marcaProdotto = rs.getString("marca");
                     String categoriaProdotto = rs.getString("categoria");
                     int prezzoProdotto = rs.getInt("prezzo");
                     System.out.println("Nome: " + nomeProdotto + ", Marca: " + marcaProdotto + ", Categoria: " + categoriaProdotto + ", Prezzo: " + prezzoProdotto);
                 }
                 stmt2.close();
				 
				 
			 }
			 /*
			  * Se la scelta è due chiediamo il prodotto che si vuole acquistare
			  * andiamo a selezionare quel prodotto, lo aggiungiamo ad una lista
			  */
			 else if (scelta == 2) {
				 
				 Scanner input1 = new Scanner (System.in);
				 System.out.println("Scrivi il nome del prodotto che vuoi acquistare: ");
				 String nome = input1.next();
				 
				 connection = DriverManager.getConnection(url, username, password);
				 
				 
				 
				 // query per selezionare un prodotto in base al nome
				 
                 String selectQuery = "SELECT * FROM prodotti WHERE nome = (?)";
                 
                 
                 // creiamo un oggetto prepared statement che ci serve per eseguire una query con i segnaposto
                 PreparedStatement stmt = connection.prepareStatement(selectQuery);
                 // settiamo la stringa del segnaposto
                 stmt.setString(1,nome);
                 
                 // la query restituisce una struttura dati result Set
                 ResultSet rs = stmt.executeQuery();
                 
                 
                 /* andiamo a iterare il resultset
                  * Fino a quando ci sono elementi recuperiamo nome, marca e categoria e prezzo
                  * Stampiamo il prodotto acquistato
                  * Aggingiamo il prodotto già instanziato con i valori del resultset nella lista dei prodotti
                  * 
                  * */
                 while(rs.next()) {
                	 
                	String nome1 = rs.getString("nome");
                	String marca = rs.getString("marca");
                	String categoria = rs.getString("categoria");
                	int prezzo = rs.getInt("prezzo");
                	
                	System.out.println("Hai acquistato " + nome1);
                	
                	listaProdotti.add(new Prodotto(nome1, marca, categoria, prezzo));
                 }
				 
			 }
			 
		 }
		 
		 while (scelta != 0);
		 
		 /* 
		  * Fuori dal do while creimao una variabile somma che ci servirà a stampare la somma dei prezzi dei prodotti
		  * 
		  * Scorriamo la lista dei prodotti, stampiamo i prodotti acquistati e incrementiamo la variabile somma con i prezzi
		  * 
		  */
		 int somma = 0;
		 System.out.println("Riepilogo ordine: ");
		 
		 for (Prodotto p1: listaProdotti) {
			 
			 System.out.println(p1);
			 somma += p1.prezzo;
		 }
		 // stampiamo il totale
		 System.out.println("Il totale da pagare è "  + somma);
		 
		 
		 
	 }
}

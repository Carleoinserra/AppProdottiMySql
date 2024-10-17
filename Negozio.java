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
		 String url = "jdbc:mysql://localhost:3306/mydb";
	        String username = "root";
	        String password = "Ilfoggia1";
	        Connection connection = null;
		 
		 ArrayList <Prodotto> listaProdotti = new ArrayList <>();
		 
		 
		 int scelta = 0;
		 do {
			 System.out.println("Cosa vuoi fare?: 1 per visualizzare tutti i prodotti 2 per acquistare un prodotto dal nome");
			 Scanner input = new Scanner (System.in);
			 
			 scelta = input.nextInt();
			 if (scelta == 1) {
				 
				// Stampa tutti i prodotti
                 connection = DriverManager.getConnection(url, username, password);
                 String selectQuery = "SELECT * FROM prodotti";
                 Statement stmt2 = connection.createStatement();
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
			 
			 else if (scelta == 2) {
				 
				 Scanner input1 = new Scanner (System.in);
				 System.out.println("Scrivi il nome del prodotto che vuoi acquistare: ");
				 String nome = input1.next();
				 
				 connection = DriverManager.getConnection(url, username, password);
				 
				 
				 
                 String selectQuery = "SELECT * FROM prodotti WHERE nome = (?)";
                 
                 
                 
                 PreparedStatement stmt = connection.prepareStatement(selectQuery);
                 stmt.setString(1,nome);
                 
                 ResultSet rs = stmt.executeQuery();
                 
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
		 int somma = 0;
		 System.out.println("Riepilogo ordine: ");
		 
		 for (Prodotto p1: listaProdotti) {
			 
			 System.out.println(p1);
			 somma += p1.prezzo;
		 }
		 
		 System.out.println("Il totale da pagare è "  + somma);
		 
		 
		 
	 }
}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class ManipolazioneProdottiTHROWS {
 
	 public static void main(String[] args) throws SQLException, ClassNotFoundException {
	        // mi connetto al mio database
	        String url = "jdbc:mysql://localhost:3306/mydb";
	        String username = "root";
	        String password = "Ilfoggia1";
	        Connection connection = null;
	        Scanner input = new Scanner(System.in);
 
	        // istanzio la scelta per il menu
	        int scelta = 0;
	        do {
	            System.out.println("**** GESTIONE PRODOTTI****** ");
	            System.out.println("Premi 1 per inserire un prodotto ");
	            System.out.println("Premi 2 per stampare tutti i prodotti ");
	            System.out.println("Premi 3 stampa i prodotti di una categoria(fresco frigo banco)");
	            System.out.println("Premi 4 Aggiorna prezzo di un prodotto");
	            System.out.println("**************************** ");
 
	            scelta = input.nextInt();
	            input.nextLine(); // Consuma la newline lasciata da nextInt()
 
	            switch (scelta) {
	                case 1:
	                    // Inserimento di un prodotto
	                    Class.forName("com.mysql.cj.jdbc.Driver");
	                    connection = DriverManager.getConnection(url, username, password);
	                    System.out.println("Connessione al database riuscita!");
 
	                    // Raccolta dati del prodotto
	                    Scanner input1 = new Scanner(System.in);
	                    System.out.println("Inserisci nome prodotto : ");
	                    String nome = input1.nextLine();
	                    System.out.println("Inserisci marca prodotto : ");
	                    String marca = input1.nextLine();
	                    System.out.println("Inserisci categoria prodotto(fresco/frigo/banco): ");
	                    String categoria = input1.nextLine();
	                    System.out.println("Inserisci prezzo prodotto : ");
	                    int prezzo = input1.nextInt();
 
	                    String insertQuery = "INSERT INTO prodotti (nome, marca, categoria, prezzo) VALUES (?, ?, ?, ?)";
	                    PreparedStatement stmt = connection.prepareStatement(insertQuery);
	                    stmt.setString(1, nome);
	                    stmt.setString(2, marca);
	                    stmt.setString(3, categoria);
	                    stmt.setInt(4, prezzo);
 
	                    int rowAffected = stmt.executeUpdate();
	                    System.out.println("Hai aggiunto numero di elementi pari a: " + rowAffected);
	                    stmt.close();
	                    break;
 
	                case 2:
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
	                    break;
 
	                case 3:
	                    // Stampa prodotti in base alla categoria
	                    connection = DriverManager.getConnection(url, username, password);
	                    System.out.println("Inserisci la categoria (fresco, frigo, banco):");
	                    String categoriaFiltro = input.nextLine();
 
	                    String selectByCategoryQuery = "SELECT * FROM prodotti WHERE categoria = ?";
	                    PreparedStatement stmt3 = connection.prepareStatement(selectByCategoryQuery);
	                    stmt3.setString(1, categoriaFiltro);
	                    ResultSet rsCategoria = stmt3.executeQuery();
 
	                    boolean presente = false;
	                    while (rsCategoria.next()) {
	                        String nomeCategoria = rsCategoria.getString("nome");
	                        String marcaCategoria = rsCategoria.getString("marca");
	                        String categoriaProdotto = rsCategoria.getString("categoria");
	                        int prezzoCategoria = rsCategoria.getInt("prezzo");
	                        System.out.println("Nome: " + nomeCategoria + ", Marca: " + marcaCategoria + ", Categoria: " + categoriaProdotto + ", Prezzo: " + prezzoCategoria);
	                        presente = true;
	                    }
 
	                    if (!presente) {
	                        System.out.println("Non è presente alcun prodotto della categoria inserita.");
	                    }
	                    stmt3.close();
	                    break;
 
	                case 4:
	                    // Aggiornamento prezzo prodotto
	                    connection = DriverManager.getConnection(url, username, password);
	                    System.out.println("Inserisci il nome del prodotto a cui cambiare il prezzo: ");
	                    String nomeUpdate = input.nextLine();
	                    System.out.println("Inserisci il nuovo prezzo: ");
	                    int prezzoNuovo = input.nextInt();
 
	                    String updateQuery = "UPDATE prodotti SET prezzo = ? WHERE nome = ?";
	                    PreparedStatement stmt4 = connection.prepareStatement(updateQuery);
	                    stmt4.setInt(1, prezzoNuovo);
	                    stmt4.setString(2, nomeUpdate);
 
	                    int rowsAffected = stmt4.executeUpdate();
	                    if (rowsAffected > 0) {
	                        System.out.println("Prezzo aggiornato con successo");
	                    } else {
	                        System.out.println("Nessun prodotto trovato con il nome: " + nomeUpdate);
	                    }
	                    stmt4.close();
	                    break;
	            }
 
	        } while (scelta != 0);
 
	        input.close();
	    }
	} 
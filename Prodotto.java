
public class Prodotto {
	
	String nome;
	String marca;
	String categoria;
	int prezzo;
	public Prodotto(String nome, String marca, String categoria, int prezzo) {
		
		this.nome = nome;
		this.marca = marca;
		this.categoria = categoria;
		this.prezzo = prezzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	@Override
	public String toString() {
		return "Prodotto [nome=" + nome + ", marca=" + marca + ", categoria=" + categoria + ", prezzo=" + prezzo + "]";
	}
	

}

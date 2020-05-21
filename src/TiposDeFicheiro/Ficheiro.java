package TiposDeFicheiro;

public abstract class Ficheiro {
	//Atributo
	private String nome;
	
	//Construtor
	public Ficheiro(String nome) {
		this.nome=nome;
	}

	//Acessores
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//Metodo
	//Lista os conteudos presentes no ficheiro
	public abstract String listar();
	
	//Este método apaga os conteudos de um ficheiro
	public abstract void resetConteudo();
	
	//Devolve o tipo do ficheiro
	public abstract String getTipoFicheiro();

	//ToString
	@Override
	public String toString() {
		return "Ficheiro [nome=" + nome + "]";
	}
}

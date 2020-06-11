package TiposDeFicheiro;

public abstract class Ficheiro {
	//Atributo
	private String nome;
	private Pasta pai;
	
	//Construtor
	public Ficheiro(String nome) {
		this.nome=nome;
		this.pai=null;
	}

	//Acessores
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Pasta getPai() {
		return pai;
	}

	public void setPai(Pasta pai) {
		this.pai = pai;
	}
	
	//Metodo
	//Lista os conteudos presentes num ficheiro
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

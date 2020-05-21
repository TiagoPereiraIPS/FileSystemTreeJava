package TiposDeFicheiro;

public class FicheiroDeTexto extends Ficheiro{
	//Atributo
	private String corpoTexto;
	
	//Construtor
	public FicheiroDeTexto(String nome, String corpoTexto) {
		super(nome);
		this.corpoTexto=corpoTexto;
	}
	
	//Acessores
	public String getCorpoTexto() {
		return corpoTexto;
	}

	public void setCorpoTexto(String corpoTexto) {
		this.corpoTexto = corpoTexto;
	}
	
	//Metodo
	//Acrescenta texto ao ja existente
	public void escrever(String texto) {
		this.corpoTexto+=" "+texto;
	}
	
	//Substitui o texto atual por outro escrito pelo utilizador
	public void substituirTexto(String texto) {
		this.setCorpoTexto(texto);
	}
	
	@Override
	public String listar() {
		if(corpoTexto.isEmpty()) {
			return "O ficheiro de texto encontra-se vazio";
		}
		
		return this.getCorpoTexto();
	}

	@Override
	public void resetConteudo() {
		this.setCorpoTexto("");
	}
	
	@Override
	public String getTipoFicheiro() {
		return "Ficheiro de Texto";
	}

	//ToString
	@Override
	public String toString() {
		return "FicheiroDeTexto [nome=" + this.getNome() + "corpoTexto=" + corpoTexto + "]";
	}
}

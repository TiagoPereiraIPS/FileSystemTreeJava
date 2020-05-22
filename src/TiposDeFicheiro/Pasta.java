package TiposDeFicheiro;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Pasta extends Ficheiro {
	//Atributo
	private LinkedList<Ficheiro> ficheiros;
	
	//Construtor
	public Pasta(String nome) {
		super(nome);
		ficheiros = new LinkedList<Ficheiro>();
	}

	//Acessor
	public LinkedList<Ficheiro> getFicheiros() {
		return ficheiros;
	}
	
	//Metodos
	//Insere um ficheiro na pasta
	public void inserir(Ficheiro ficheiro) {
		ficheiros.add(ficheiro);
		organizar();
	}
	
	//Remove o ficheiro da pasta seleccionado
	public void remover(Ficheiro ficheiro) {
		ficheiros.remove(ficheiros.indexOf(ficheiro));
	}
	
	//Verifica se um ficheiro existe na pasta atraves do nome
	public boolean existe(String nome) {
		boolean existe=false;
		
		for(Ficheiro ficheiro: ficheiros) {
			if(ficheiro.getNome().equals(nome)) {
				return true;
			}
		}
		
		return existe;
	}
	
	//Verifica se um ficheiro existe numa pasta e retorna o mesmo atraves do nome
	public Ficheiro retornaFicheiro(String nome) {
		for(Ficheiro ficheiro: ficheiros) {
			if(ficheiro.getNome().equals(nome)) {
				return ficheiro;
			}
		}
		
		return null;
	}
	
	//Organiza os ficheiros na pasta de forma alfabetica
	private void organizar() {
		Collections.sort(ficheiros, new Comparator<Ficheiro>() {
			@Override
			public int compare(Ficheiro o1, Ficheiro o2) {
				return o1.getNome().toUpperCase().compareTo(o2.getNome().toUpperCase());
			}
		});
	}
	
	@Override
	public String listar() {
		String listagem = "";
		
		if(ficheiros.isEmpty()) {
			return "A pasta encontra-se vazia";
		}
		
		for(Ficheiro ficheiro: ficheiros) {
			if(listagem.isEmpty()) {
				listagem = (ficheiros.indexOf(ficheiro)+1) + " - " + ficheiro.getNome() + " (" + ficheiro.getTipoFicheiro() + ")";
			} else {
				listagem+="\n"+ (ficheiros.indexOf(ficheiro)+1) + " - " + ficheiro.getNome() + " (" + ficheiro.getTipoFicheiro() + ")";
			}
		}
		
		return listagem;
	}
	
	@Override
	public void resetConteudo() {
		this.ficheiros = new LinkedList<Ficheiro>();
	}
	
	@Override
	public String getTipoFicheiro() {
		return "Pasta";
	}

	//ToString
	@Override
	public String toString() {
		return "Pasta [nome=" + this.getNome() + ", ficheiros=" + ficheiros + "]";
	}
}

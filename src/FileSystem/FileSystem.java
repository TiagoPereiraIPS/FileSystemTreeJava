package FileSystem;

import java.util.LinkedList;

import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.Pasta;

public class FileSystem implements IFileSystem {
	//Atributo
	public Pasta root;
	
	//Construtor
	public FileSystem() {
		this.root=new Pasta("root");
	}
	
	//Acessor
	public Pasta getRoot() {
		return root;
	}
	
	//Metodos
	@Override
	public void inserir(Pasta pasta, Ficheiro ficheiro) {
		pasta.inserir(ficheiro);
	}

	@Override
	public String apagar(Pasta pasta, Ficheiro ficheiro) {
		if(pasta.existe(ficheiro.getNome())) {
			pasta.remover(ficheiro);
			return "Ficheiro " + ficheiro.getNome() + " eliminado com sucesso";
		}
		return "Falha na eliminação, ficheiro não existente";
	}

	@Override
	public Ficheiro pesquisarFicheiro(String nome) {
		LinkedList<Pasta> porVerificar = new LinkedList<Pasta>();
		
		porVerificar.add(root);
		
		while(!porVerificar.isEmpty()) {
			Pasta temp = porVerificar.poll();
			
			if(temp.getNome().equals(nome)) {
				return temp;
			}
			
			if(temp.existe(nome)) {
				return temp.retornaFicheiro(nome);
			}
			
			for(Ficheiro ficheiro: temp.getFicheiros()) {
				if(ficheiro.getTipoFicheiro().equals("Pasta")) {
					porVerificar.add((Pasta)ficheiro);
				}
			}
		}
		
		return null;
	}

	@Override
	public String listarFicheirosFileSystem() {
		if (root.getFicheiros().isEmpty()) {
			return "-" + root.getNome() + "(" + root.getTipoFicheiro() + ")" + "\n";
		}
		
		return preOrdem(root, 0);
	}

	private String preOrdem(Pasta ficheiro, int nivel) {
		String resultado = "";
		
		if (ficheiro != null) {
			for(int i=0; i<nivel; i++) {
				resultado+="  ";
			}
			resultado += "-" + ficheiro.getNome() + "(" + ficheiro.getTipoFicheiro() + ")" + "\n";
		}
		
		for(Ficheiro ficheir: ficheiro.getFicheiros()) {
			if(ficheir.getTipoFicheiro().equals("Pasta")) {
				resultado+=preOrdem((Pasta)ficheir, nivel+1);
			} else {
				for(int i=0; i<nivel+1; i++) {
					resultado+="  ";
				}
				resultado += "-" + ficheir.getNome() + "(" + ficheir.getTipoFicheiro() + ")" + "\n";
			}
		}
		
		return resultado;
	}
	
	//ToString
	@Override
	public String toString() {
		return "FileSystem [root=" + root + "]";
	}
}

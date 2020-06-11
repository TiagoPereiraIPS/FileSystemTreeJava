package FileSystem;

import java.util.LinkedList;

import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.Pasta;

public class FileSystem implements IFileSystem {
	//Atributo
	private Pasta root;
	private Pasta atual;
	
	//Construtor
	public FileSystem() {
		this.root=new Pasta("root");
		this.atual=getRoot();
	}
	
	//Acessor
	public Pasta getRoot() {
		return root;
	}
	
	public Pasta getAtual() {
		return atual;
	}
	
	public void setAtual(Pasta atual) {
		this.atual = atual;
	}
	
	//Metodos
	@Override
	public String inserir(Ficheiro ficheiro) {
		if(!(getAtual().existe(ficheiro.getNome()))) {
			atual.inserir(ficheiro);
			return ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " criado com sucesso.\n";
		}
		return "Não pode repetir nomes de ficheiros existentes.\n";
	}
	
	@Override
	public String apagar(Ficheiro ficheiro) {
		if(atual.existe(ficheiro.getNome())) {
			atual.remover(ficheiro);
			return ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " eliminado com sucesso.\n";
		}
		return "Falha na eliminação, ficheiro não existente.\n";
	}
	
	@Override
	public Ficheiro retornaFicheiro(String nome) {
		return getAtual().retornaFicheiro(nome);
	}

	@Override
	public boolean existe(String nome) {
		return getAtual().existe(nome);
	}
	
	@Override
	public String listar() {
		return getAtual().listar() + "\n";
	}
	
	@Override
	public String resetConteudoAtual() {
		getAtual().resetConteudo();
		return "Pasta " + getAtual().getNome() + " resetada com sucesso.\n";
	}
	
	@Override
	public String resetConteudo(String nome) {
		Ficheiro ficheiro = retornaFicheiro(nome);
		ficheiro.resetConteudo();
		return ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " resetado com sucesso.\n";
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
	
	@Override
	public String listarFicheirosFileSystemAtual() {
		if (atual.getFicheiros().isEmpty()) {
			return "-" + atual.getNome() + "(" + atual.getTipoFicheiro() + ")" + "\n";
		}
		
		return preOrdem(atual, 0);
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

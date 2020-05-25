package FileSystem;

import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.Pasta;

public interface IFileSystem {
	//Inserir um novo ficheiro(no) dentro da Pasta(no) pretendida
	void inserir(Pasta pasta, Ficheiro ficheiro);
	
	//Apagar um ficheiro(no) dentro da Pasta(no) pretendida
	String apagar(Pasta pasta, Ficheiro ficheiro);
	
	//Pesquisa por um ficheiro na arvore e devolve o mesmo (pesquisa por largura)
	Ficheiro pesquisarFicheiro(String nome);
	
	//Lista todos os ficheiros do filesystem(arvore) (metodo de listagem preOrder)
	String listarFicheirosFileSystem();
}

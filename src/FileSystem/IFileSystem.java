package FileSystem;

import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.Pasta;

public interface IFileSystem {
	//Inserir um novo ficheiro(no) dentro da Pasta(no) atual
	String inserir(Ficheiro ficheiro);
	
	//Apagar um ficheiro(no) dentro da Pasta(no) atual
	String apagar(Ficheiro ficheiro);
	
	//Retorna um ficheiro na pasta atual
	Ficheiro retornaFicheiro(String nome);
	
	//Verifica se existe algum ficheiro com o nome dado na pasta atual
	boolean existe(String nome);
	
	//Lista os conteudos presentes num ficheiro
	String listar();
	
	//Este método apaga os conteudos da pasta atual
	String resetConteudoAtual();
	
	//Este método apaga os conteudos de um ficheiro
	String resetConteudo(String nome);
	
	//Pesquisa por um ficheiro na arvore e devolve o mesmo (pesquisa por largura)
	Ficheiro pesquisarFicheiro(String nome);
	
	//Lista todos os ficheiros do filesystem(arvore) (metodo de listagem preOrder)
	String listarFicheirosFileSystem();
}

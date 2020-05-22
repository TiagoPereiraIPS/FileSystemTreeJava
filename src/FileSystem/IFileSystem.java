package FileSystem;

import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.Pasta;

public interface IFileSystem {
	//Inserir um novo ficheiro(no) dentro do no(Pasta) atual
	//public String inserir(Ficheiro ficheiro);
	
	//Inserir um novo ficheiro(no) dentro da Pasta(no) pretendida
	public void inserir(Pasta pasta, Ficheiro ficheiro);
	
	//Apagar um ficheiro(no) dentro da no(Pasta) atual
	//public String apagar(Ficheiro ficheiro);
	
	//Apagar um ficheiro(no) dentro da Pasta(no) pretendida
	public String apagar(Pasta pasta, Ficheiro ficheiro);
	
	//Pesquisa por um ficheiro na arvore e devolve o mesmo (pesquisa por largura)
	public Ficheiro pesquisarFicheiro(String nome);
	
	//Lista todos os ficheiros do filesystem(arvore) (metodo de listagem preOrder)
	public String listarFicheirosFileSystem();
	
	//public String listar(Pasta pasta);
	
	//public String resetarConteudo(Pasta pasta);
	
	//public boolean existeFiceiro(Pasta pasta);
	
	//public Ficheiro existeFicheiroRetorno(Pasta pasta);
	
	//public String getTipoFicheiro(Ficheiro ficheiro);
}

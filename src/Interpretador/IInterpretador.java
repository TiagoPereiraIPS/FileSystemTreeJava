package Interpretador;

import TiposDeFicheiro.Ficheiro;

public interface IInterpretador {
	//Recebe o input do utilizador, interpreta-o e encaminha-o para a operacao a realizar
	void processarInput(String input);
	
	//Processa e cria ficheiros
	//public void criarFicheiro(Pasta pasta, Ficheiro ficheiro);
	
	//Processa e acrescenta dados a ficheiros existentes
	//public void acrescentaDados(Ficheiro ficheiro, String dados);
	
	//Processa e substitui dados de ficheiros existentes
	//public void substituiDados(Ficheiro ficheiro, String dados);
	
	//Processa e apaga ficheiros existentes
	//public void apagarFicheiro(Ficheiro ficheiro);
	
	//Validacao de um ficheiro
	boolean validacaoFicheiro(Ficheiro ficheiro);
	
	//Validacao dos dados de um ficheiro
	boolean validacaoDados(String dados);
	
	//Lista ficheiros do fileSystem
	
	//Inserir um novo ficheiro(no) dentro do no(Pasta) atual
	//public String inserir(Ficheiro ficheiro);
	
	//Apagar um ficheiro(no) dentro da no(Pasta) atual
	//public String apagar(Ficheiro ficheiro);
	
	//public String listar(Pasta pasta);
	
	//public String resetarConteudo(Pasta pasta);
	
	//public boolean existeFiceiro(Pasta pasta);
	
	//public Ficheiro existeFicheiroRetorno(Pasta pasta);
	
	//public String getTipoFicheiro(Ficheiro ficheiro);
}

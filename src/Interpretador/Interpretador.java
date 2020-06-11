package Interpretador;

import FileSystem.FileSystem;
import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.FicheiroDeCalculo;
import TiposDeFicheiro.FicheiroDeTexto;
import TiposDeFicheiro.Pasta;

public class Interpretador {
	//Atributo
	private FileSystem fileSystem;
	private Pasta atual;
	
	//Contrutor
	public Interpretador(String input) {
		this.fileSystem = new FileSystem();
	}

	//Metodos
	//Recebe o input do utilizador, interpreta-o e encaminha-o para a operacao a realizar apos a sua validacao
	public void processarInput(String input) {
		String[] inputDividido = input.split("\\s+");
		
		if(validacaoInputFicheiroPasta(inputDividido)) {
			switch(inputDividido[1].toUpperCase()) {
			case "FOLDER":
				ficheiroPasta(inputDividido);
				break;
			case "TEXTFILE":
				ficheiroDeTextoOuCalculo(inputDividido);
				break;
			case "SPREADSHEET":
				ficheiroDeTextoOuCalculo(inputDividido);
				break;
			}
		}
		
		if(validacaoNavegacao(inputDividido)) {
			navegar(inputDividido);
		}
	}
	
	//Permite entrar e retroceder nas pastas
	private void navegar(String[] dados) {
		switch(dados[0].toUpperCase()) {
		case "ENTERFOLDER":
			if(atual.existe(dados[1]) && atual.retornaFicheiro(dados[1]).getTipoFicheiro().equals("Pasta")) {
				atual = (Pasta) atual.retornaFicheiro(dados[1]);
			}
			break;
		case "BACKFOLDER":
			if(atual.getPai() != null) {
				atual = atual.getPai();
			}
			break;
		}
	}
	
	//Realiza as operacoes do input do utilizador relativas a um ficheiro de texto ou um ficheiro de calculo
	private void ficheiroDeTextoOuCalculo(String[] argumentos) {
		switch(argumentos[0].toUpperCase()) {
		case "MAKE":
			if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
				atual.inserir(new FicheiroDeTexto(argumentos[1], argumentos[2]));
			} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
				atual.inserir(new FicheiroDeCalculo(nome));
			}
			break;
		case "DELETE":
			
			break;
		case "REPLACEDATA":

			break;
		case "LIST":

			break;
		case "RESET":

			break;
		case "ADDDATA":

			break;
		}
	}

	//Realiza as operacoes do input do utilizador relativas a uma pasta
	private void ficheiroPasta(String[] argumentos) {
		switch(argumentos[0].toUpperCase()) {
		case "MAKE":
			
			break;
		case "DELETE":
			
			break;
		case "REPLACEDATA":

			break;
		case "LIST":

			break;
		case "RESET":

			break;
		case "ADDDATA":

			break;
		}
	}
	
	//Valida o input do utilizador em relacao aos ficheiros
	private boolean validacaoInputFicheiroPasta(String[] dados) {
		try {
			if(!(dados[0].toUpperCase().equals("MAKE") || dados[0].equals("DELETE") || dados[0].equals("REPLACEDATA") || dados[0].equals("LIST") || dados[0].equals("RESET") || dados[0].equals("ADDDATA"))) {
				return false;
			}
			
			if(!(dados[1].toUpperCase().equals("FOLDER") || dados[1].toUpperCase().equals("TEXTFILE") || dados[1].toUpperCase().equals("SPREADSHEET"))) {
				return false;
			}
			
			if(!(dados[2].length() > 0)) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
		//caso o index 2 nao exista a string e valida, pois para, por exemplo criar uma pasta nao precisamos do index 2 do array de strings
		try {
			if(dados[3].length() > 0) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			return true;
		}
		return true;
	}
	
	//Valida o input do utilizador em relacao aos ficheiros
	private boolean validacaoNavegacao(String[] dados) {
		try {
			if(!(dados[0].toUpperCase().equals("ENTERFOLDER") || dados[0].toUpperCase().equals("BACKFOLDER"))) {
				return false;
			}
			
			if(!(dados[1].length() > 0)) {
				return false;
			}
			return true;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
	}

	//Validacao de um ficheiro
	private boolean validacaoFicheiro(Ficheiro ficheiro) {
		
		return false;
	}

	//Validacao dos dados a inserir num ficheiro
	private boolean validacaoDados(String[] dados) {
		
		return false;
	}
	
	//ToString
	@Override
	public String toString() {
		return "Interpretador [fileSystem=" + fileSystem + "]";
	}
}

package Interpretador;

import FileSystem.FileSystem;
import TiposDeFicheiro.Ficheiro;

public class Interpretador {
	//Atributo
	FileSystem fileSystem;
	
	//Contrutor
	public Interpretador(String input) {
		this.fileSystem = new FileSystem();
	}

	//Metodos
	//Recebe o input do utilizador, interpreta-o e encaminha-o para a operacao a realizar apos a sua validacao
	public void processarInput(String input) {
		String[] inputDividido = input.split("\\s+");
		
		if(validacaoInput(inputDividido)) {
			
		}
		
	}
	
	//Valida o input do utilizador
	public boolean validacaoInput(String[] dados) {
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

	//Validacao de um ficheiro
	public boolean validacaoFicheiro(Ficheiro ficheiro) {
		
		return false;
	}

	//Validacao dos dados a inserir num ficheiro
	public boolean validacaoDados(String dados) {
		
		return false;
	}
	
	//ToString
	@Override
	public String toString() {
		return "Interpretador [fileSystem=" + fileSystem + "]";
	}
}

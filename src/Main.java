import java.util.Scanner;

import FileSystem.FileSystem;
import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.FicheiroDeCalculo;
import TiposDeFicheiro.FicheiroDeTexto;
import TiposDeFicheiro.Pasta;

public class Main {
	static FileSystem fileSystem = new FileSystem();
	
	public static void main(String[] args) {
		String input;
		Scanner comando = new Scanner(System.in);
		
		System.out.println("Esperando input do utilizador");
		input = comando.nextLine();
		
		processarInput(input);
		
		main(args);
	}
	
	//Metodos
	//Recebe o input do utilizador, interpreta-o e encaminha-o para a operacao a realizar apos a sua validacao
	public static void processarInput(String input) {
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
		} else if(validacaoNavegacao(inputDividido)) {
			navegar(inputDividido);
		} else {
			System.out.println("O comando introduzido não cumpre com as normas.\n");
		}
	}
	
	//Permite entrar e retroceder nas pastas
	private static void navegar(String[] dados) {
		switch(dados[0].toUpperCase()) {
		case "ENTERFOLDER":
			if(fileSystem.existe(dados[1]) && fileSystem.retornaFicheiro(dados[1]).getTipoFicheiro().equals("Pasta")) {
				fileSystem.setAtual((Pasta) fileSystem.retornaFicheiro(dados[1]));
				System.out.println("Entrou na pasta " + fileSystem.getAtual());
			} else {
				System.out.println("O ficheiro especificado não existe ou não é uma pasta.\n");
			}
			break;
		case "BACKFOLDER":
			if(fileSystem.getAtual().getPai() != null) {
				System.out.println("Saiu da pasta " + fileSystem.getAtual() + ".");
				fileSystem.setAtual(fileSystem.getAtual().getPai());
				System.out.println("Entrou na pasta " + fileSystem.getAtual() + ".\n");
			} else {
				System.out.println("Não existe pasta anterior.\n");
			}
			break;
		case "LISTALL":
			System.out.println(fileSystem.listarFicheirosFileSystem());
			break;
		case "LISTALLCURRENT":
			System.out.println(fileSystem.listarFicheirosFileSystemAtual());
			break;
		}
	}
	
	//Realiza as operacoes do input do utilizador relativas a um ficheiro de texto ou um ficheiro de calculo
	private static void ficheiroDeTextoOuCalculo(String[] argumentos) {
		switch(argumentos[0].toUpperCase()) {
		case "MAKE":
			if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
				String textoCorpo = "";
				for(int i = 3; i < argumentos.length; i++) {
					textoCorpo += argumentos[i] + " ";
				}
				System.out.println(fileSystem.inserir(new FicheiroDeTexto(argumentos[2], textoCorpo)));
			} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
				System.out.println(fileSystem.inserir(new FicheiroDeCalculo(argumentos[2])));
			} else {
				System.out.println("O comando introduzido não cumpre com as normas.\n");
			}
			break;
		case "DELETE":
			System.out.println(fileSystem.apagar(fileSystem.retornaFicheiro(argumentos[2])));
			break;
		case "REPLACEDATA":
			if(fileSystem.existe(argumentos[2])) {
				if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
					String textoSubstituto = "";
					FicheiroDeTexto ficheiro = (FicheiroDeTexto) fileSystem.retornaFicheiro(argumentos[2]);
					for(int i = 3; i < argumentos.length; i++) {
						textoSubstituto += argumentos[i] + " ";
					}
					ficheiro.substituirTexto(textoSubstituto);
					System.out.println("Dados do " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " substituídos com sucesso.\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					ficheiro.substituirEscrever(Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[5]), argumentos[4]);
					System.out.println("Dados do " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " substituídos com sucesso.\n");
				}
			} else {
				System.out.println("O ficheiro especificado não existe.\n");
			}
			break;
		case "LIST":
			if(fileSystem.existe(argumentos[2])) {
				if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
					FicheiroDeTexto ficheiro = (FicheiroDeTexto) fileSystem.retornaFicheiro(argumentos[2]);
					System.out.println(ficheiro.listar() + "\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					System.out.println(ficheiro.listar() + "\n");
				}
			} else {
				System.out.println("O ficheiro especificado não existe.\n");
			}
			break;
		case "RESET":
			if(fileSystem.existe(argumentos[2])) {
				System.out.println(fileSystem.resetConteudo(argumentos[2]));
			} else {
				System.out.println("O ficheiro especificado não existe.\n");
			}
			break;
		case "ADDDATA":
			if(fileSystem.existe(argumentos[2])) {
				if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
					String textoAcrescentar = "";
					FicheiroDeTexto ficheiro = (FicheiroDeTexto) fileSystem.retornaFicheiro(argumentos[2]);
					for(int i = 3; i < argumentos.length; i++) {
						textoAcrescentar += argumentos[i] + " ";
					}
					ficheiro.escrever(textoAcrescentar);
					System.out.println("Conteúdos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					ficheiro.escrever(Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[5]), argumentos[4]);
					System.out.println("Conteúdos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				} else {
					System.out.println("Os dados inseridos não são válidos.\n");
				}
				break;
			} else {
				System.out.println("O ficheiro especificado não existe.\n");
			}
		}
	}

	//Realiza as operacoes do input do utilizador relativas a uma pasta
	private static void ficheiroPasta(String[] argumentos) {
		switch(argumentos[0].toUpperCase()) {
		case "MAKE":
			if(!(fileSystem.existe(argumentos[2]))) {
				System.out.println(fileSystem.inserir(new Pasta(argumentos[2])));
			} else {
				System.out.println("Não pode repetir nomes de ficheiros.\n");
			}
			break;
		case "DELETE":
			System.out.println(fileSystem.apagar(fileSystem.retornaFicheiro(argumentos[2])));
			break;
		case "REPLACEDATA":
			System.out.println("Não é possível substituir ficheiros de uma pasta.\n");
			break;
		case "LIST":
			System.out.println(fileSystem.listar());
			break;
		case "RESET":
			System.out.println(fileSystem.resetConteudoAtual());
			break;
		case "ADDDATA":
			System.out.println("Não é possível realizar este comando numa pasta.\n");
			break;
		}
	}
	
	//Valida o input do utilizador em relacao aos ficheiros
	private static boolean validacaoInputFicheiroPasta(String[] dados) {
		try {
			if(!(dados[0].toUpperCase().equals("MAKE") || dados[0].toUpperCase().equals("DELETE") || dados[0].toUpperCase().equals("REPLACEDATA") || dados[0].toUpperCase().equals("LIST") || dados[0].toUpperCase().equals("RESET") || dados[0].toUpperCase().equals("ADDDATA"))) {
				return false;
			}
			if(!(dados[1].toUpperCase().equals("FOLDER") || dados[1].toUpperCase().equals("TEXTFILE") || dados[1].toUpperCase().equals("SPREADSHEET"))) {
				return false;
			}
			if(!(dados[2].length() > 0)) {
				return false;
			}
		} catch(IndexOutOfBoundsException e) {
			if(dados[1].toUpperCase().equals("FOLDER") && !(dados[0].toUpperCase().equals("MAKE") || dados[0].toUpperCase().equals("DELETE"))) {
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	//Valida o input do utilizador em relacao aos ficheiros
	private static boolean validacaoNavegacao(String[] dados) {
		try {
			if(!(dados[0].toUpperCase().equals("ENTERFOLDER") || dados[0].toUpperCase().equals("BACKFOLDER") || dados[0].toUpperCase().equals("LISTALL") || dados[0].toUpperCase().equals("LISTALLCURRENT"))) {
				return false;
			}
			
			if(!(dados[1].length() > 0)) {
				return false;
			}
			return true;
		} catch(IndexOutOfBoundsException e) {
			if(dados[0].toUpperCase().equals("BACKFOLDER") || dados[0].toUpperCase().equals("LISTALL") || dados[0].toUpperCase().equals("LISTALLCURRENT")) {
				return true;
			} else {
				return false;
			}
		}
	}

	//Validacao dos dados a inserir num ficheiro
	private static boolean validacaoDados(String[] dados) {
		switch(dados[1].toUpperCase()) {
		case "TEXTFILE":
			try {
				if(dados[3].length() > 0) {
					return true;
				} else {
					return false;
				}
			} catch(IndexOutOfBoundsException ex) {
				if(dados[0].toUpperCase().equals("LIST")) {
					return true;
				} else {
					return false;
				}
		    }
		case "SPREADSHEET":
			try {
				if(!(dados[4].equals("+") || dados[4].equals("-") || dados[4].equals("*") || dados[4].equals("/") || dados[4].equals("%") || dados[4].equals("^"))) {
					return false;
				}
			
				try {
					int var = Integer.parseInt(dados[3]);
					var = Integer.parseInt(dados[5]);
			    } catch(NumberFormatException ex) {
			        return false;
			    }
			} catch(IndexOutOfBoundsException ex) {
				if(dados[0].toUpperCase().equals("MAKE") || dados[0].toUpperCase().equals("LIST")) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}

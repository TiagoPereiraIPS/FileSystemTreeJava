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
		
		processarInput(input.trim());
		
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
		} else if(validacaoHelp(inputDividido)) {
			help();
		} else {
			System.out.println("O comando introduzido n�o cumpre com as normas.\n");
		}
	}
	
	//Permite entrar e retroceder nas pastas, assim como realizar listagens de ficheiros
	private static void navegar(String[] dados) {
		switch(dados[0].toUpperCase()) {
		case "ENTERFOLDER":
			if(fileSystem.existe(dados[1]) && fileSystem.retornaFicheiro(dados[1]).getTipoFicheiro().equals("Pasta")) {
				fileSystem.setAtual((Pasta) fileSystem.retornaFicheiro(dados[1]));
				System.out.println("Entrou na pasta " + fileSystem.getAtual().getNome());
			} else {
				System.out.println("O ficheiro especificado n�o existe ou n�o � uma pasta.\n");
			}
			break;
		case "BACKFOLDER":
			if(fileSystem.getAtual().getPai() != null) {
				System.out.println("Saiu da pasta " + fileSystem.getAtual().getNome() + ".");
				fileSystem.setAtual(fileSystem.getAtual().getPai());
				System.out.println("Entrou na pasta " + fileSystem.getAtual().getNome() + ".\n");
			} else {
				System.out.println("N�o existe pasta anterior.\n");
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
				System.out.println("O comando introduzido n�o cumpre com as normas.\n");
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
					System.out.println("Dados do " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " substitu�dos com sucesso.\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					ficheiro.substituirEscrever(Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[5]), argumentos[4]);
					System.out.println("Dados do " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " substitu�dos com sucesso.\n");
				}
			} else {
				System.out.println("O ficheiro especificado n�o existe.\n");
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
				System.out.println("O ficheiro especificado n�o existe.\n");
			}
			break;
		case "RESET":
			if(fileSystem.existe(argumentos[2])) {
				System.out.println(fileSystem.resetConteudo(argumentos[2]));
			} else {
				System.out.println("O ficheiro especificado n�o existe.\n");
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
					System.out.println("Conte�dos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					ficheiro.escrever(Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[5]), argumentos[4]);
					System.out.println("Conte�dos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				} else {
					System.out.println("Os dados inseridos n�o s�o v�lidos.\n");
				}
				break;
			} else {
				System.out.println("O ficheiro especificado n�o existe.\n");
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
				System.out.println("N�o pode repetir nomes de ficheiros.\n");
			}
			break;
		case "DELETE":
			System.out.println(fileSystem.apagar(fileSystem.retornaFicheiro(argumentos[2])));
			break;
		case "REPLACEDATA":
			System.out.println("N�o � poss�vel substituir ficheiros de uma pasta.\n");
			break;
		case "LIST":
			System.out.println(fileSystem.listar());
			break;
		case "RESET":
			System.out.println(fileSystem.resetConteudoAtual());
			break;
		case "ADDDATA":
			System.out.println("N�o � poss�vel realizar este comando numa pasta.\n");
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
	
	//
	private static boolean validacaoHelp(String[] argumentos) {
		try {
			return argumentos[0].toUpperCase().equals("HELP");
		}catch(IndexOutOfBoundsException ex) {
			return false;
		}
	}

	//Fornece informa��es acerca dos comandos a utilizar e redireciona para as informa��es
	private static void help() {
		System.out.println("Insira 1 para informa��es acerca de ficheiros de texto;");
		System.out.println("Insira 2 para informa��es acerca de ficheiros de c�lculo;");
		System.out.println("Insira 3 para informa��es acerca de pastas;");
		System.out.println("Insira 4 para informa��es acerca de navega��o e listagens gerais.");
		
		String input;
		Scanner comando = new Scanner(System.in);
		
		System.out.println("Esperando numero da op��o, para cancelar esta opera��o insira qualquer outro carater.");
		input = comando.nextLine();
		
		switch(input.trim()) {
		case "1":
			infoFicheiroDeTexto();
			break;
		case "2":
			infoFicheiroDeCalculo();
			break;
		case "3":
			infoPastas();
			break;
		case "4":
			infoNavegacao();
			break;
		}
	}

	//Informa��es detalhadas acerca dos ficheiros de texto
	private static void infoFicheiroDeTexto() {
		System.out.println("Exemplos de comandos");
		System.out.println("Criar ficheiro de texto:\n  make textfile nomeFicheiro corpo do texto\n");
		System.out.println("Apagar ficheiro de texto:\n  delete textfile nomeFicheiro\n");
		System.out.println("Substituir conte�do de um ficheiro de texto:\n  replacedata textfile nomeFicheiro corpo do texto\n");
		System.out.println("Listar conte�do de um ficheiro de texto:\n  list textfile nomeFicheiro\n");
		System.out.println("Apagar conte�do de um ficheiro de texto:\n  reset textfile nomeFicheiro\n");
		System.out.println("Adicionar conte�dos aos existentes de um ficheiro de texto:\n  adddata textfile nomeFicheiro corpo do texto\n");
	}

	//Informa��es detalhadas acerca dos ficheiros de calculo
	private static void infoFicheiroDeCalculo() {
		System.out.println("Exemplos de comandos");
		System.out.println("Criar ficheiro de c�lculo:\n  make spreadsheet nomeFicheiro\n");
		System.out.println("Apagar ficheiro de c�lculo:\n  delete spreadsheet nomeFicheiro\n");
		System.out.println("Substituir conte�do de um ficheiro de c�lculo:\n  replacedata spreadsheet nomeFicheiro numero1 opera��o(+,-,*,/,%,^) numero2\n");
		System.out.println("Listar conte�do de um ficheiro de c�lculo:\n  list spreadsheet nomeFicheiro\n");
		System.out.println("Apagar conte�do de um ficheiro de c�lculo:\n  reset spreadsheet nomeFicheiro\n");
		System.out.println("Adicionar conte�dos aos existentes de um ficheiro de c�lculo:\n  adddata spreadsheet nomeFicheiro numero1 opera��o(+,-,*,/,%,^) numero2\n");
	}

	//Informa��es detalhadas acerca das pastas
	private static void infoPastas() {
		System.out.println("Exemplos de comandos (quando � referida pasta atual, esta � referida � pasta que nos encontramos atualmente a navegar)");
		System.out.println("Criar uma pasta:\n  make folder nomePasta\n");
		System.out.println("Apagar uma pasta e os seus ficheiros:\n  delete folder nomePasta\n");
		System.out.println("Listar ficheiros da pasta atual:\n  list folder\n");
		System.out.println("Apagar todos os ficheiros da pasta atual:\n  reset folder\n");
	}

	//Informa��es detalhadas acerca da navegacao do filesystem e listagem de ficheiros
	private static void infoNavegacao() {
		System.out.println("Exemplos de comandos");
		System.out.println("Entrar numa pasta:\n  enterfolder nomePasta\n");
		System.out.println("Sair de uma pasta para a sua anterior (Pai):\n  backfolder\n");
		System.out.println("Listar todos os ficheiros do filesystem:\n  listall\n");
		System.out.println("Listar todos os ficheiros a partir da pasta atual:\n  listallcurrent\n");
	}
}

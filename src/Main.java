import FileSystem.FileSystem;
import TiposDeFicheiro.Ficheiro;
import TiposDeFicheiro.FicheiroDeCalculo;
import TiposDeFicheiro.FicheiroDeTexto;
import TiposDeFicheiro.Pasta;

public class Main {
	FileSystem fileSystem = new FileSystem();
	
	public static void main(String[] args) {
		
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
			if(fileSystem.existe(dados[1]) && fileSystem.retornaFicheiro(dados[1]).getTipoFicheiro().equals("Pasta")) {
				fileSystem.setAtual((Pasta) fileSystem.retornaFicheiro(dados[1]));
			}
			break;
		case "BACKFOLDER":
			if(fileSystem.getAtual().getPai() != null) {
				fileSystem.setAtual(fileSystem.getAtual().getPai());
			}
			break;
		}
	}
	
	//Realiza as operacoes do input do utilizador relativas a um ficheiro de texto ou um ficheiro de calculo
	private void ficheiroDeTextoOuCalculo(String[] argumentos) {
		switch(argumentos[0].toUpperCase()) {
		case "MAKE":
			if(argumentos[1].toUpperCase().equals("TEXTFILE") && validacaoDados(argumentos)) {
				String textoCorpo = "";
				for(int i = 3; i < argumentos.length; i++) {
					textoCorpo += argumentos[i];
				}
				System.out.println(fileSystem.inserir(new FicheiroDeTexto(argumentos[2], textoCorpo)));
			} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
				System.out.println(fileSystem.inserir(new FicheiroDeCalculo(argumentos[2])));
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
						textoSubstituto += argumentos[i];
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
			System.out.println(fileSystem.listar() + "\n");
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
						textoAcrescentar += argumentos[i];
					}
					ficheiro.escrever(textoAcrescentar);
					System.out.println("Conteúdos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				} else if(argumentos[1].toUpperCase().equals("SPREADSHEET") && validacaoDados(argumentos)) {
					FicheiroDeCalculo ficheiro = (FicheiroDeCalculo) fileSystem.retornaFicheiro(argumentos[2]);
					ficheiro.escrever(Integer.parseInt(argumentos[3]), Integer.parseInt(argumentos[5]), argumentos[4]);
					System.out.println("Conteúdos adicionados ao " + ficheiro.getTipoFicheiro() + " " + ficheiro.getNome() + " com sucesso.\n");
				}
				break;
			} else {
				System.out.println("O ficheiro especificado não existe.\n");
			}
		}
	}

	//Realiza as operacoes do input do utilizador relativas a uma pasta
	private void ficheiroPasta(String[] argumentos) {
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

	//Validacao dos dados a inserir num ficheiro
	private boolean validacaoDados(String[] dados) {
		switch(dados[1].toUpperCase()) {
		case "TEXTFILE":
			if(dados[3].length() > 0) {
				return true;
			} else {
				return false;
			}
		case "SPREADSHEET":
			if(!(dados[4] == "+" || dados[4] == "-" || dados[4] == "*" || dados[4] == "/" || dados[4] == "%" || dados[4] == "^")) {
				return false;
			}
			try {
				int var = Integer.parseInt(dados[3]);
				var = Integer.parseInt(dados[5]);
		    } catch(NumberFormatException ex) {
		        return false;
		    }
			return true;
		}
		return false;
	}

}

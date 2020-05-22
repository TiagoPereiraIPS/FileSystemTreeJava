import FileSystem.FileSystem;
import TiposDeFicheiro.FicheiroDeCalculo;
import TiposDeFicheiro.FicheiroDeTexto;
import TiposDeFicheiro.Pasta;

public class Main {

	public static void main(String[] args) {
		FileSystem fileSystem = new FileSystem();
		
		fileSystem.inserir(fileSystem.getRoot(), new FicheiroDeTexto("Historia", "Era uma vez"));
		fileSystem.inserir(fileSystem.getRoot(), new FicheiroDeCalculo("Atrio contas"));
		
		Pasta pasta1 = new Pasta("Escola");
		
		fileSystem.inserir(fileSystem.getRoot(), pasta1);
		
		fileSystem.inserir(pasta1, new FicheiroDeTexto("Escola texto", "Era uma vez uma escola"));
		fileSystem.inserir(pasta1, new FicheiroDeCalculo("Escola contas"));
		
		Pasta pasta2 = new Pasta("Trabalho");
		
		fileSystem.inserir(fileSystem.getRoot(), pasta2);
		FicheiroDeCalculo fc1 = new FicheiroDeCalculo("Trabalho contas");
		fc1.escrever(2, 4, "*");
		
		fileSystem.inserir(pasta2, new FicheiroDeTexto("Trabalho texto", "Era uma vez"));
		fileSystem.inserir(pasta2, fc1);
		
		Pasta pasta3 = new Pasta("Teste");
		
		fileSystem.inserir(pasta2, pasta3);
		
		fileSystem.inserir(pasta3, new FicheiroDeTexto("Pasta3 texto", "Era uma vez uma escola"));
		
		System.out.println(fileSystem.listarFicheirosFileSystem());
		//System.out.println(fileSystem.pesquisarFicheiro("Trabalho contas"));
		//System.out.println(fileSystem.pesquisarFicheiro("Escola texto"));
		//System.out.println(fileSystem.pesquisarFicheiro("Historia"));
	}

}

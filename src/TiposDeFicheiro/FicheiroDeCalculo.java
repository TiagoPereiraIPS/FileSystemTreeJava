package TiposDeFicheiro;

public class FicheiroDeCalculo extends Ficheiro {
	//Atributo
	private String corpoFicheiro;
	
	//Construtor
	public FicheiroDeCalculo(String nome) {
		super(nome);
		corpoFicheiro="";
	}

	//Acessores
	public String getCorpoFicheiro() {
		return corpoFicheiro;
	}

	public void setCorpoFicheiro(String corpoFicheiro) {
		this.corpoFicheiro = corpoFicheiro;
	}
	
	//Metodos
	//Calcula e acrescenta uma conta as ja existentes
	public void escrever(int n1, int n2, String operador) {
		this.corpoFicheiro = this.corpoFicheiro.isEmpty() ? calcular(n1, n2, operador) : this.corpoFicheiro+"\n"+calcular(n1, n2, operador);
	}
	
	//Calcula e substitui o texto atual pelo calculo realizado
	public void substituirEscrever(int n1, int n2, String operador) {
		this.setCorpoFicheiro(calcular(n1, n2, operador));
	}
	
	//Fazer conta
	private String calcular(int n1, int n2, String operador) {
        double resultado = 0;
        
        switch(operador) {
        	case "+":
        		resultado=n1+n2;
        		break;
        	case "-":
        		resultado=n1-n2;
        		break;
        	case "*":
        		resultado=n1*n2;
        		break;
        	case "/":
        		resultado=n1/n2;
        		break;
        	case "%":
        		resultado=n1%n2;
        		break;
        	case "^":
        		resultado=Math.pow(n1, n2);
        		break;
        }
        
        return n1 + " " + operador + " " + n2 + " = " + resultado;
    }
	
	@Override
	public String listar() {	
		if(corpoFicheiro.isEmpty()) {
			return "O ficheiro de calculo encontra-se vazio";
		}
		
		return this.getCorpoFicheiro();
	}
	
	@Override
	public void resetConteudo() {
		this.corpoFicheiro="";
	}
	
	@Override
	public String getTipoFicheiro() {
		return "Ficheiro de Cálculo";
	}

	//ToString
	@Override
	public String toString() {
		return "FicheiroDeCalculo [nome=" + this.getNome() + ", corpoFicheiro=" + corpoFicheiro + "]";
	}
}

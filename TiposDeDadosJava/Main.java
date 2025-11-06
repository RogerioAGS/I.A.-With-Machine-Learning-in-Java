//public Java
public class Main {
    public static void main(String[] args) {
        
        System.out.println("--- Iniciando Exercício 1: Tipos de Dados ---");
        
        // 1. Tipo Inteiro (int)
        int numeroDeModelos;
        
        // 2. Tipo Decimal (double)
        double valorDaEntropia; 
        
        // 3. Tipo Booleano (boolean)
        boolean isTreinamentoConcluido; 
        
        // 4. Tipo String
        String versaoDoSDK; 
        
        // ------------------------------------------------------------------
        
        // TODO: Inicialize a variável 'numeroDeModelos' com um valor inteiro (e.g., 5)
        numeroDeModelos = 3;
        
        // TODO: Inicialize a variável 'valorDaEntropia' com um valor decimal (e.g., 0.98)
        valorDaEntropia = 0.925; 
        
        // TODO: Inicialize a variável 'isTreinamentoConcluido' com 'true' ou 'false'
        isTreinamentoConcluido = true; 
        
        // TODO: Inicialize a variável 'versaoDoSDK' com um texto (e.g., "oci-sdk-v1.0")
        versaoDoSDK = "oci-ai-language-v2.0";
        
        
        // ------------------------------------------------------------------
        
        System.out.println("\n--- Valores das Variáveis ---");
        
        // TODO: Use System.out.println() para imprimir o valor de cada uma das quatro variáveis:
        System.out.println("Número de Modelos no Projeto: " + numeroDeModelos);
        System.out.println("Entropia Máxima Calculada: " + valorDaEntropia);
        System.out.println("Status do Treinamento: " + isTreinamentoConcluido);
        System.out.println("Versão do SDK da Oracle: " + versaoDoSDK);
        
    }
}
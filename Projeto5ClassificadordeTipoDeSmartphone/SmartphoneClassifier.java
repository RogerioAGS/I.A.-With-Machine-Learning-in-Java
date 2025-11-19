import java.util.*;

/**
 * Ponto de entrada do programa.
 * Carrega o dataset, calcula o Ganho de Informação, constrói a árvore ID3 e
 * executa testes de classificação.
 */
public class SmartphoneClassifier { // CORREÇÃO: Não precisa estender ID3Algorithm

    private static final String[] ATTRIBUTE_NAMES = {"SO", "Tela", "Câmera", "Preço"};

    /**
     * Atravessa a árvore de decisão para classificar um novo ponto de dados.
     */
    public static String classify(ID3Node tree, DataPoint newDevice) {
        // 1. Nó Folha: Retorna o rótulo
        if (tree.label != null) {
            return tree.label; 
        }

        // 2. Nó Interno: Encontra o índice do atributo de decisão
        int attrIndex = -1;
        for (int i = 0; i < ATTRIBUTE_NAMES.length; i++) {
            if (ATTRIBUTE_NAMES[i].equals(tree.attributeName)) {
                attrIndex = i;
                break;
            }
        }

        if (attrIndex == -1) return "Erro de Atributo";

        // 3. Obtém o valor do atributo no novo dispositivo
        String value = newDevice.getFeature(attrIndex);

        // 4. Navega para o nó filho
        if (tree.children.containsKey(value)) {
            return classify(tree.children.get(value), newDevice);
        } else {
            // Caso em que o valor não foi visto no treinamento
            return "Decisão Indefinida (Valor não encontrado: " + value + ")";
        }
    }

    public static void main(String[] args) {
        // Dataset de 8 pontos (4 Trocar, 4 Não Trocar)
        List<DataPoint> dataset = Arrays.asList(
            new DataPoint(Arrays.asList("iOS", "Média", "Alta", "Caro"), "Não Trocar"),       
            new DataPoint(Arrays.asList("Android", "Grande", "Alta", "Razoável"), "Não Trocar"), 
            new DataPoint(Arrays.asList("iOS", "Pequena", "Média", "Razoável"), "Trocar"),    
            new DataPoint(Arrays.asList("Outro", "Média", "Baixa", "Barato"), "Trocar"),        
            new DataPoint(Arrays.asList("Android", "Grande", "Média", "Barato"), "Trocar"),   
            new DataPoint(Arrays.asList("iOS", "Média", "Baixa", "Caro"), "Não Trocar"),       
            new DataPoint(Arrays.asList("Android", "Grande", "Alta", "Razoável"), "Não Trocar"), 
            new DataPoint(Arrays.asList("Android", "Pequena", "Alta", "Caro"), "Trocar")      
        );

        // I. Foco na Discussão: Ganho de Informação Inicial (Raiz)
        // CORREÇÃO: Chamada direta aos métodos estáticos em ID3Metrics
        double initialEntropy = ID3Metrics.calculateEntropy(dataset); 
        System.out.printf("Entropia Inicial (E(S)): %.4f (Esperado: 1.0000)%n", initialEntropy);
        System.out.println("------------------------------------");

        Set<Integer> allIndices = new HashSet<>(Arrays.asList(0, 1, 2, 3)); 
        double maxGain = -1.0;
        String bestAttr = "";

        // Calcula o Ganho para cada atributo
        for (int i = 0; i < ATTRIBUTE_NAMES.length; i++) {
            // CORREÇÃO: Chamada direta aos métodos estáticos em ID3Metrics
            double gain = ID3Metrics.calculateGain(dataset, i); 
            System.out.printf("Ganho(%s): %.4f%n", ATTRIBUTE_NAMES[i], gain);
            if (gain > maxGain) {
                maxGain = gain;
                bestAttr = ATTRIBUTE_NAMES[i];
            }
        }
        System.out.println("------------------------------------");
        System.out.println("Melhor Atributo para Raiz: **" + bestAttr + "** (Ganho: " + String.format("%.4f", maxGain) + ")");

        // II. Construção e Classificação
        System.out.println("\n--- Construindo Árvore de Decisão ID3 ---");
        // CORREÇÃO: Chamada direta ao método estático em ID3Algorithm
        ID3Node decisionTree = ID3Algorithm.buildTree(dataset, allIndices); 
        System.out.println("Árvore construída. Raiz: " + decisionTree.attributeName);

        System.out.println("\n--- Testes de Classificação ---");

        // Teste 1: Dispositivo que deve levar a 'Não Trocar'
        DataPoint test1 = new DataPoint(Arrays.asList("iOS", "Média", "Alta", "Caro"), null);
        System.out.println("Teste 1 (iOS, Alta Câmera): Decisão -> " + classify(decisionTree, test1)); 

        // Teste 2: Dispositivo que deve levar a 'Trocar'
        DataPoint test2 = new DataPoint(Arrays.asList("Outro", "Pequena", "Baixa", "Barato"), null);
        System.out.println("Teste 2 (Baixa Câmera, Barato): Decisão -> " + classify(decisionTree, test2));
    }
}
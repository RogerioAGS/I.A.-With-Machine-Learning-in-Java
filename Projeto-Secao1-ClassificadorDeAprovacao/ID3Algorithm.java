import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.Comparator;

/**
 * ============================================================================
 * PROJETO: CLASSIFICADOR DE APROVAÇÃO DE EMPRÉSTIMO PESSOAL
 * Algoritmo ID3 (Iterative Dichotomiser 3) para Árvore de Decisão
 * ============================================================================
 * 
 * OBJETIVO:
 * Construir uma árvore de decisão que classifica se um empréstimo deve ser
 * aprovado (Sim/Não) com base em 4 atributos de risco do solicitante.
 * 
 * ATRIBUTOS PREDITORES:
 * 1. Histórico: Ruim, Neutro, Bom
 * 2. Renda: Baixa, Média, Alta
 * 3. Emprego: Instável, Estável
 * 4. Garantia: Não, Sim
 * 
 * ATRIBUTO ALVO:
 * - Aprovar: Sim, Não
 * 
 * ALGORITMO ID3:
 * 1. Calcula a entropia do conjunto de dados
 * 2. Para cada atributo, calcula o ganho de informação
 * 3. Escolhe o atributo com maior ganho como raiz
 * 4. Divide os dados por esse atributo
 * 5. Aplica recursivamente para cada subconjunto
 * 
 * ============================================================================
 * 
 * @author Rogerio
 * @version 1.0
 */

public class ID3Algorithm {
    
    // VARIÁVEIS GLOBAIS DE SETUP (Etapa 2)
    public static final String TARGET_ATTRIBUTE = "Assistir";
    public static final List<String> ATTRIBUTES = List.of("Gênero", "Duração", "Avaliação", "Atores");

    // Conjunto de dados (Dataset) - 14 Instâncias (Etapa 2)
    public static final List<Map<String, String>> DATA = List.of(
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Longa", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Sci-Fi", "Duração", "Longa", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Não"),
        Map.of("Gênero", "Ação", "Duração", "Alta", "Avaliação", "Alta", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Média", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Ação", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Média", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Comédia", "Duração", "Média", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
        Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não")
    );

    /**
     * Calcula a Entropia de um conjunto de dados. (Etapa 3)
     */
    public static double calculateEntropy(List<Map<String, String>> data, String targetAttribute) {
        if (data.isEmpty()) { return 0.0; }
        Map<String, Long> countByClass = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()));
        double entropy = 0.0;
        int totalInstances = data.size();
        for (Long count : countByClass.values()) {
            double probability = (double) count / totalInstances;
            if (probability > 0) {
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }
        return entropy;
    }

    /**
     * Calcula o Ganho de Informação para um atributo. (Etapa 4)
     */
    public static double calculateGain(List<Map<String, String>> data, String attribute, String targetAttribute) {
        double totalEntropy = calculateEntropy(data, targetAttribute);
        int totalInstances = data.size();

        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(attribute)));

        double weightedEntropy = 0.0;
        for (List<Map<String, String>> subset : splitData.values()) {
            double probability = (double) subset.size() / totalInstances;
            weightedEntropy += probability * calculateEntropy(subset, targetAttribute);
        }

        return totalEntropy - weightedEntropy;
    }

    /**
     * Algoritmo Recursivo ID3 para construir a Árvore de Decisão. (Etapa 4)
     */
    public static TreeNode buildTree(List<Map<String, String>> data, List<String> availableAttributes, String targetAttribute) {
        // Caso Base 1 (Pureza)
        String firstClass = data.get(0).get(targetAttribute);
        if (data.stream().allMatch(instance -> instance.get(targetAttribute).equals(firstClass))) {
            return new TreeNode(firstClass, true);
        }

        // Caso Base 2 (Parada)
        if (availableAttributes.isEmpty() || data.isEmpty()) {
            String majorityClass = data.stream()
                .collect(Collectors.groupingBy(instance -> instance.get(targetAttribute), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(firstClass);
            return new TreeNode(majorityClass, true);
        }

        // 1. Encontrar o atributo com o MAIOR Ganho
        String bestAttribute = availableAttributes.stream()
            .max(Comparator.comparingDouble(attribute -> calculateGain(data, attribute, targetAttribute)))
            .orElseThrow(() -> new IllegalStateException("Erro: Não foi possível encontrar o melhor atributo."));

        // 2. Criar o nó raiz e 3. Remover o atributo
        TreeNode root = new TreeNode(bestAttribute, false);
        List<String> remainingAttributes = new ArrayList<>(availableAttributes);
        remainingAttributes.remove(bestAttribute);
        
        // 4. Chamada Recursiva para cada valor
        Map<String, List<Map<String, String>>> splitData = data.stream()
            .collect(Collectors.groupingBy(instance -> instance.get(bestAttribute)));

        for (Map.Entry<String, List<Map<String, String>>> entry : splitData.entrySet()) {
            TreeNode childNode = buildTree(entry.getValue(), remainingAttributes, targetAttribute);
            root.addChild(entry.getKey(), childNode);
        }

        return root;
    }
    
    /**
     * Imprime a Árvore de Decisão usando um Percurso em Pré-Ordem. (Etapa 3)
     */
    public static void printTree(TreeNode node, String prefix) {
        if (node.isLeaf) {
            System.out.println(prefix + "-> DECISÃO: " + node.attribute);
            return;
        }
        System.out.println(prefix + "-> TESTE: " + node.attribute + "?");
        for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
            String attributeValue = entry.getKey();
            TreeNode childNode = entry.getValue();
            String newPrefix = prefix + "    [Se " + node.attribute + " é " + attributeValue + "] ";
            printTree(childNode, newPrefix);
        }
    }

    /**
     * Usa a Árvore de Decisão para classificar uma nova instância (Inferência). (Etapa 5)
     */
    public static String classify(TreeNode node, Map<String, String> instance) {
        if (node.isLeaf) {
            return node.attribute;
        }
        String attributeValue = instance.get(node.attribute);
        if (node.children.containsKey(attributeValue)) {
            return classify(node.children.get(attributeValue), instance);
        } else {
            System.err.println("Aviso: Valor não visto ('" + attributeValue + "') no atributo '" + node.attribute + "'. Não foi possível classificar.");
            return "DESCONHECIDO"; 
        }
    }

    public static void main(String[] args) {
        System.out.println("Setup concluído! Total de Instâncias de Treino: " + DATA.size());
        
        // --- TESTE DE ENTROPIA INICIAL (Etapa 3) ---
        double initialEntropy = calculateEntropy(DATA, TARGET_ATTRIBUTE);
        System.out.printf("\nEntropia Inicial (total): %.4f\n", initialEntropy);

        // --- CONSTRUÇÃO DA ÁRVORE ID3 (Etapa 4) ---
        System.out.println("\n--- Construção da Árvore ID3 ---");
        TreeNode decisionTree = buildTree(DATA, ATTRIBUTES, TARGET_ATTRIBUTE);
        printTree(decisionTree, "");

        // --- AVALIAÇÃO DO MODELO (Etapa 5) ---
        List<Map<String, String>> TEST_DATA = List.of(
            Map.of("Gênero", "Ação", "Duração", "Média", "Avaliação", "Baixa", "Atores", "Conhecidos", TARGET_ATTRIBUTE, "Não"),
            Map.of("Gênero", "Comédia", "Duração", "Longa", "Avaliação", "Alta", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim"),
            Map.of("Gênero", "Drama", "Duração", "Curta", "Avaliação", "Média", "Atores", "Desconhecidos", TARGET_ATTRIBUTE, "Sim")
        );
        
        int correctPredictions = 0;
        
        System.out.println("\n--- Avaliação do Modelo (Testando Recomendações) ---");

        for (Map<String, String> testInstance : TEST_DATA) {
            String actualClass = testInstance.get(TARGET_ATTRIBUTE);
            Map<String, String> instanceToClassify = new HashMap<>(testInstance);
            instanceToClassify.remove(TARGET_ATTRIBUTE);
            
            String prediction = classify(decisionTree, instanceToClassify);
            
            if (prediction.equals(actualClass)) {
                correctPredictions++;
                System.out.print("✅ ");
            } else {
                System.out.print("❌ ");
            }
            System.out.println("Previsto: " + prediction + " | Real: " + actualClass);
        }

        double accuracy = (double) correctPredictions / TEST_DATA.size() * 100;
        System.out.printf("\nACURÁCIA (Precisão) do Modelo: %d/%d = %.2f%%\n",
                             correctPredictions, TEST_DATA.size(), accuracy);
    }
}
